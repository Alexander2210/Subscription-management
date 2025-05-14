package com.example.subscriptionmanagement.service;

import com.example.subscriptionmanagement.dto.UserDTO;
import com.example.subscriptionmanagement.entity.UserEntity;
import com.example.subscriptionmanagement.mapper.UserMapper;
import com.example.subscriptionmanagement.repository.SubscriptionRepository;
import com.example.subscriptionmanagement.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UserMapper userMapper;

    public UserDTO createUser(UserDTO userDTO) {
        log.info("Attempting to create user: {}", userDTO.email());
        try {
            UserEntity user = userMapper.toEntity(userDTO);
            user = userRepository.save(user);
            log.debug("User created successfully: ID={}", user.getId());
            return userMapper.toDto(user);
        } catch (DataIntegrityViolationException ex) {
            log.error("Database error while creating user: {}", ex.getMessage());
            throw new IllegalArgumentException("Email already exists");
        }
    }

    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        log.debug("Fetching user with ID: {}", id);
        return userRepository.findById(id)
                .map(user -> {
                    log.debug("Found user: {}", user.getEmail());
                    return userMapper.toDto(user);
                })
                .orElseThrow(() -> {
                    log.warn("User not found: ID={}", id);
                    return new EntityNotFoundException("User not found");
                });
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        log.info("Updating user ID={}", id);
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Update failed: User ID={} not found", id);
                    return new EntityNotFoundException("User not found");
                });

        try {
            existingUser.setName(userDTO.name());
            existingUser.setEmail(userDTO.email());
            UserEntity updatedUser = userRepository.save(existingUser);
            log.info("User ID={} updated successfully", id);
            return userMapper.toDto(updatedUser);
        } catch (DataIntegrityViolationException ex) {
            log.error("Email conflict during update: {}", userDTO.email());
            throw new IllegalArgumentException("Email already in use");
        }
    }

    public void deleteUser(Long id) {
        log.info("Deleting user ID={}", id);
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Delete failed: User ID={} not found", id);
                    return new EntityNotFoundException("User not found");
                });

        try {
            subscriptionRepository.deleteByUserId(id);
            userRepository.delete(user);
            log.info("User ID={} and related subscriptions deleted", id);
        } catch (Exception ex) {
            log.error("Error deleting user ID={}: {}", id, ex.getMessage());
            throw new RuntimeException("User deletion failed");
        }
    }
}
