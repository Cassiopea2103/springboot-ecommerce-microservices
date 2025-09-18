package com.cassiopea.orderservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {
    private Long id ;
    private String orderNumber ;
    private OrderLineItemDto [] orderLineItems ;
}
