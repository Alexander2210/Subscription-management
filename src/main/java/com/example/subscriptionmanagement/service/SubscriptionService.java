package com.example.subscriptionmanagement.service;

import com.example.subscriptionmanagement.dto.SubscriptionDTO;
import com.example.subscriptionmanagement.dto.SubscriptionTopResponseDTO;
import com.example.subscriptionmanagement.entity.SubscriptionEntity;

import java.util.List;

public interface SubscriptionService {
    SubscriptionDTO addSubscription(Long userId, SubscriptionDTO subscription);

    List<SubscriptionDTO> getUserSubscriptions(Long userId);

    void deleteSubscription(Long userId, Long subscriptionId);

    List<SubscriptionTopResponseDTO> getTopSubscriptions();
}
