package com.cassiopea.inventoryservice.exceptions.models;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ExceptionResponse {

    private Integer status;
    private String method;
    private String path;
    private String message;
    @Builder.Default
    private Date timestamp = new Date();

    public static ExceptionResponse of(Integer status, String message, HttpServletRequest request) {
        return ExceptionResponse.builder()
                .status(status)
                .method(request.getMethod())
                .path(request.getRequestURI())
                .message(message)
                .build();
    }
}
