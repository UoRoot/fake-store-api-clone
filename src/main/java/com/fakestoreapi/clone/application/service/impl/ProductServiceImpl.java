package com.fakestoreapi.clone.application.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fakestoreapi.clone.application.service.interfaces.IProductService;
import com.fakestoreapi.clone.domain.entity.Product;
import com.fakestoreapi.clone.domain.exception.category.CategoryNotFoundException;
import com.fakestoreapi.clone.domain.exception.product.ProductNotFoundException;
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
        Optional<Product> product = productRepository.findById(id);
        
        if (product.isEmpty()) {
            throw new ProductNotFoundException("El producto con id " + id + " no existe");
        }

        return productRepository.findById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}