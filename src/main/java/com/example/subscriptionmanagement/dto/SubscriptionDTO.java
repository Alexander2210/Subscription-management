package com.example.subscriptionmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import jakarta.validation.constraints.Size;


public record SubscriptionDTO(
        Long id,
        @NotBlank @Size(max = 50) String serviceName,
        LocalDateTime startDate,
        Long userId
) {}