package com.cassiopea.inventoryservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryRequest {
    @NotNull(message = "skuCode is required")

    private String skuCode;
    @NotNull(message = "Quantity is required.")
    private Integer quantity;
}
