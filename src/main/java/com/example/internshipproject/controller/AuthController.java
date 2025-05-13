package com.example.internshipproject.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.internshipproject.model.User;
import com.example.internshipproject.repo.UserRepository;

import ch.qos.logback.core.status.Status;
import jakarta.servlet.http.HttpSession;
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession session;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists.");
        }
        user.setPasswordHash(encoder.encode(user.getPasswordHash()));
        user.setStatus(User.Status.active);

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully.");
    }

    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser) {
        Optional<User> userOpt = userRepository.findByEmail(loginUser.getEmail());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (encoder.matches(loginUser.getPasswordHash(), user.getPasswordHash())) {
                session.setAttribute("user", user); // force session to exist
                return ResponseEntity.ok(user);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
    }

    @GetMapping("/session-check")
    public ResponseEntity<?> sessionCheck() {
        return ResponseEntity.ok(session.getAttribute("user") != null ? "Session Active" : "No session");
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        session.invalidate();
        return ResponseEntity.ok("Logged out successfully.");
    }

    @GetMapping("/current-user")
    public ResponseEntity<?> currentUser() {
        User user = (User) session.getAttribute("user");
        return ResponseEntity.ok(user);
    }
}
