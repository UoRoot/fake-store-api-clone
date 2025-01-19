package com.fakestoreapi.clone.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fakestoreapi.clone.application.dto.request.cart.CartRequest;
import com.fakestoreapi.clone.application.dto.response.cart.CartResponse;
import com.fakestoreapi.clone.domain.entity.Cart;
import com.fakestoreapi.clone.domain.entity.User;

@Mapper(uses = { CartItemMapper.class })
public interface CartMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "userId")
    Cart toDomain(CartRequest request);

    @Mapping(target = "userId", source = "user")
    CartResponse toResponse(Cart domain);

    default User mapIdToUser(Long userId) {
        if (userId == null)
            return null;
        return User.builder().id(userId).build();
    }

    default Long mapUserToId(User user) {
        if (user == null)
            return null;
        return user.getId();
    }

}
