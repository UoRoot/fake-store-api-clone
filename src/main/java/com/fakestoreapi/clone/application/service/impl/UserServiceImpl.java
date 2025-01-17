package com.fakestoreapi.clone.application.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fakestoreapi.clone.application.service.interfaces.IUserService;
import com.fakestoreapi.clone.domain.entity.User;
import com.fakestoreapi.clone.domain.exception.ResourceNotFoundException;
import com.fakestoreapi.clone.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public User getUser(Long id) {
        Optional<User> product = userRepository.findById(id);

        if (product.isEmpty()) {
            throw new ResourceNotFoundException("User with id " + id + " does not exist");
        }

        return product.get();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }


    @Override
    @Transactional
    public User updateUser(Long id, User user) {

        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User with Id " + id + " does not exist");
        }
        user.setId(id);
        return userRepository.update(user);
    }

    @Override
    @Transactional
    public User deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User with id " + id + " does not exist");
        }

        userRepository.delete(id);

        return user.get();
    }
    
}
