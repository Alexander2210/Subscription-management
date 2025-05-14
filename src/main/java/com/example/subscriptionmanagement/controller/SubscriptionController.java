package com.example.subscriptionmanagement.controller;


import com.example.subscriptionmanagement.dto.SubscriptionDTO;
import com.example.subscriptionmanagement.dto.SubscriptionRequestDTO;
import com.example.subscriptionmanagement.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<SubscriptionDTO> addSubscription(
            @PathVariable Long userId,
            @Valid @RequestBody SubscriptionRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subscriptionService.addSubscription(userId, request));
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionDTO>> getSubscriptionsByUserId(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(subscriptionService.getUserSubscriptions(userId));
    }

    @DeleteMapping("/{subscriptionId}")
    public ResponseEntity<Void> deleteSubscription(
            @PathVariable Long userId,
            @PathVariable Long subscriptionId
    ) {
        subscriptionService.deleteSubscription(userId, subscriptionId);
        return ResponseEntity.noContent().build();
    }
}

