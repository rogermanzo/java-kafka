package com.api.saludapp.user.service;

import com.api.saludapp.user.dto.ApiResponse;
import com.api.saludapp.user.dto.UserCreateDto;
import com.api.saludapp.user.dto.UserDto;
import com.api.saludapp.user.mapper.UserMapper;
import com.api.saludapp.user.model.User;
import com.api.saludapp.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserMapper userMapper;

    // Obtiene todos los usuarios
    public ApiResponse<List<UserDto>> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            List<UserDto> userDtos = users.stream()
                    .map(userMapper::toDto)
                    .collect(Collectors.toList());
            return ApiResponse.success(userDtos, "Users retrieved successfully");
        } catch (Exception e) {
            return ApiResponse.error("Error retrieving users: " + e.getMessage());
        }
    }

    // Obtiene un usuario por ID
    public ApiResponse<UserDto> getUserById(Long id) {
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                UserDto userDto = userMapper.toDto(user.get());
                return ApiResponse.success(userDto, "User found");
            } else {
                return ApiResponse.error("User not found with id: " + id);
            }
        } catch (Exception e) {
            return ApiResponse.error("Error retrieving user: " + e.getMessage());
        }
    }

    // Obtiene un usuario por email
    public ApiResponse<UserDto> getUserByEmail(String email) {
        try {
            Optional<User> user = userRepository.findByEmail(email);
            if (user.isPresent()) {
                UserDto userDto = userMapper.toDto(user.get());
                return ApiResponse.success(userDto, "User found");
            } else {
                return ApiResponse.error("User not found with email: " + email);
            }
        } catch (Exception e) {
            return ApiResponse.error("Error retrieving user: " + e.getMessage());
        }
    }

    // Crea un nuevo usuario
    public ApiResponse<UserDto> createUser(UserCreateDto userCreateDto) {
        try {
            if (userRepository.findByEmail(userCreateDto.getEmail()).isPresent()) {
                return ApiResponse.error("User with email " + userCreateDto.getEmail() + " already exists");
            }
            
            User user = userMapper.toEntity(userCreateDto);
            User savedUser = userRepository.save(user);
            UserDto userDto = userMapper.toDto(savedUser);
            return ApiResponse.success(userDto, "User created successfully");
        } catch (Exception e) {
            return ApiResponse.error("Error creating user: " + e.getMessage());
        }
    }

    // Actualiza un usuario existente
    public ApiResponse<UserDto> updateUser(Long id, UserCreateDto userCreateDto) {
        try {
            Optional<User> existingUserOpt = userRepository.findById(id);
            if (existingUserOpt.isEmpty()) {
                return ApiResponse.error("User not found with id: " + id);
            }
            
            User existingUser = existingUserOpt.get();
            userMapper.updateEntity(existingUser, userCreateDto);
            User updatedUser = userRepository.save(existingUser);
            UserDto userDto = userMapper.toDto(updatedUser);
            return ApiResponse.success(userDto, "User updated successfully");
        } catch (Exception e) {
            return ApiResponse.error("Error updating user: " + e.getMessage());
        }
    }

    // Elimina un usuario
    public ApiResponse<Void> deleteUser(Long id) {
        try {
            if (userRepository.existsById(id)) {
                userRepository.deleteById(id);
                return ApiResponse.success(null, "User deleted successfully");
            } else {
                return ApiResponse.error("User not found with id: " + id);
            }
        } catch (Exception e) {
            return ApiResponse.error("Error deleting user: " + e.getMessage());
        }
    }
}
