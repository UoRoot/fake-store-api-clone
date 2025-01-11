package com.fakestoreapi.clone.infrastructure.persistence.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.fakestoreapi.clone.domain.entity.User;
import com.fakestoreapi.clone.domain.repository.UserRepository;
import com.fakestoreapi.clone.infrastructure.persistence.entities.UserEntity;
import com.fakestoreapi.clone.infrastructure.persistence.mapper.UserPersistenceMapper;
import com.fakestoreapi.clone.infrastructure.persistence.repository.interfaces.IUserRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final IUserRepository jpaUserRepository;
    private final UserPersistenceMapper mapper;

    @Override
    public Optional<User> findById(Long id) {
        return jpaUserRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        return jpaUserRepository.findAll()
        .stream()
        .map(mapper::toDomain)
        .toList();
    }

    @Override
    public User save(User user) {
        UserEntity entity = mapper.toEntity(user);
        return mapper.toDomain(jpaUserRepository.save(entity));
    }

    @Override
    public User update(User user) {
        UserEntity entity = jpaUserRepository.findById(user.getId()).get();
        mapper.updateEntity(user, entity);
        return mapper.toDomain(jpaUserRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        jpaUserRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaUserRepository.existsById(id);
    }
}
