package com.fakestoreapi.clone.application.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fakestoreapi.clone.application.service.interfaces.IProductService;
import com.fakestoreapi.clone.domain.entity.Category;
import com.fakestoreapi.clone.domain.entity.Product;
import com.fakestoreapi.clone.domain.exception.ResourceNotFoundException;
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
        Optional<Category> category = categoryRepository.findById(product.getCategory().getId());

        if (category.isEmpty()) {
            throw new ResourceNotFoundException("La categoria con Id " +
                    product.getCategory().getId() + " no existe");
        }

        product.setCategory(category.get());
        return productRepository.save(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            throw new ResourceNotFoundException("El producto con id " + id + " no existe");
        }

        return product.get();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public Product updateProduct(Long id, Product product) {

        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("El producto con Id " + id + " no existe");
        }

        Optional<Category> category = categoryRepository.findById(product.getCategory().getId());

        if (category.isEmpty()) {
            throw new ResourceNotFoundException("La categoria con Id " +
                    product.getCategory().getId() + " no existe");
        }

        product.setId(id);
        product.setCategory(category.get());
        return productRepository.update(product);
    }

    @Override
    @Transactional
    public Product deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            throw new ResourceNotFoundException("El producto con id " + id + " no existe");
        }

        productRepository.delete(id);

        return product.get();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getProductsByCategoryName(String categoryName) {
        return productRepository.findByCategoryName(categoryName);
    }

}