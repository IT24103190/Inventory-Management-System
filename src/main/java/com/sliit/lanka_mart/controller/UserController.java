package com.sliit.lanka_mart.controller;

import com.sliit.lanka_mart.model.User;
import com.sliit.lanka_mart.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    // UC-05: Manage User Access
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping("/role/{roleName}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable String roleName) {
        return ResponseEntity.ok(userService.getUsersByRole(roleName));
    }

    // UC-05: Create new user with role assignment
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUser(user));
    }

    // UC-05: Update user roles and permissions
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(
            @PathVariable String userId,
            @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(userId, user));
    }

    // UC-05: Password reset functionality
    @PutMapping("/{userId}/password")
    public ResponseEntity<Void> updatePassword(
            @PathVariable String userId,
            @RequestBody PasswordUpdateRequest request) {
        userService.updatePassword(userId, request.newPassword());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    record PasswordUpdateRequest(String newPassword) {}
}