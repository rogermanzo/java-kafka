package com.api.saludapp.user.mapper;

import com.api.saludapp.user.dto.UserCreateDto;
import com.api.saludapp.user.dto.UserDto;
import com.api.saludapp.user.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        
        return new UserDto(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getPhone(),
            user.getDateOfBirth(),
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
    }

    public User toEntity(UserCreateDto userCreateDto) {
        if (userCreateDto == null) {
            return null;
        }
        
        User user = new User();
        user.setName(userCreateDto.getName());
        user.setEmail(userCreateDto.getEmail());
        user.setPhone(userCreateDto.getPhone());
        user.setPassword(userCreateDto.getPassword());
        user.setDateOfBirth(userCreateDto.getDateOfBirth());
        
        return user;
    }

    public void updateEntity(User existingUser, UserCreateDto userCreateDto) {
        if (existingUser == null || userCreateDto == null) {
            return;
        }
        
        existingUser.setName(userCreateDto.getName());
        existingUser.setEmail(userCreateDto.getEmail());
        existingUser.setPhone(userCreateDto.getPhone());
        existingUser.setPassword(userCreateDto.getPassword());
        existingUser.setDateOfBirth(userCreateDto.getDateOfBirth());
    }
}
