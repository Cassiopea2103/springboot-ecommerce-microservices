package com.cassiopea.orderservice.mappers;

import com.cassiopea.orderservice.dto.InventoryResponseDTO;

import java.util.List;
import java.util.Map;

public class InventoryMapper {
    public static InventoryResponseDTO responseToDTO(Map<String, List<String>> responseData) {
        return InventoryResponseDTO.builder()
                .available(responseData.get("available"))
                .insufficient(responseData.get("insufficient"))
                .outOfStock(responseData.get("outOfStock"))
                .invalid(responseData.get("invalid"))
                .build();
    }
}
