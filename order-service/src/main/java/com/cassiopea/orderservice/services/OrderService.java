package com.cassiopea.orderservice.services;

import com.cassiopea.orderservice.dto.InventoryResponseDTO;
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
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient webClient;
    @Value("${com.cassiopea.inventory-service-url}")
    private String INVENTORY_URL;

    public Page<OrderResponse> getAllOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderResponse> orders = orderRepository.findAll(pageable)
                .map(OrderMapper::toOrderDTO);
        if (orders.isEmpty()) throw new OrderNotFoundException("No orders found!");
        return orders;
    }

    public OrderResponse getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(OrderMapper::toOrderDTO)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID " + id + " not found!"));
    }

    public Mono<OrderResponse> createOrder(OrderRequest request) {

        List<OrderLineItem> orderLineItems = request.getOrderLineItemDTOs().stream()
                .map(OrderLineItemMapper::toOrderLineItem).toList();

        // check stock availability via inventory service :
        Map<String, Integer> requestedSkus = orderLineItems.stream()
                .collect(Collectors.toMap(OrderLineItem::getSkuCode, OrderLineItem::getQuantity));

        // 1. call inventory service :
        return webClient.post()
                .uri(INVENTORY_URL + "/checkStock")
                .bodyValue(requestedSkus)
                .retrieve()
                .bodyToMono(InventoryResponseDTO.class)
                .publishOn(Schedulers.boundedElastic())
                .publishOn(Schedulers.boundedElastic())
                .publishOn(Schedulers.boundedElastic())
                .publishOn(Schedulers.boundedElastic())
                .flatMap(
                        // 2. validate stock :
                        inventoryResponse -> {
                            if (!inventoryResponse.getOutOfStock().isEmpty() || !inventoryResponse.getInsufficient().isEmpty() || !inventoryResponse.getInvalid().isEmpty()) {
                                StringBuilder errorMessage = new StringBuilder("Order cannot be placed due to the following inventory issues :\n");
                                if (!inventoryResponse.getInsufficient().isEmpty()) {
                                    errorMessage.append("- Insufficient stock for SKUS : ")
                                            .append(String.join(", ", inventoryResponse.getInsufficient())).append("\n");
                                }
                                if (!inventoryResponse.getOutOfStock().isEmpty()) {
                                    errorMessage.append("- Out of stock for SKUS : ")
                                            .append(String.join(", ", inventoryResponse.getOutOfStock()))
                                            .append("\n");
                                }
                                if (!inventoryResponse.getInvalid().isEmpty()) {
                                    errorMessage.append("- Invalid SKUS : ")
                                            .append(String.join(", ", inventoryResponse.getInvalid()));
                                }
                                return Mono.error(new InventoryException(errorMessage.toString()));
                            }
                            // 3.create order :
                            return Mono.fromCallable(() -> {
                                Order newOrder = Order.builder()
                                        .orderNumber(UUID.randomUUID().toString())
                                        .orderLineItems(orderLineItems)
                                        .build();
                                Order savedOrder = orderRepository.save(newOrder);
                                return OrderMapper.toOrderDTO(savedOrder);
                            });
                        }
                );
    }

    public String deleteOrder(Long orderId) {
        getOrderById(orderId);
        orderRepository.deleteById(orderId);
        return "Order with id " + orderId + " successfully deleted!";
    }
}
