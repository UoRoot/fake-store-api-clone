package com.fakestoreapi.clone.infrastructure.persistence.repository.impl;

import org.springframework.stereotype.Repository;

import com.fakestoreapi.clone.domain.repository.CategoryRepository;
import com.fakestoreapi.clone.infrastructure.persistence.repository.interfaces.ICategoryRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {
    private final ICategoryRepository jpaCategoryRepository;

    @Override
    public boolean existsById(Integer id) {
        return jpaCategoryRepository.existsById(id);
    }

}