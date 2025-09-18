package com.cassiopea.inventoryservice.services;

import com.cassiopea.inventoryservice.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository ;

    public boolean isInStock ( String skuCode ) {
        return inventoryRepository.findBySkuCode(skuCode).isPresent();
    }
}
