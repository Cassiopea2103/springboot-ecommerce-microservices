package com.cassiopea.inventoryservice.repositories;

import com.cassiopea.inventoryservice.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory>findBySkuCode(String skuCode) ;
    boolean existsBySkuCode ( String skuCode ) ;
}
