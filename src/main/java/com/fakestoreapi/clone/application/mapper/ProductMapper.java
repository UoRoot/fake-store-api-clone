package com.fakestoreapi.clone.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fakestoreapi.clone.application.dto.request.ProductRequest;
import com.fakestoreapi.clone.application.dto.response.ProductResponse;
import com.fakestoreapi.clone.domain.entity.Category;
import com.fakestoreapi.clone.domain.entity.Product;
import com.fakestoreapi.clone.infrastructure.persistence.entities.ProductEntity;

@Mapper
public interface ProductMapper {
    @Mapping(target = "category", source = "category.name")
    ProductResponse toResponse(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", source = "categoryId")
    Product toProduct(ProductRequest request);

    Product toDomain(ProductEntity entity);

    ProductEntity toEntity(Product product);

    default Category mapIdToCategory(Integer categoryId) {
        if (categoryId == null)
            return null;
        return Category.builder().id(categoryId).build();
    }
}
