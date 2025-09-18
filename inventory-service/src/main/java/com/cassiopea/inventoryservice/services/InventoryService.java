package com.cassiopea.inventoryservice.services;

import com.cassiopea.inventoryservice.dto.InventoryQuantityRequest;
import com.cassiopea.inventoryservice.dto.InventoryRequest;
import com.cassiopea.inventoryservice.dto.InventoryResponse;
import com.cassiopea.inventoryservice.entities.Inventory;
import com.cassiopea.inventoryservice.exceptions.types.InvalidQuantityException;
import com.cassiopea.inventoryservice.exceptions.types.InventoryNotFoundException;
import com.cassiopea.inventoryservice.mappers.InventoryMapper;
import com.cassiopea.inventoryservice.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository ;

    public Page < InventoryResponse> getAllInventoryItems ( int page , int size ) {
        Pageable pageable = PageRequest.of ( page , size ) ;
        Page<InventoryResponse> inventoryItems =  inventoryRepository.findAll ( pageable )
                .map( InventoryMapper::toInventoryResponse );

        if ( inventoryItems.isEmpty() ) throw new InventoryNotFoundException("No inventory items found") ;
        return inventoryItems ;
    }

    public InventoryResponse getInventoryItemById ( Long inventoryId ) {
        return inventoryRepository.findById(inventoryId )
                .map ( InventoryMapper::toInventoryResponse )
                .orElseThrow (
                        () -> new InventoryNotFoundException("Inventory with id " + inventoryId + " not found")
                );
    }

    public boolean isInStock ( String skuCode ) {
        return inventoryRepository.findBySkuCode(skuCode).isPresent();
    }

    public InventoryResponse createInventory (InventoryRequest request ) {
        Inventory newInventory = Inventory.builder()
                .skuCode ( request.getSkuCode () )
                .quantity ( request.getQuantity () )
                .build ()  ;

        // save to database :
        inventoryRepository.save ( newInventory ) ;
        return InventoryMapper.toInventoryResponse ( newInventory ) ;
    }

    public InventoryResponse updateQuantity ( Long id , InventoryQuantityRequest request ) {
        Inventory foundInventory = InventoryMapper.toInventory(getInventoryItemById(id));
        int updatedQuantity = foundInventory.getQuantity() + request.getQuantity() ;
        if ( updatedQuantity < 0 ) throw new InvalidQuantityException() ;
        foundInventory.setQuantity ( updatedQuantity ) ;
        inventoryRepository.save ( foundInventory ) ;
        return InventoryMapper.toInventoryResponse(foundInventory);
    }

    public String deleteInventoryItem ( long inventoryId ) {
        getInventoryItemById ( inventoryId ) ;
        inventoryRepository.deleteById ( inventoryId ) ;
        return "Inventory with ID " + inventoryId + " deleted successfully." ;
    }

}
