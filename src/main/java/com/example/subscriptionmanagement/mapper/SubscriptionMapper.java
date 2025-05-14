package com.example.subscriptionmanagement.mapper;

import com.example.subscriptionmanagement.dto.SubscriptionDTO;
import com.example.subscriptionmanagement.entity.SubscriptionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {
    @Mapping(target = "userId", source = "user.id")
    SubscriptionDTO toDto(SubscriptionEntity subscription);

    @Mapping(target = "user", ignore = true)
    SubscriptionEntity toEntity(SubscriptionDTO dto);
}
