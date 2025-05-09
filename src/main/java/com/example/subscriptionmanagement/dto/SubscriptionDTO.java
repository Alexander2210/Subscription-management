package com.example.subscriptionmanagement.dto;

import java.time.LocalDateTime;

public record SubscriptionDTO(
        Long id,
        String serviceName,
        LocalDateTime startDate,
        Long userId
) {
}
