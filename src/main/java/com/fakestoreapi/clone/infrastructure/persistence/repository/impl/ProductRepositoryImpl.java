package com.fakestoreapi.clone.infrastructure.persistence.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.fakestoreapi.clone.domain.entity.Product;
import com.fakestoreapi.clone.domain.repository.ProductRepository;
import com.fakestoreapi.clone.infrastructure.persistence.entities.ProductEntity;
import com.fakestoreapi.clone.infrastructure.persistence.mapper.ProductPersistenceMapper;
import com.fakestoreapi.clone.infrastructure.persistence.repository.interfaces.IProductRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {
    private final IProductRepository jpaProductRepository;
    private final ProductPersistenceMapper mapper;

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

    @Override
    public Product update(Product product) {
        ProductEntity entity = jpaProductRepository.findById(product.getId()).get();
        mapper.updateEntity(product, entity);
        return mapper.toDomain(jpaProductRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        jpaProductRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaProductRepository.existsById(id);
    }

    @Override
    public List<Product> findByCategoryName(String categoryName) {
        return jpaProductRepository.findByCategoryName(categoryName)
            .stream()
            .map(mapper::toDomain)
            .toList();
    }

}