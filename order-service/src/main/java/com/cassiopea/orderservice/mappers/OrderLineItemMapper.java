package com.cassiopea.orderservice.mappers;

import com.cassiopea.orderservice.dto.OrderLineItemDto;
import com.cassiopea.orderservice.entities.OrderLineItem;

public class OrderLineItemMapper {
    public static OrderLineItemDto toOrderLineItemDto (OrderLineItem orderLineItem ){
        return OrderLineItemDto.builder()
                .skuCode ( orderLineItem.getSkuCode() )
                .quantity ( orderLineItem.getQuantity())
                .price ( orderLineItem.getPrice() )
                .build() ;
    }

    public static OrderLineItem toOrderLineItem ( OrderLineItemDto dto ) {
        return OrderLineItem.builder()
                .skuCode( dto.getSkuCode() )
                .quantity( dto.getQuantity())
                .price ( dto.getPrice())
                .build() ;
    }
}
