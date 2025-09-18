package com.cassiopea.productservice.services;

import com.cassiopea.productservice.dto.ProductRequest;
import com.cassiopea.productservice.dto.ProductResponse;
import com.cassiopea.productservice.entities.Product;
import com.cassiopea.productservice.exceptions.types.ProductNotFoundException;
import com.cassiopea.productservice.mappers.ProductResponseMapper;
import com.cassiopea.productservice.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    // repository instance :
    private final ProductRepository productRepository;

    public Page<ProductResponse> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable)
                .map(ProductResponseMapper::toProductResponse);
    }

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        // save product to database :
        productRepository.save(product);

        return ProductResponseMapper.toProductResponse(product);
    }

    // get product by id :
    public ProductResponse getProductById(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + productId + " not found"));
        return ProductResponseMapper.toProductResponse(product);
    }

    public String deleteProduct(String productId) {
        // call the getProductById method to check if the product exists:
        getProductById(productId);
        productRepository.deleteById(productId);
        return "Product with id " + productId + " deleted successfully";
    }
}
