package com.cassiopea.orderservice.mappers;

import com.cassiopea.orderservice.dto.OrderLineItemRequest;
import com.cassiopea.orderservice.dto.OrderLineItemResponse;
import com.cassiopea.orderservice.entities.OrderLineItem;

import java.math.BigDecimal;

public class OrderLineItemMapper {
    public static OrderLineItem toOrderLineItemEntity(OrderLineItemRequest orderLineItemRequest, BigDecimal price) {
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setSkuCode(orderLineItemRequest.skuCode());
        orderLineItem.setQuantity(orderLineItemRequest.quantity());
        orderLineItem.setPrice(price);
        return orderLineItem;
    }

    public static OrderLineItemResponse toOrderLineItemResponse(OrderLineItem orderLineItem) {
        return new OrderLineItemResponse(
                orderLineItem.getId(),
                orderLineItem.getSkuCode(),
                orderLineItem.getQuantity(),
                orderLineItem.getPrice()
        );
    }
}
