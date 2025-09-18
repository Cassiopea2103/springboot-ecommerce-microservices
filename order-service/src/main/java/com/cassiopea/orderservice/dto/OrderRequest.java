package com.cassiopea.orderservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderRequest {
    private String orderNumber ;
    private OrderLineItemDto [] orderLineItemDtos ;
}
