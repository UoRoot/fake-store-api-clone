package com.fakestoreapi.clone.application.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fakestoreapi.clone.application.service.interfaces.ICartService;
import com.fakestoreapi.clone.domain.entity.Cart;
import com.fakestoreapi.clone.domain.entity.CartItem;
import com.fakestoreapi.clone.domain.exception.ResourceNotFoundException;
import com.fakestoreapi.clone.domain.repository.CartItemRepository;
import com.fakestoreapi.clone.domain.repository.CartRepository;
import com.fakestoreapi.clone.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Cart createCart(Cart cart) {
        if (!userRepository.existsById(cart.getUser().getId())) {
            throw new ResourceNotFoundException("User with id " + cart.getUser().getId() + " does not exist");
        }

        List<CartItem> savedCartItems = cartItemRepository.saveAll(cart.getProducts());

        cart.setProducts(savedCartItems);

        return cartRepository.save(cart);
    }

    @Override
    @Transactional(readOnly = true)
    public Cart getCart(Long id) {
        Optional<Cart> cart = cartRepository.findById(id);

        if (cart.isEmpty()) {
            throw new ResourceNotFoundException("Cart with id " + id + " does not exist");
        }

        return cart.get();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    @Override
    @Transactional
    public Cart updateCart(Long id, Cart cart) {
        return null;
    }

    @Override
    @Transactional
    public Cart deleteCart(Long id) {
        return null;
    }

}
