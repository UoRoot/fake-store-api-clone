package com.fakestoreapi.clone.application.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fakestoreapi.clone.application.service.interfaces.IProductService;
import com.fakestoreapi.clone.domain.entity.Product;
import com.fakestoreapi.clone.domain.exception.category.CategoryNotFoundException;
import com.fakestoreapi.clone.domain.repository.CategoryRepository;
import com.fakestoreapi.clone.domain.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Product createProduct(Product product) {
        if (!categoryRepository.existsById(product.getCategory().getId())) {
            throw new CategoryNotFoundException("La categoria con Id " +
                    product.getCategory().getId() + " no existe");
        }
        return productRepository.save(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> getProduct(Long id) {
        return productRepository.findById(id);
    }
}