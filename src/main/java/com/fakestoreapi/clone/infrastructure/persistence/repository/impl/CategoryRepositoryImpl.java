package com.fakestoreapi.clone.infrastructure.persistence.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.fakestoreapi.clone.domain.entity.Category;
import com.fakestoreapi.clone.domain.repository.CategoryRepository;
import com.fakestoreapi.clone.infrastructure.persistence.mapper.CategoryPersistenceMapper;
import com.fakestoreapi.clone.infrastructure.persistence.repository.interfaces.ICategoryRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {
    private final ICategoryRepository jpaCategoryRepository;
    private final CategoryPersistenceMapper mapper;

    @Override
    public boolean existsById(Integer id) {
        return jpaCategoryRepository.existsById(id);
    }

    @Override
    public Optional<Category> findById(Integer id) {
        return jpaCategoryRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Category> findAll() {
        return jpaCategoryRepository.findAll()
            .stream()
            .map(mapper::toDomain)
            .toList();
    }

}