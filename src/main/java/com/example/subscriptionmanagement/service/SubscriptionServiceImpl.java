package com.example.subscriptionmanagement.service;

import com.example.subscriptionmanagement.dto.SubscriptionDTO;
import com.example.subscriptionmanagement.dto.SubscriptionRequestDTO;
import com.example.subscriptionmanagement.dto.SubscriptionTopResponseDTO;
import com.example.subscriptionmanagement.entity.SubscriptionEntity;
import com.example.subscriptionmanagement.entity.UserEntity;
import com.example.subscriptionmanagement.mapper.SubscriptionMapper;
import com.example.subscriptionmanagement.repository.SubscriptionRepository;
import com.example.subscriptionmanagement.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;

    public SubscriptionDTO addSubscription(Long userId, SubscriptionRequestDTO request) {
        log.info("Adding subscription '{}' for user ID={}", request.serviceName(), userId);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User ID={} not found for subscription", userId);
                    return new EntityNotFoundException("User not found");
                });

        try {
            SubscriptionEntity subscription = new SubscriptionEntity();
            subscription.setServiceName(request.serviceName());
            subscription.setStartDate(LocalDateTime.now());
            subscription.setUser(user);

            SubscriptionEntity saved = subscriptionRepository.save(subscription);
            log.debug("Subscription created: ID={}", saved.getId());
            return subscriptionMapper.toDto(saved);
        } catch (Exception ex) {
            log.error("Error creating subscription: {}", ex.getMessage());
            throw new RuntimeException("Subscription creation failed");
        }
    }

    @Transactional(readOnly = true)
    public List<SubscriptionDTO> getUserSubscriptions(Long userId) {
        log.debug("Fetching subscriptions for user ID={}", userId);
        if (!userRepository.existsById(userId)) {
            log.warn("User ID={} not found for subscriptions", userId);
            throw new EntityNotFoundException("User not found");
        }

        try {
            return subscriptionRepository.findByUserId(userId).stream()
                    .peek(sub -> log.trace("Processing subscription ID={}", sub.getId()))
                    .map(subscriptionMapper::toDto)
                    .toList();
        } catch (Exception ex) {
            log.error("Error fetching subscriptions: {}", ex.getMessage());
            throw new RuntimeException("Failed to retrieve subscriptions");
        }
    }

    public void deleteSubscription(Long userId, Long subscriptionId) {
        log.info("Deleting subscription ID={} for user ID={}", subscriptionId, userId);

        SubscriptionEntity subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> {
                    log.error("Subscription ID={} not found", subscriptionId);
                    return new EntityNotFoundException("Subscription not found");
                });

        if (!subscription.getUser().getId().equals(userId)) {
            log.warn("User ID={} attempted to delete foreign subscription ID={}", userId, subscriptionId);
            throw new IllegalArgumentException("Subscription does not belong to user");
        }

        try {
            subscriptionRepository.delete(subscription);
            log.debug("Subscription ID={} deleted", subscriptionId);
        } catch (Exception ex) {
            log.error("Error deleting subscription: {}", ex.getMessage());
            throw new RuntimeException("Subscription deletion failed");
        }
    }

    @Transactional(readOnly = true)
    public List<SubscriptionTopResponseDTO> getTopSubscriptions() {
        log.info("Fetching top 3 subscriptions");
        try {
            return subscriptionRepository.findTop3Subscriptions().stream()
                    .map(result -> {
                        log.trace("Processing service: {}", result[0]);
                        return new SubscriptionTopResponseDTO(
                                (String) result[0],
                                ((Long) result[1]).intValue()
                        );
                    })
                    .toList();
        } catch (Exception ex) {
            log.error("Error fetching top subscriptions: {}", ex.getMessage());
            throw new RuntimeException("Failed to retrieve top subscriptions");
        }
    }
}

