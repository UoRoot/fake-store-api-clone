package com.fakestoreapi.clone.infrastructure.persistence.repository.impl;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.fakestoreapi.clone.application.mapper.ProductMapper;
import com.fakestoreapi.clone.domain.entity.Product;
import com.fakestoreapi.clone.domain.repository.ProductRepository;
import com.fakestoreapi.clone.infrastructure.persistence.repository.interfaces.IProductRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {
    private final IProductRepository jpaProductRepository;
    private final ProductMapper mapper;

    @Override
    public Optional<Product> findById(Long id) {
        return jpaProductRepository.findById(id)
                .map(mapper::toDomain);
    }
}