package com.fakestoreapi.clone.application.service.interfaces;

import java.util.List;

import com.fakestoreapi.clone.domain.entity.Cart;

public interface ICartService {

    Cart createCart(Cart cart);

    Cart getCart(Long id);

    List<Cart> getAllCarts();

    Cart updateCart(Long id, Cart cart);

    Cart deleteCart(Long id);

}