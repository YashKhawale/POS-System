package com.sale.pos_system.mapper;

import com.sale.pos_system.model.User;
import com.sale.pos_system.payload.dto.UserDto;

public class UserMapper {
    public static UserDto toDTO(User savedUser) {
        UserDto userDto = new UserDto();
        userDto.setFullName(savedUser.getFullName());
        userDto.setId(savedUser.getId());
        userDto.setEmail(savedUser.getEmail());
        userDto.setRole(savedUser.getRole());
        userDto.setPhone(savedUser.getPhone());
        userDto.setCreatedAt(savedUser.getCreatedAt());
        userDto.setUpdatedAt(savedUser.getUpdatedAt());
        userDto.setLastLogin(savedUser.getLastLogin());
        userDto.setStoreId(savedUser.getStore() != null ? savedUser.getStore().getId() : null);
        userDto.setBranchId(savedUser.getBranch() != null ? savedUser.getBranch().getId() : null);

        return userDto;
    }

    public static User toEntity(UserDto userDto) {
        User createdUser = new User();
        createdUser.setEmail(userDto.getEmail());
        createdUser.setFullName(userDto.getFullName());
        createdUser.setRole(userDto.getRole());
        createdUser.setCreatedAt(userDto.getCreatedAt());
        createdUser.setUpdatedAt(userDto.getUpdatedAt());
        createdUser.setLastLogin(userDto.getLastLogin());
        createdUser.setPhone(userDto.getPhone());
        createdUser.setPassword(userDto.getPassword());

        return createdUser;
    }
}
