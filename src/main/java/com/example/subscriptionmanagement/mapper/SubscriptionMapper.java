package com.example.subscriptionmanagement.mapper;

import com.example.subscriptionmanagement.dto.SubscriptionDTO;
import com.example.subscriptionmanagement.dto.SubscriptionTopResponseDTO;
import com.example.subscriptionmanagement.entity.SubscriptionEntity;
import com.example.subscriptionmanagement.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    @Mapping(target = "userId", source = "user.id")
    SubscriptionDTO toDto(SubscriptionEntity subscription);

    @Mapping(target = "user", ignore = true)
    SubscriptionEntity toEntity(SubscriptionDTO dto);

    @Mapping(target = "subscribersCount", ignore = true)
    SubscriptionTopResponseDTO toTopResponse(SubscriptionEntity subscription);

    default UserEntity mapUserIdToUser(Long userId) {
        if (userId == null) return null;
        UserEntity user = new UserEntity();
        user.setId(userId);
        return user;
    }
}
