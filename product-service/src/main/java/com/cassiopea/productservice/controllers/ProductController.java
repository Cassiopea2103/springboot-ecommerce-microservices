package com.cassiopea.productservice.controllers;

import com.cassiopea.productservice.dto.ProductRequest;
import com.cassiopea.productservice.dto.ProductResponse;
import com.cassiopea.productservice.services.ProductService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    // product service instance :
    private final ProductService productService;

    // get all products with pagination :
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<ProductResponse> products = productService.getAllProducts(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") String productId) {
        ProductResponse product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @GetMapping("/{skuCode}/price")
    public BigDecimal getPriceBySkuCode(@PathVariable String skuCode) {
        return productService.getPriceBySkuCode(skuCode);
    }

    @GetMapping("/sku/{skuCode}")
    public void checkProductBySkuCode(@PathVariable String skuCode) {
        productService.checkProductBySkuCode(skuCode);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request) {
        ProductResponse newProduct = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") String productId) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.deleteProduct(productId));
    }
}