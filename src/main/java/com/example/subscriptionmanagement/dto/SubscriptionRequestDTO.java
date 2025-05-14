package com.example.subscriptionmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SubscriptionRequestDTO(
        @NotBlank @Size(max = 50) String serviceName
) {
}

