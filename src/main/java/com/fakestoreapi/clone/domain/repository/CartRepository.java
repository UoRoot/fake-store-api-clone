package com.fakestoreapi.clone.domain.repository;

import java.util.List;
import java.util.Optional;

import com.fakestoreapi.clone.domain.entity.Cart;

public interface CartRepository {

    Optional<Cart> findById(Long id);

    Cart save(Cart cart);

    List<Cart> findAll();

    Cart update(Cart cart);

    void delete(Long id);

    boolean existsById(Long id);

}
