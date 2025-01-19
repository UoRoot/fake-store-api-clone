package com.fakestoreapi.clone.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fakestoreapi.clone.application.dto.request.cart.CartRequest;
import com.fakestoreapi.clone.application.dto.response.cart.CartResponse;
import com.fakestoreapi.clone.domain.entity.CartItem;
import com.fakestoreapi.clone.domain.entity.Product;

@Mapper
public interface CartItemMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", source = "productId")
    CartItem toDomain(CartRequest.CartItem request);

    @Mapping(target = "productId", source = "product.id")
    CartResponse.CartItem toResponse(CartItem domain);

    default Product mapIdToProduct(Long productId) {
        if (productId == null)
            return null;
        return Product.builder().id(productId).build();
    }

    default Long mapProductToId(Product product) {
        if (product == null)
            return null;
        return product.getId();
    }

}
