package com.fakestoreapi.clone.application.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.fakestoreapi.clone.domain.entity.Product;

public interface IProductService {

    Product createProduct(Product product);

    Optional<Product> getProduct(Long id);

    List<Product> getAllProducts();

    Optional<Product> deleteProduct(Long id);
}
