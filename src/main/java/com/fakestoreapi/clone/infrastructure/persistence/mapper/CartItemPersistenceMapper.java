package com.fakestoreapi.clone.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.fakestoreapi.clone.domain.entity.CartItem;
import com.fakestoreapi.clone.domain.entity.Product;
import com.fakestoreapi.clone.infrastructure.persistence.entities.CartItemEntity;
import com.fakestoreapi.clone.infrastructure.persistence.entities.ProductEntity;

@Mapper
public interface CartItemPersistenceMapper {

    CartItem toDomain(CartItemEntity entity);

    CartItemEntity toEntity(CartItem domain);

    default Product fromEntityToProductDomain(ProductEntity product) {
        if (product == null) {
            return null;
        }
        return Product.builder().id(product.getId()).build();
    }

    default ProductEntity fromDomainToProductEntity(Product product) {
        if (product == null) {
            return null;
        }
        ProductEntity entity = new ProductEntity();
        entity.setId(product.getId());

        return entity;
    }

}
