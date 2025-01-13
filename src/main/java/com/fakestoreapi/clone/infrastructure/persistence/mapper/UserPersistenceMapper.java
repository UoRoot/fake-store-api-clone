package com.fakestoreapi.clone.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.fakestoreapi.clone.domain.entity.User;
import com.fakestoreapi.clone.infrastructure.persistence.entities.UserEntity;

@Mapper
public interface UserPersistenceMapper {
    
    User toDomain(UserEntity entity);
    
    UserEntity toEntity(User user);

    @Mapping(target = "id", ignore = true)
    void updateEntity(User product, @MappingTarget UserEntity entity);

}
