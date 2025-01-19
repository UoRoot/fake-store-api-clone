package com.fakestoreapi.clone.infrastructure.persistence.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fakestoreapi.clone.infrastructure.persistence.entities.CartItemEntity;

public interface ICartItemRepository extends JpaRepository<CartItemEntity, Long> {
    
}
