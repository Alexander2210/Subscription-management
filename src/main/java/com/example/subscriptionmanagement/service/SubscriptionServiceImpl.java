package com.example.subscriptionmanagement.service;

import com.example.subscriptionmanagement.dto.SubscriptionDTO;
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

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService{

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final SubscriptionMapper subscriptionMapper;

    @Transactional
    public SubscriptionDTO addSubscription(Long userId, SubscriptionDTO subscriptionDTO) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        SubscriptionEntity subscription = subscriptionMapper.toEntity(subscriptionDTO);
        subscription.setUser(user);

        log.info("Adding subscription {} to user {}", subscriptionDTO.serviceName(), userId);
        return subscriptionMapper.toDto(subscriptionRepository.save(subscription));
    }

    public List<SubscriptionDTO> getUserSubscriptions(Long userId) {
        return subscriptionRepository.findByUserId(userId).stream()
                .map(subscriptionMapper::toDto)
                .toList();
    }

    @Transactional
    public void deleteSubscription(Long userId, Long subscriptionId) {
        SubscriptionEntity subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new EntityNotFoundException("Subscription not found"));

        if (!subscription.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Subscription does not belong to user");
        }

        subscriptionRepository.deleteById(subscriptionId);
        log.info("Deleted subscription {} for user {}", subscriptionId, userId);
    }

    public List<SubscriptionTopResponseDTO> getTopSubscriptions() {
        return subscriptionRepository.findTop3Subscriptions().stream()
                .map(result -> new SubscriptionTopResponseDTO(
                        (String) result[0],
                        ((Long) result[1]).intValue()
                ))
                .toList();
    }
}

