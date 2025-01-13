package com.fakestoreapi.clone.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fakestoreapi.clone.application.dto.request.user.UserSaveDTO;
import com.fakestoreapi.clone.application.dto.response.user.UserResponse;
import com.fakestoreapi.clone.domain.entity.User;

@Mapper
public interface UserMapper {
    
    @Mapping(target = "id", ignore = true)
    User toDomain(UserSaveDTO request);

    UserResponse toResponse(User user);

}
