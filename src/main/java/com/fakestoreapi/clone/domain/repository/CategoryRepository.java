package com.fakestoreapi.clone.domain.repository;

import com.fakestoreapi.clone.domain.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    Optional<Category> findById(Integer id);

    List<Category> findAll();

    boolean existsById(Integer id);

}
