package com.cassiopea.inventoryservice.controllers;

import com.cassiopea.inventoryservice.dto.InventoryQuantityRequest;
import com.cassiopea.inventoryservice.dto.InventoryRequest;
import com.cassiopea.inventoryservice.dto.InventoryResponse;
import com.cassiopea.inventoryservice.services.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<Page<InventoryResponse>> getAllInventoryItems(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Page<InventoryResponse> inventoryItems = inventoryService.getAllInventoryItems(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(inventoryItems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryResponse> getInventoryItemById(@PathVariable("id") Long id) {
        InventoryResponse inventoryItem = inventoryService.getInventoryItemById(id);
        return ResponseEntity.status(HttpStatus.OK).body(inventoryItem);
    }

    @PostMapping("/checkStock")
    public ResponseEntity<Map<String, List<String>>> checkStock(@RequestBody Map<String, Integer> requestedSkus) {
        Map<String, List<String>> response = inventoryService.checkStock(requestedSkus);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PostMapping
    public ResponseEntity<InventoryResponse> createInventory(@Valid @RequestBody InventoryRequest request) {
        InventoryResponse createdInventory = inventoryService.createInventory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdInventory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryResponse> addQuantity(@PathVariable("id") Long id, @RequestBody InventoryQuantityRequest request) {
        InventoryResponse addedInventory = inventoryService.updateQuantity(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(addedInventory);
    }

    // update inventory quantities by sku codes , just like the PutMapping above but using sku codes instead of ids :
    @PutMapping("/updateQuantitiesBySku")
    public ResponseEntity<List<InventoryResponse>> updateQuantitiesBySku(@RequestBody Map<String, Integer> skuQuantities) {
        List<InventoryResponse> updatedInventories = inventoryService.updateQuantitiesBySku(skuQuantities);
        return ResponseEntity.status(HttpStatus.OK).body(updatedInventories);
    }


    @DeleteMapping("/{inventoryId}")
    public ResponseEntity<String> removeInventoryItem(@PathVariable("inventoryId") Long inventoryId) {
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.deleteInventoryItem(inventoryId));
    }
}
