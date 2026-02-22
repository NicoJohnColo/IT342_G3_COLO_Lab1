package com.example.colo.mini_app.controller;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.colo.mini_app.model.User;
import com.example.colo.mini_app.service.UserService;

@CrossOrigin(origins = {"http://localhost:3000", "http://10.0.2.2:8080"})
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public Map<String, Object> me(Authentication authentication) {
        String username = authentication.getName();
        User u = userService.findByUsername(username);
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
