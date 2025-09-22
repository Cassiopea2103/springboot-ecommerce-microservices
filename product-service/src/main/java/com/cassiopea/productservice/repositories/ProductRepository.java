package com.cassiopea.productservice.repositories;

import com.cassiopea.productservice.entities.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    boolean existsBySkuCode(String skuCode);

    Product findBySkuCode(String skuCode);
}
