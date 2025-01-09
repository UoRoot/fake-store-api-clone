package com.fakestoreapi.clone.domain.repository;

import com.fakestoreapi.clone.domain.entity.Category;
import java.util.Optional;

public interface CategoryRepository {

    Optional<Category> findById(Integer id);

    boolean existsById(Integer id);

}
