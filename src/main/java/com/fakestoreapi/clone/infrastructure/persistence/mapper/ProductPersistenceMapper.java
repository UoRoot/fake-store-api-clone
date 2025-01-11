package com.fakestoreapi.clone.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.fakestoreapi.clone.domain.entity.Product;
import com.fakestoreapi.clone.infrastructure.persistence.entities.ProductEntity;

@Mapper(uses = { CategoryPersistenceMapper.class })
public interface ProductPersistenceMapper {

    Product toDomain(ProductEntity entity);

    ProductEntity toEntity(Product product);

    @Mapping(target = "id", ignore = true)
    void updateEntity(Product product, @MappingTarget ProductEntity entity);

}
