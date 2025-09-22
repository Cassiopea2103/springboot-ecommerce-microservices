package com.cassiopea.orderservice.mappers;

import com.cassiopea.orderservice.dto.OrderLineItemDto;
import com.cassiopea.orderservice.dto.OrderResponse;
import com.cassiopea.orderservice.entities.Order;

public class OrderResponseMapper {
    public static OrderResponse toOrderResponse(Order order ) {
        return OrderResponse.builder()
                .id ( order.getId () )
                .orderNumber ( order.getOrderNumber() )
                .orderLineItems(
                        order.getOrderLineItems().stream().map (
                                OrderLineItemMapper::toOrderLineItemDto
                        ).toArray ( OrderLineItemDto[]::new )
                )
                .build () ;
    }
}
