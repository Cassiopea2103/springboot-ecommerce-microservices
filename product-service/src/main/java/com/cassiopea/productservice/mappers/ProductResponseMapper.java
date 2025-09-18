package com.cassiopea.productservice.mappers;

import com.cassiopea.productservice.dto.ProductResponse;
import com.cassiopea.productservice.entities.Product;

public class ProductResponseMapper {
    public static ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
