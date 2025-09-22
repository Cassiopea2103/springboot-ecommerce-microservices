package com.cassiopea.orderservice.dto;

import com.cassiopea.orderservice.entities.OrderLineItem;
import lombok.Builder;
import lombok.Data;

import java.util.List;

public record OrderResponse(
        Long id,
        String orderNumber,
        List<OrderLineItemResponse> orderLineItems
) {
}