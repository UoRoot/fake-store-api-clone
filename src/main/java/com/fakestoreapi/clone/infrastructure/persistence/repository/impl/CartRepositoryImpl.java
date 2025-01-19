package com.fakestoreapi.clone.infrastructure.persistence.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.fakestoreapi.clone.domain.entity.Cart;
import com.fakestoreapi.clone.domain.repository.CartRepository;
import com.fakestoreapi.clone.infrastructure.persistence.entities.CartEntity;
import com.fakestoreapi.clone.infrastructure.persistence.entities.CartItemEntity;
import com.fakestoreapi.clone.infrastructure.persistence.entities.UserEntity;
import com.fakestoreapi.clone.infrastructure.persistence.mapper.CartPersistenceMapper;
import com.fakestoreapi.clone.infrastructure.persistence.repository.interfaces.ICartItemRepository;
import com.fakestoreapi.clone.infrastructure.persistence.repository.interfaces.ICartRepository;
import com.fakestoreapi.clone.infrastructure.persistence.repository.interfaces.IUserRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepository {
    private final ICartRepository jpaCartRepository;
    private final ICartItemRepository jpaCartItemRepository;
    private final IUserRepository jpaUserRepository;
    private final CartPersistenceMapper mapper;

    @Override
    public Optional<Cart> findById(Long id) {
        return jpaCartRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Cart save(Cart cart) {
        CartEntity entity = mapper.toEntity(cart);

        UserEntity userEntity = jpaUserRepository.findById(cart.getUser().getId()).get();
        entity.setUser(userEntity);

        List<Long> cartItemIds = cart.getProducts().stream().map(p -> p.getId()).toList();
        List<CartItemEntity> cartItemEntities = jpaCartItemRepository.findAllById(cartItemIds);

        entity.setProducts(cartItemEntities);

        return mapper.toDomain(jpaCartRepository.save(entity));
    }

    @Override
    public List<Cart> findAll() {
        return jpaCartRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Cart update(Cart cart) {
        return null;
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public boolean existsById(Long id) {
        return true;
    }
}
