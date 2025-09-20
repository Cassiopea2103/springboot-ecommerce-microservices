package com.cassiopea.productservice.services;

import com.cassiopea.productservice.dto.ProductRequest;
import com.cassiopea.productservice.dto.ProductResponse;
import com.cassiopea.productservice.entities.Product;
import com.cassiopea.productservice.exceptions.types.ProductAlreadyExistException;
import com.cassiopea.productservice.exceptions.types.ProductNotFoundException;
import com.cassiopea.productservice.mappers.ProductMapper;
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
                .map(ProductMapper::toProductDTO);
    }

    // get product by id :
    public ProductResponse getProductById(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + productId + " not found"));
        return ProductMapper.toProductDTO(product);
    }

    public void checkProductBySkuCode(String skuCode) {
        boolean exists = productRepository.existsBySkuCode(skuCode);
        if (!exists) throw new ProductNotFoundException("Product with SKU CODE " + skuCode + " not found.");
    }

    public ProductResponse createProduct(ProductRequest productRequest) {
        // check if sku code exists already :
        if (productRepository.existsBySkuCode(productRequest.getSkuCode())) {
            throw new ProductAlreadyExistException("Product with SKU " + productRequest.getSkuCode() + " already exists");
        }
        Product product = ProductMapper.toProductEntity(productRequest);

        // save product to database :
        Product savedProduct = productRepository.save(product);

        return ProductMapper.toProductDTO(savedProduct);
    }

    public String deleteProduct(String productId) {
        // call the getProductById method to check if the product exists:
        getProductById(productId);
        productRepository.deleteById(productId);
        return "Product with id " + productId + " deleted successfully";
    }
}
