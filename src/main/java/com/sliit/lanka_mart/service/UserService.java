package com.sliit.lanka_mart.service;

import com.sliit.lanka_mart.model.User;
import com.sliit.lanka_mart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists: " + user.getEmail());
        }

        if (user.getUserId() == null || user.getUserId().isEmpty()) {
            user.setUserId(generateUserId());
        }

        // Hash password
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        return userRepository.save(user);
    }

    public User updateUser(String userId, User userDetails) {
        User user = getUserById(userId);
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setCompanyName(userDetails.getCompanyName());
        user.setRole(userDetails.getRole());
        return userRepository.save(user);
    }

    public void updatePassword(String userId, String newPassword) {
        User user = getUserById(userId);
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    public List<User> getUsersByRole(String roleName) {
        return userRepository.findByRole_RoleName(roleName);
    }

    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private String generateUserId() {
        long count = userRepository.count();
        return String.format("USR%05d", count + 1);
    }
}