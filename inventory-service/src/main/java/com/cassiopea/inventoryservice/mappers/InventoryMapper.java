package com.cassiopea.inventoryservice.mappers;

import com.cassiopea.inventoryservice.dto.InventoryResponse;
import com.cassiopea.inventoryservice.entities.Inventory;

public class InventoryMapper {
    public static InventoryResponse toInventoryResponse (Inventory inventory ) {
        return InventoryResponse.builder()
                .id ( inventory.getId () )
                .skuCode ( inventory.getSkuCode() )
                .quantity ( inventory.getQuantity() )
                .build () ;
    }

    public static Inventory toInventory ( InventoryResponse inventoryResponse ) {
        return Inventory.builder()
                .id ( inventoryResponse.getId() )
                .skuCode( inventoryResponse.getSkuCode() )
                .quantity ( inventoryResponse.getQuantity() )
                .build() ;
    }

}
