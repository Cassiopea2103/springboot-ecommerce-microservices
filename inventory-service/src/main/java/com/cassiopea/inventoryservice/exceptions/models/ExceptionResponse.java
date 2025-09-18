package com.cassiopea.inventoryservice.exceptions.models;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ExceptionResponse {
    private Integer status ;
    private String message ;
    private String method ;
    private String path ;
    @Builder.Default
    private Date timestamp = new Date() ;
}
