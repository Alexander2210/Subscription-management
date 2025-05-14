package com.example.subscriptionmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record UserDTO(
        Long id,
        @NotBlank @Size(max = 100) String name,
        @Email String email,
        LocalDateTime createdAt
) {}