package com.fakestoreapi.clone.infrastructure.persistence.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.fakestoreapi.clone.domain.entity.CartItem;
import com.fakestoreapi.clone.domain.repository.CartItemRepository;
import com.fakestoreapi.clone.infrastructure.persistence.entities.CartItemEntity;
import com.fakestoreapi.clone.infrastructure.persistence.entities.ProductEntity;
import com.fakestoreapi.clone.infrastructure.persistence.mapper.CartItemPersistenceMapper;
import com.fakestoreapi.clone.infrastructure.persistence.repository.interfaces.ICartItemRepository;
import com.fakestoreapi.clone.infrastructure.persistence.repository.interfaces.IProductRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CartItemRepositoryImpl implements CartItemRepository {
    private final ICartItemRepository jpaCartItemRepository;
    private final IProductRepository jpaProductRepository;
    private final CartItemPersistenceMapper mapper;

    @Override
    public Optional<CartItem> findById(Long id) {
        return jpaCartItemRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<CartItem> findAll() {
        return jpaCartItemRepository.findAll()
                .stream()
                .map(mapper::toDomain).toList();
    }

    @Override
    public CartItem save(CartItem cartItem) {
        CartItemEntity entity = mapper.toEntity(cartItem);
        ProductEntity product = jpaProductRepository.findById(cartItem.getProduct().getId()).get();

        entity.setProduct(product);
        return mapper.toDomain(jpaCartItemRepository.save(entity));
    }

    @Override
    public List<CartItem> saveAll(List<CartItem> cartItems) {
        List<Long> productIds = cartItems.stream().map(c -> c.getProduct().getId()).toList();
        List<ProductEntity> productEntities = jpaProductRepository.findAllById(productIds);

        List<CartItemEntity> cartItemEntities = new ArrayList<>();
        
        for (int i = 0; i < cartItems.size(); i++) {
            CartItemEntity cartItemEntity = mapper.toEntity(cartItems.get(i));
            cartItemEntity.setProduct(productEntities.get(i));
            cartItemEntities.add(cartItemEntity);
        }

        return jpaCartItemRepository.saveAll(cartItemEntities)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

}
