package com.fakestoreapi.clone.domain.repository;

import java.util.List;
import java.util.Optional;

import com.fakestoreapi.clone.domain.entity.CartItem;

public interface CartItemRepository {
    
    Optional<CartItem> findById(Long id);

    List<CartItem> findAll();
    
    CartItem save(CartItem cartItem);

    List<CartItem> saveAll(List<CartItem> cartItems);

}
