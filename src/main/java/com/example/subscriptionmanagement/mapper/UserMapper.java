package com.example.subscriptionmanagement.mapper;

import com.example.subscriptionmanagement.dto.UserDTO;
import com.example.subscriptionmanagement.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(UserEntity user);
    UserEntity toEntity(UserDTO dto);
}