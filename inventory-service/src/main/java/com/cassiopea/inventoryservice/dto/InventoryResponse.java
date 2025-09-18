package com.cassiopea.inventoryservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryResponse {
    private Long id ;
    private String skuCode ;
    private Integer quantity ;
}
