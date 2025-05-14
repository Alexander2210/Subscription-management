package com.example.subscriptionmanagement.repository;

import com.example.subscriptionmanagement.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
