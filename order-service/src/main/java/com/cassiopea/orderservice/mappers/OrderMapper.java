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

public class OrderMapper {
    public static Order toOrderEntity(OrderRequest orderRequest, List<BigDecimal> prices) {
        Order order = new Order();

        
    }
}
