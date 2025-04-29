package com.example.internshipproject.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.internshipproject.model.User;
import com.example.internshipproject.repo.UserRepository;
import com.example.internshipproject.service.JwtService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return "Error: Email already registered.";
        }
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        user.setRole("USER");
        userRepository.save(user);
        return "Registration Successful!";
    }

    @PostMapping("/login")
    public String login(@RequestBody User loginUser) {
        Optional<User> userOptional = userRepository.findByEmail(loginUser.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(loginUser.getPasswordHash(), user.getPasswordHash())) {
                return jwtService.generateToken(user.getEmail());
            }
        }
        return "Error: Invalid email or password.";
    }
}
