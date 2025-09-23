package com.cassiopea.orderservice.services;

import com.cassiopea.orderservice.clients.InventoryClient;
import com.cassiopea.orderservice.clients.ProductClient;
import com.cassiopea.orderservice.dto.OrderRequest;
import com.cassiopea.orderservice.dto.OrderResponse;
import com.cassiopea.orderservice.entities.Order;
import com.cassiopea.orderservice.entities.OrderLineItem;
import com.cassiopea.orderservice.exceptions.types.InventoryException;
import com.cassiopea.orderservice.exceptions.types.OrderNotFoundException;
import com.cassiopea.orderservice.mappers.OrderLineItemMapper;
import com.cassiopea.orderservice.mappers.OrderMapper;
import com.cassiopea.orderservice.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final InventoryClient inventoryClient;
    @Value("${com.cassiopea.inventory-service-url}")
    private String INVENTORY_URL;

    public Page<OrderResponse> getAllOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderResponse> orders = orderRepository.findAll(pageable)
                .map(OrderMapper::toOrderResponse);
        if (orders.isEmpty()) throw new OrderNotFoundException("No orders found!");
        return orders;
    }

    public OrderResponse getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(OrderMapper::toOrderResponse)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID " + id + " not found!"));
    }

    public OrderResponse createOrder(OrderRequest request) {

        // 1. map order line items :
        List<OrderLineItem> orderLineItems = request.orderLineItems()
                .stream()
                .map(orderLineItemRequest -> OrderLineItemMapper.toOrderLineItemEntity(orderLineItemRequest, BigDecimal.ZERO))
                .toList();

        // 2. prepare requested skus map :
        Map<String, Integer> requestedSkuQuantities = orderLineItems.stream()
                .collect(Collectors.toMap(OrderLineItem::getSkuCode, OrderLineItem::getQuantity));

        // 3. check inventory stocks for requested skus :
        Map<String, List<String>> inventoryResponse = inventoryClient.checkStock(requestedSkuQuantities);

        // 4. validate stock availability :
        if (!inventoryResponse.get("invalid").isEmpty() || !inventoryResponse.get("outOfStock").isEmpty() || !inventoryResponse.get("insufficient").isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Order cannot be placed due to the following reasons:");
            if (!inventoryResponse.get("invalid").isEmpty()) {
                errorMessage.append(" Invalid SKUs: ").append(String.join(", ", inventoryResponse.get("invalid")));
                errorMessage.append(System.lineSeparator());
            }
            if (!inventoryResponse.get("outOfStock").isEmpty()) {
                errorMessage.append("Out of stock SKUs: ").append(String.join(", ", inventoryResponse.get("outOfStock")));
                errorMessage.append(System.lineSeparator());
            }
            if (!inventoryResponse.get("insufficient").isEmpty()) {
                errorMessage.append("Insufficient stock for SKUs: ").append(String.join(", ", inventoryResponse.get("insufficient")));
                errorMessage.append(System.lineSeparator());
            }

            throw new InventoryException(errorMessage.toString());
        }

        // 5. fetch and set prices for each order line item :
        orderLineItems.forEach(item -> {
            item.setPrice(productClient.getPriceBySkuCode(item.getSkuCode()));
        });

        // 6. create order and save to database :
        Order newOrder = new Order();
        newOrder.setOrderNumber(UUID.randomUUID().toString());
        newOrder.setOrderLineItems(orderLineItems);
        Order savedOrder = orderRepository.save(newOrder);

        // 7. decrease inventory stocks :
        inventoryClient.updateQuantitiesBySku(requestedSkuQuantities);
        
        return OrderMapper.toOrderResponse(savedOrder);
    }

    public String deleteOrder(Long orderId) {
        getOrderById(orderId);
        orderRepository.deleteById(orderId);
        return "Order with id " + orderId + " successfully deleted!";
    }
}
