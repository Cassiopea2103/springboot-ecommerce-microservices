package com.cassiopea.orderservice.exceptions.handler;

import com.cassiopea.orderservice.exceptions.models.ExceptionResponse;
import com.cassiopea.orderservice.exceptions.types.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // product not found :
    @ExceptionHandler ( OrderNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleProductNotFoundException ( OrderNotFoundException ex ) {
        // build error response :
        ExceptionResponse errorResponse = ExceptionResponse.builder()
                .status(HttpStatus.NOT_FOUND.value() )
                .message ( ex.getMessage () )
                .build() ;

        return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( errorResponse ) ;
    }

    @ExceptionHandler ( MissingServletRequestParameterException.class)
    public ResponseEntity<ExceptionResponse> handleMissingParameterException ( MissingServletRequestParameterException exception ) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .status ( HttpStatus.BAD_REQUEST.value() )
                .message ( exception.getMessage() )
                .build() ;

        return ResponseEntity.status ( HttpStatus.BAD_REQUEST).body ( exceptionResponse ) ;
    }

    // default error handler :
    @ExceptionHandler ( Exception.class )
    public ResponseEntity<ExceptionResponse> handleGenericException ( Exception exception ) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .status ( HttpStatus.INTERNAL_SERVER_ERROR.value () )
                .message (exception.getMessage())
                .build() ;

        return ResponseEntity.status ( HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
    }
}
