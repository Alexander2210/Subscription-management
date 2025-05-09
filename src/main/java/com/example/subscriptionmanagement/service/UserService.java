package com.example.subscriptionmanagement.service;

import com.example.subscriptionmanagement.dto.UserDTO;
import com.example.subscriptionmanagement.entity.UserEntity;

public interface UserService {
    UserDTO createUser(UserDTO user);

    UserDTO getUserById(Long id);

    UserDTO updateUser(Long id, UserDTO userDetails);

    void deleteUser(Long id);
}
