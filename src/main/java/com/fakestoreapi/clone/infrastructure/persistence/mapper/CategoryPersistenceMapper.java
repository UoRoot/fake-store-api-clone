package com.fakestoreapi.clone.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.fakestoreapi.clone.domain.entity.Category;
import com.fakestoreapi.clone.infrastructure.persistence.entities.CategoryEntity;

@Mapper
public interface CategoryPersistenceMapper {

    CategoryEntity toEntity(Category category);

    Category toDomain(CategoryEntity entity);

}