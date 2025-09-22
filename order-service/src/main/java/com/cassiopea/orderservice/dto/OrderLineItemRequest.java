package com.cassiopea.orderservice.dto;

public record OrderLineItemRequest(
        String skuCode,
        Integer quantity
) {
}