package com.example.colo.mini_app.controller;

import com.example.colo.mini_app.model.User;
import com.example.colo.mini_app.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/me")
    public Map<String, Object> me(Authentication authentication) {
        String username = authentication.getName();
        User u = userRepository.findByUsername(username).orElseThrow();
        return Map.of(
                "id", u.getId(),
                "username", u.getUsername(),
                "email", u.getEmail(),
                "firstName", u.getFirstName(),
                "lastName", u.getLastName(),
                "isActive", u.getIsActive(),
                "createdAt", u.getCreatedAt(),
                "updatedAt", u.getUpdatedAt(),
                "lastLogin", u.getLastLogin()
        );
    }
}
