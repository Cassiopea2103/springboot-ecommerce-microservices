package com.cassiopea.orderservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

@FeignClient(name = "product-service", path = "/api/v1/products")
public interface ProductClient {
    // get product price by sku code :
    @GetMapping("/{skuCode}/price")
    BigDecimal getPriceBySkuCode(@PathVariable String skuCode);
}
