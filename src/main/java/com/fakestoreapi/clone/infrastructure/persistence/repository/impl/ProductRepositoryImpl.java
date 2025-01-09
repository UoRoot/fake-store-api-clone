package com.fakestoreapi.clone.infrastructure.persistence.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.fakestoreapi.clone.application.mapper.ProductMapper;
import com.fakestoreapi.clone.domain.entity.Product;
import com.fakestoreapi.clone.domain.repository.ProductRepository;
import com.fakestoreapi.clone.infrastructure.persistence.entities.ProductEntity;
import com.fakestoreapi.clone.infrastructure.persistence.repository.interfaces.IProductRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {
    private final IProductRepository jpaProductRepository;
    private final ProductMapper mapper;

    @Override
    public Product save(Product product) {
        ProductEntity entity = mapper.toEntity(product);
        return mapper.toDomain(jpaProductRepository.save(entity));
    }

    @Override
    public Optional<Product> findById(Long id) {
        return jpaProductRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return jpaProductRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}