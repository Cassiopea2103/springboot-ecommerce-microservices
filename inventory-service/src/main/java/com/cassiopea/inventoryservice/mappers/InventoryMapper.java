package com.cassiopea.inventoryservice.mappers;

import com.cassiopea.inventoryservice.dto.InventoryRequest;
import com.cassiopea.inventoryservice.dto.InventoryResponse;
import com.cassiopea.inventoryservice.entities.Inventory;

public class InventoryMapper {
    public static InventoryResponse toInventoryResponse(Inventory inventory) {
        return InventoryResponse.builder()
                .id(inventory.getId())
                .skuCode(inventory.getSkuCode())
                .quantity(inventory.getQuantity())
                .build();
    }

    public static Inventory requestToInventory(InventoryRequest requestDTO) {
        return Inventory.builder()
                .skuCode(requestDTO.getSkuCode())
                .quantity(requestDTO.getQuantity())
                .build();
    }

    public static Inventory responseToInventory(InventoryResponse responseDTO) {
        return Inventory.builder()
                .id(responseDTO.getId())
                .skuCode(responseDTO.getSkuCode())
                .quantity(responseDTO.getQuantity())
                .build();
    }

}
