package com.fakestoreapi.clone.application.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.fakestoreapi.clone.domain.entity.Product;

public interface IProductService {

    Product createProduct(Product product);

    Optional<Product> getProduct(Long id);

    List<Product> getAllProducts();

    List<Product> getProductsByCategoryName(String categoryName);

    Product updateProduct(Long id, Product product);

    Optional<Product> deleteProduct(Long id);
}
