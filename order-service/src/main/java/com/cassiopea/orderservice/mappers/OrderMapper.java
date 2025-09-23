package com.cassiopea.orderservice.mappers;

import com.cassiopea.orderservice.dto.OrderLineItemRequest;
import com.cassiopea.orderservice.dto.OrderRequest;
import com.cassiopea.orderservice.dto.OrderResponse;
import com.cassiopea.orderservice.entities.Order;
import com.cassiopea.orderservice.entities.OrderLineItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OrderMapper {
    public static Order toOrderEntity(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderLineItems(orderRequest.orderLineItems()
                .stream()
                .map(orderLineItemRequest -> OrderLineItemMapper.toOrderLineItemEntity(orderLineItemRequest, BigDecimal.ZERO)) // Price will be set later
                .toList());

        return order;
    }

    public static OrderResponse toOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getOrderLineItems()
                        .stream()
                        .map(OrderLineItemMapper::toOrderLineItemResponse)
                        .toList()
        );
    }
}
