package com.example.subscriptionmanagement.service;

import com.example.subscriptionmanagement.dto.SubscriptionDTO;
import com.example.subscriptionmanagement.dto.SubscriptionRequestDTO;
import com.example.subscriptionmanagement.dto.SubscriptionTopResponseDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface SubscriptionService {
    SubscriptionDTO addSubscription(Long userId, SubscriptionRequestDTO request);
    List<SubscriptionDTO> getUserSubscriptions(Long userId);

    void deleteSubscription(Long userId, Long subscriptionId);

    List<SubscriptionTopResponseDTO> getTopSubscriptions();
}
