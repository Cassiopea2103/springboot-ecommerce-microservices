package com.cassiopea.inventoryservice.exceptions.types;

public class OutOfStockException extends RuntimeException{
    public OutOfStockException ( String message ) {
        super ( message ) ;
    }
}
