package com.cassiopea.inventoryservice.services;

import com.cassiopea.inventoryservice.dto.InventoryQuantityRequest;
import com.cassiopea.inventoryservice.dto.InventoryRequest;
import com.cassiopea.inventoryservice.dto.InventoryResponse;
import com.cassiopea.inventoryservice.entities.Inventory;
import com.cassiopea.inventoryservice.exceptions.types.InvalidQuantityException;
import com.cassiopea.inventoryservice.exceptions.types.InventoryNotFoundException;
import com.cassiopea.inventoryservice.exceptions.types.ProductNotFoundException;
import com.cassiopea.inventoryservice.mappers.InventoryMapper;
import com.cassiopea.inventoryservice.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.*;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final WebClient webClient;

    public Page<InventoryResponse> getAllInventoryItems(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<InventoryResponse> inventoryItems = inventoryRepository.findAll(pageable)
                .map(InventoryMapper::toInventoryResponse);

        if (inventoryItems.isEmpty()) throw new InventoryNotFoundException("No inventory items found");
        return inventoryItems;
    }

    public InventoryResponse getInventoryItemById(Long inventoryId) {
        return inventoryRepository.findById(inventoryId)
                .map(InventoryMapper::toInventoryResponse)
                .orElseThrow(
                        () -> new InventoryNotFoundException("Inventory with id " + inventoryId + " not found")
                );
    }

    public Map<String, List<String>> checkStock(Map<String, Integer> requestedSkus) {
        // found existing inventory items by sku codes :
        List<Inventory> foundInventoryItems = inventoryRepository.findBySkuCodeIn(requestedSkus.keySet());
        if (foundInventoryItems.isEmpty()) throw new InventoryNotFoundException("No inventory items found.");

        Set<String> foundSkus = new HashSet<>();
        List<String> available = new ArrayList<>();
        List<String> insufficient = new ArrayList<>();
        List<String> outOfStock = new ArrayList<>();

        for (Inventory item : foundInventoryItems) {
            String sku = item.getSkuCode();
            Integer availableQuantity = item.getQuantity();
            Integer requestedQuantity = requestedSkus.get(sku);

            foundSkus.add(sku);
            if (availableQuantity == null || availableQuantity <= 0) {
                outOfStock.add(sku);
            } else if (requestedQuantity != null && requestedQuantity > availableQuantity) {
                insufficient.add(sku);
            } else {
                available.add(sku);
            }
        }

        List<String> invalid = requestedSkus.keySet().stream()
                .filter(sku -> !foundSkus.contains(sku))
                .toList();

        return Map.of(
                "available", available,
                "insufficient", insufficient,
                "outOfStock", outOfStock,
                "invalid", invalid
        );
    }

    public Mono<InventoryResponse> createInventory(InventoryRequest request) {
        Inventory newInventory = InventoryMapper.requestToInventory(request);
        // check if sku code exists :
        return webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/sku/{skuCode}").build(request.getSkuCode())
                )
                .exchangeToMono(response -> {
                            if (response.statusCode().is2xxSuccessful()) {
                                // proceed to order creation :
                                return Mono.fromCallable(() -> InventoryMapper.toInventoryResponse(inventoryRepository.save(newInventory)));
                            } else {
                                return Mono.error(() -> new ProductNotFoundException("Product with SKU " + request.getSkuCode() + " not found!"));
                            }
                        }
                );
    }

    public InventoryResponse updateQuantity(Long id, InventoryQuantityRequest request) {
        InventoryResponse foundInventory = getInventoryItemById(id);
        int updatedQuantity = foundInventory.getQuantity() + request.getQuantity();
        if (updatedQuantity < 0) throw new InvalidQuantityException();
        foundInventory.setQuantity(updatedQuantity);
        Inventory updatedInventory = inventoryRepository.save(InventoryMapper.responseToInventory(foundInventory));
        return InventoryMapper.toInventoryResponse(updatedInventory);
    }

    public List<InventoryResponse> updateQuantitiesBySku(Map<String, Integer> skuQuantities) {
        return skuQuantities.entrySet().stream()
                .map(entry -> {
                    Inventory foundInventory = inventoryRepository.findBySkuCode(entry.getKey());
                    foundInventory.setQuantity(foundInventory.getQuantity() - entry.getValue());
                    Inventory updatedInventory = inventoryRepository.save(foundInventory);
                    return InventoryMapper.toInventoryResponse(updatedInventory);
                }).toList();
    }

    public String deleteInventoryItem(long inventoryId) {
        getInventoryItemById(inventoryId);
        inventoryRepository.deleteById(inventoryId);
        return "Inventory with ID " + inventoryId + " deleted successfully.";
    }
}
