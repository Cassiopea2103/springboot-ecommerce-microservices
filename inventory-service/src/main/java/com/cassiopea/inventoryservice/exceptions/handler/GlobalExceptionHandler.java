package com.cassiopea.inventoryservice.exceptions.handler;

import com.cassiopea.inventoryservice.exceptions.models.ExceptionResponse;
import com.cassiopea.inventoryservice.exceptions.types.InvalidQuantityException;
import com.cassiopea.inventoryservice.exceptions.types.InventoryNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // inventory not found exception :
    @ExceptionHandler (InventoryNotFoundException.class )
    public ResponseEntity<ExceptionResponse> handleInventoryNotFoundException ( InventoryNotFoundException exception, HttpServletRequest request ) {
        ExceptionResponse errorResponse = ExceptionResponse.builder ()
                .status (HttpStatus.NOT_FOUND.value() )
                .method ( request.getMethod() )
                .path ( request.getRequestURI() )
                .message ( exception.getMessage () )
                .build () ;
        return ResponseEntity.status ( HttpStatus.NOT_FOUND).body ( errorResponse ) ;
    }

    // invalid quantity exception :
    @ExceptionHandler (InvalidQuantityException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidQuantityException ( InvalidQuantityException exception, HttpServletRequest request ) {
        ExceptionResponse errorResponse = ExceptionResponse.builder()
                .status ( HttpStatus.BAD_REQUEST.value() )
                .method ( request.getMethod() )
                .path ( request.getRequestURI() )
                .message ( exception.getMessage () )
                .build () ;
        return ResponseEntity.status (HttpStatus.BAD_REQUEST).body ( errorResponse ) ;
    }

    // default generic exceptions :
    @ExceptionHandler ( Exception.class )
    public ResponseEntity<ExceptionResponse> handleGenericExceptions (Exception exception, HttpServletRequest request) {
        ExceptionResponse errorResponse = ExceptionResponse.builder()
                .status (HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message ( exception.getMessage() )
                .method ( request.getMethod() )
                .path (request.getRequestURI() )
                .build() ;
        return ResponseEntity.status ( HttpStatus.INTERNAL_SERVER_ERROR).body (errorResponse ) ;
    }


}
