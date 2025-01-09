package com.fakestoreapi.clone.application.mapper;

import org.mapstruct.Mapper;

import com.fakestoreapi.clone.domain.entity.Category;
import com.fakestoreapi.clone.infrastructure.persistence.entities.CategoryEntity;

@Mapper
public interface CategoryMapper {

    CategoryEntity toEntity(Category category);

    Category toDomain(CategoryEntity entity);

}
