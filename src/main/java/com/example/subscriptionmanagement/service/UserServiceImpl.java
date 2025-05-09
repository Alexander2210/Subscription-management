package com.example.subscriptionmanagement.service;

import com.example.subscriptionmanagement.dto.UserDTO;
import com.example.subscriptionmanagement.entity.UserEntity;
import com.example.subscriptionmanagement.mapper.UserMapper;
import com.example.subscriptionmanagement.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        log.info("Creating user: {}", userDTO.email());
        UserEntity user = userMapper.toEntity(userDTO);
        return userMapper.toDto(userRepository.save(user));
    }

    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        userMapper.updateFromDto(userDTO, existingUser);
        return userMapper.toDto(userRepository.save(existingUser));
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        log.info("Deleted user with id: {}", id);
    }
}
