package com.cassiopea.inventoryservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", path = "/api/v1/products")
public interface ProductClient {
    @GetMapping("/sku/{skuCode}")
    void checkProductBySkuCode(@PathVariable("skuCode") String skuCode);
}
