package com.sliit.lanka_mart.controller;

import com.sliit.lanka_mart.model.User;
import com.sliit.lanka_mart.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            User user = userService.getUserByEmail(request.email());

            if (userService.validatePassword(request.password(), user.getPasswordHash())) {
                return ResponseEntity.ok(new LoginResponse(
                        true,
                        "Login successful",
                        user.getUserId(),
                        user.getEmail(),
                        user.getFullName(),
                        user.getRole().getRoleName(),
                        null // Token would be generated here in production
                ));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new LoginResponse(false, "Invalid credentials", null, null, null, null, null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(false, "Invalid credentials", null, null, null, null, null));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        try {
            User newUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    record LoginRequest(String email, String password) {}

    record LoginResponse(
            Boolean success,
            String message,
            String userId,
            String email,
            String fullName,
            String role,
            String token
    ) {}
}