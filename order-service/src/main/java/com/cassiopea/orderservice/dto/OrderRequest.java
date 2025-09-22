package com.cassiopea.orderservice.dto;

import java.util.List;
import java.util.UUID;

public record OrderRequest(
        List<OrderLineItemRequest> orderLineItems
) {
}