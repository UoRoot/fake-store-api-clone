package com.fakestoreapi.clone.infrastructure.persistence.repository.impl;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.fakestoreapi.clone.application.mapper.CategoryMapper;
import com.fakestoreapi.clone.domain.entity.Category;
import com.fakestoreapi.clone.domain.repository.CategoryRepository;
import com.fakestoreapi.clone.infrastructure.persistence.repository.interfaces.ICategoryRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {
    private final ICategoryRepository jpaCategoryRepository;
    private final CategoryMapper mapper;

    @Override
    public boolean existsById(Integer id) {
        return jpaCategoryRepository.existsById(id);
    }

    @Override
    public Optional<Category> findById(Integer id) {
        return jpaCategoryRepository.findById(id)
                .map(mapper::toDomain);
    }

}