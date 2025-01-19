package com.spring.app.controllers;

import java.util.List;
import java.util.stream.Collectors;
import com.spring.app.enums.user_enums.Gender;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.spring.app.services.UserService;
import com.spring.app.dto.UserDTO;
import com.spring.app.models.User;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO UserDTO) {
        User user = convertToEntity(UserDTO);
        User savedUser = userService.createUser(user);
        return convertToDto(savedUser);
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private User convertToEntity(UserDTO UserDTO) {
        User user = new User();
        user.setUsername(UserDTO.getUsername());
        user.setEmail(UserDTO.getEmail());
        user.setGender((UserDTO.getGender()));
        return user;
    }

    private UserDTO convertToDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setGender((user.getGender()));
        return dto;
    }
}