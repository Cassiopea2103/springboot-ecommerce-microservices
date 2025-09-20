package com.cassiopea.productservice.exceptions.handler;

import com.cassiopea.productservice.exceptions.models.ExceptionResponse;
import com.cassiopea.productservice.exceptions.types.ProductAlreadyExistException;
import com.cassiopea.productservice.exceptions.types.ProductNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // product not found :
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleProductNotFoundException(ProductNotFoundException exception, HttpServletRequest request) {
        ExceptionResponse errorResponse = ExceptionResponse.of(HttpStatus.NOT_FOUND.value(), exception.getMessage(), request);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ProductAlreadyExistException.class)
    public ResponseEntity<ExceptionResponse> handleProductAlreadyExistException(ProductAlreadyExistException exception, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = ExceptionResponse.of(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }
}
