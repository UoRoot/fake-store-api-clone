package com.fakestoreapi.clone.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fakestoreapi.clone.application.dto.request.cart.CartRequest;
import com.fakestoreapi.clone.application.dto.response.cart.CartResponse;
import com.fakestoreapi.clone.domain.entity.Cart;
import com.fakestoreapi.clone.domain.entity.Product;
import com.fakestoreapi.clone.domain.entity.User;

@Mapper
public interface CartMapper {

    @Mapping(target = "id", ignore = true)
    Cart toDomain(CartRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products.productId", source = "products.product.id" )
    @Mapping(target = "userId", source = "user.id" )
    CartResponse toResponse(Cart cart);

    default Product mapIdToProduct(Long productId) {
        if (productId == null)
            return null;
        return Product.builder().id(productId).build();
    }

    default User mapIdToUser(Long userId) {
        if (userId == null)
            return null;
        return User.builder().id(userId).build();
    }
}
