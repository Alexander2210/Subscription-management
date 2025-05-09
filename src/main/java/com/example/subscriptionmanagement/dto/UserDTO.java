package com.example.subscriptionmanagement.dto;

import java.time.LocalDateTime;
import java.util.List;

public record UserDTO(
        Long id,
        String name,
        String email,
        LocalDateTime createdAt,
        List<Long> subscriptionIds
) {
}
