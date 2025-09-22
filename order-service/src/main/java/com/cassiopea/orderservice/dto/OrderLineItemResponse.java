package com.cassiopea.orderservice.dto;

import java.math.BigDecimal;

public record OrderLineItemResponse(
        Long id,
        String skuCode,
        Integer quantity,
        BigDecimal price
) {
}
