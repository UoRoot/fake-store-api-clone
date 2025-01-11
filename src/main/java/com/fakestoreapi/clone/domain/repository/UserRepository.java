package com.fakestoreapi.clone.domain.repository;

import java.util.List;
import java.util.Optional;

import com.fakestoreapi.clone.domain.entity.User;

public interface UserRepository {

    Optional<User> findById(Long id);
    
    List<User> findAll();

    User save(User user);

    User update(User user);

    void delete(Long id);

    boolean existsById(Long id);

}
