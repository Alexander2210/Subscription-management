package com.example.subscriptionmanagement.mapper;

import com.example.subscriptionmanagement.dto.UserDTO;
import com.example.subscriptionmanagement.entity.SubscriptionEntity;
import com.example.subscriptionmanagement.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "subscriptionIds", source = "subscriptions")
    UserDTO toDto(UserEntity user);

    @Mapping(target = "subscriptions", ignore = true)
    UserEntity toEntity(UserDTO userDTO);

    default List<Long> mapSubscriptions(List<SubscriptionEntity> subscriptions) {
        return subscriptions.stream()
                .map(SubscriptionEntity::getId)
                .toList();
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "subscriptions", ignore = true)
    void updateFromDto(UserDTO dto, @MappingTarget UserEntity entity);
}