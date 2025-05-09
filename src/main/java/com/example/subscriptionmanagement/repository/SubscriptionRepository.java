package com.example.subscriptionmanagement.repository;

import com.example.subscriptionmanagement.entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {
    List<SubscriptionEntity> findByUserId(Long userId);
    @Query("SELECT s.serviceName, COUNT(s) as count FROM SubscriptionEntity s GROUP BY s.serviceName ORDER BY count DESC LIMIT 3")
    List<Object[]> findTop3Subscriptions();
}
