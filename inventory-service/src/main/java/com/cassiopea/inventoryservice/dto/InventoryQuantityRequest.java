package com.cassiopea.inventoryservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryQuantityRequest {
    @NotNull ( message = "Quantity is required.")
    private Integer quantity ;
}
