package com.cassiopea.orderservice.exceptions.models;

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
}
