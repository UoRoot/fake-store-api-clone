package com.fakestoreapi.clone.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.fakestoreapi.clone.domain.entity.Cart;
import com.fakestoreapi.clone.domain.entity.User;
import com.fakestoreapi.clone.infrastructure.persistence.entities.CartEntity;
import com.fakestoreapi.clone.infrastructure.persistence.entities.UserEntity;

@Mapper(uses = { CartItemPersistenceMapper.class } )
public interface CartPersistenceMapper {

    Cart toDomain(CartEntity entity);

    CartEntity toEntity(Cart domain);

    default User fromEntityToProductDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return User.builder().id(entity.getId()).build();
    }

    default UserEntity fromDomainToProductEntity(User domain) {
        if (domain == null) {
            return null;
        }
        UserEntity entity = new UserEntity();
        entity.setId(domain.getId());

        return entity;
    }

}
