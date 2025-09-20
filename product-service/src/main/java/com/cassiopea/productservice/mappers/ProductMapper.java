package com.cassiopea.productservice.mappers;

import com.cassiopea.productservice.dto.ProductRequest;
import com.cassiopea.productservice.dto.ProductResponse;
import com.cassiopea.productservice.entities.Product;

public class ProductMapper {
    public static ProductResponse toProductDTO(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .skuCode(product.getSkuCode())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

    public static Product toProductEntity(ProductRequest productDTO) {
        return Product.builder()
                .skuCode(productDTO.getSkuCode())
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .build();
    }
}
