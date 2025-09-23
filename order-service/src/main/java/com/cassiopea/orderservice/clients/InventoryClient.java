package com.cassiopea.orderservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "inventory-service", path = "/api/v1/inventory")
public interface InventoryClient {
    @PostMapping("/checkStock")
    Map<String, List<String>> checkStock(@RequestBody Map<String, Integer> requestedSkuQuantities);

    @PutMapping("/updateQuantitiesBySku")
    void updateQuantitiesBySku(@RequestBody Map<String, Integer> skuQuantities);
}
