package com.fakestoreapi.clone.domain.repository;

import java.util.Optional;

import com.fakestoreapi.clone.domain.entity.Product;

public interface ProductRepository {

    Optional<Product> findById(Long id);

    Product save(Product product);

}
