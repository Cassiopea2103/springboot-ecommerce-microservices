package com.cassiopea.inventoryservice.exceptions.types;

import lombok.Data;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }

}
