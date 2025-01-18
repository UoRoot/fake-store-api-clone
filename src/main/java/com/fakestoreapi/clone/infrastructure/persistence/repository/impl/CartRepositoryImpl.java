package com.fakestoreapi.clone.infrastructure.persistence.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.fakestoreapi.clone.domain.entity.Cart;
import com.fakestoreapi.clone.domain.repository.CartRepository;
import com.fakestoreapi.clone.infrastructure.persistence.entities.CartEntity;
import com.fakestoreapi.clone.infrastructure.persistence.mapper.CartPersistenceMapper;
import com.fakestoreapi.clone.infrastructure.persistence.repository.interfaces.ICartRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepository {
    private final ICartRepository jpaCartRepository;
    private final CartPersistenceMapper mapper;

    @Override
    public Optional<Cart> findById(Long id) {
        return jpaCartRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Cart save(Cart cart) {
        CartEntity entity = mapper.toEntity(cart);
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
        CartEntity entity = jpaCartRepository.findById(cart.getId()).get();
        mapper.updateEntity(cart, entity);
        return mapper.toDomain(jpaCartRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        jpaCartRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaCartRepository.existsById(id);
    }
}
