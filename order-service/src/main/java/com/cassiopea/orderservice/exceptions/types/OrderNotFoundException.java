package com.cassiopea.orderservice.exceptions.types;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(String message ) {
        super ( message ) ;
    }
}
