package com.cassiopea.orderservice.exceptions.types;

public class InventoryException extends RuntimeException {
    public InventoryException(String message) {
        super(message);
    }
}
