package com.cassiopea.productservice.exceptions.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class ExceptionResponse {
    private int status;
    private String message;
    @Builder.Default
    private Date timestamp = new Date();
}
