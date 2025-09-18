package com.cassiopea.productservice.exceptions.handler;

import com.cassiopea.productservice.exceptions.models.ExceptionResponse;
import com.cassiopea.productservice.exceptions.types.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // product not found :
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleProductNotFoundException(ProductNotFoundException exception) {
        // create error response payload :
        ExceptionResponse errorResponse = ExceptionResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(exception.getMessage())
                .build();

        // return response entity :
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
