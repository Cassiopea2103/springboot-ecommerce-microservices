package com.cassiopea.orderservice.services;

import com.cassiopea.orderservice.dto.OrderLineItemDto;
import com.cassiopea.orderservice.dto.OrderRequest;
import com.cassiopea.orderservice.dto.OrderResponse;
import com.cassiopea.orderservice.exceptions.types.OrderNotFoundException;
import com.cassiopea.orderservice.mappers.OrderLineItemMapper;
import com.cassiopea.orderservice.mappers.OrderResponseMapper;
import com.cassiopea.orderservice.entities.Order;
import com.cassiopea.orderservice.entities.OrderLineItem;
import com.cassiopea.orderservice.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository ;

    public Page<OrderResponse> getAllOrders ( int page , int size ) {
        Pageable pageable = PageRequest.of ( page , size ) ;
        Page<OrderResponse> orders = orderRepository.findAll ( pageable )
                .map ( OrderResponseMapper::toOrderResponse ) ;
        if (orders.isEmpty()) throw new OrderNotFoundException("No orders found!") ;
        return orders ;
    }

    public OrderResponse getOrderById ( Long id ) {
        return orderRepository.findById ( id )
                .map ( OrderResponseMapper::toOrderResponse )
                .orElseThrow ( () -> new OrderNotFoundException("Product with ID " + id + " not found!"));
    }

    public OrderResponse createOrder (OrderRequest request ) {

        OrderLineItemDto[]  orderLineItemsDtos = request.getOrderLineItemDtos();
        List < OrderLineItem> orderLineItems = Arrays.stream(orderLineItemsDtos).map(OrderLineItemMapper::toOrderLineItem).toList() ;

        // create order entity :
        Order order = Order.builder()
                .orderNumber ( UUID.randomUUID().toString() )
                .orderLineItems ( orderLineItems )
                .build() ;

        // persist order entity to database :
        orderRepository.save ( order ) ;

        return OrderResponseMapper.toOrderResponse ( order ) ;
    }

    public String deleteOrder ( Long orderId ) {
        getOrderById ( orderId ) ;
        orderRepository.deleteById ( orderId ) ;
        return "Order with id " + orderId + " successfully deleted!" ;
    }
}
