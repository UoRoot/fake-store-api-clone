package com.fakestoreapi.clone.application.service.interfaces;

import java.util.List;

import com.fakestoreapi.clone.domain.entity.User;

public interface IUserService {

    User createUser(User user);

    User getUser(Long id);

    List<User> getAllUsers();

    User updateUser(Long id, User user);

    User deleteUser(Long id);

}
