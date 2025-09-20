package com.cassiopea.orderservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "com.cassiopea")
public class CustomProperties {
    private String inventoryServiceUrl;
}
