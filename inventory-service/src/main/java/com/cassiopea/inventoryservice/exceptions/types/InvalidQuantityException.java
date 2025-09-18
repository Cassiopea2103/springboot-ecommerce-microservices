package com.cassiopea.inventoryservice.exceptions.types;

public class InvalidQuantityException extends RuntimeException{
    public InvalidQuantityException (){
        super ("Quantity for an inventory item cannot be negative!") ;
    }
}
