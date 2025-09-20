package com.cassiopea.orderservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class InventoryResponseDTO {
    private List<String> available;
    private List<String> insufficient;
    private List<String> outOfStock;
    private List<String> invalid;
}
