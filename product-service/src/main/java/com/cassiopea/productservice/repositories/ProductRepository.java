package com.cassiopea.productservice.repositories;

import com.cassiopea.productservice.entities.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    boolean existsBySkuCode(String skuCode);
}
