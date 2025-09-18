package com.cassiopea.inventoryservice.exceptions.types;

public class InventoryNotFoundException extends RuntimeException{
    public InventoryNotFoundException( String message ) { super ( message ) ;}
}
