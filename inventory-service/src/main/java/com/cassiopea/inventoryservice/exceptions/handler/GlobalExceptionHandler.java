package com.cassiopea.inventoryservice.exceptions.handler;

import com.cassiopea.inventoryservice.exceptions.models.ExceptionResponse;
import com.cassiopea.inventoryservice.exceptions.types.InvalidQuantityException;
import com.cassiopea.inventoryservice.exceptions.types.InventoryNotFoundException;
import com.cassiopea.inventoryservice.exceptions.types.OutOfStockException;
import com.cassiopea.inventoryservice.exceptions.types.ProductNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // inventory not found exception :
    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleInventoryNotFoundException(InventoryNotFoundException exception, HttpServletRequest request) {
        ExceptionResponse errorResponse = ExceptionResponse.of(HttpStatus.NOT_FOUND.value(), exception.getMessage(), request);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // invalid quantity exception :
    @ExceptionHandler(InvalidQuantityException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidQuantityException(InvalidQuantityException exception, HttpServletRequest request) {
        ExceptionResponse errorResponse = ExceptionResponse.of(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // default generic exceptions :
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGenericExceptions(Exception exception, HttpServletRequest request) {
        ExceptionResponse errorResponse = ExceptionResponse.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), request);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<ExceptionResponse> handleOutOfStockException(OutOfStockException exception, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = ExceptionResponse.of(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), request);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleProductNotFoundException(ProductNotFoundException exception, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = ExceptionResponse.of(HttpStatus.NOT_FOUND.value(), exception.getMessage(), request);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }
}
