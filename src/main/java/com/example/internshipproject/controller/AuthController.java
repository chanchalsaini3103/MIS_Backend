package com.example.internshipproject.controller;

import java.util.Optional;
import java.util.UUID; // ✅ For token generation

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.internshipproject.model.User;
import com.example.internshipproject.repo.UserRepository;
import com.example.internshipproject.service.JwtService;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

	

	@Autowired
	private JavaMailSender mailSender;

	
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
        user.setRole(user.getRole() != null ? user.getRole() : "USER");
        user.setStatus(User.Status.inactive); // ✅ Fully qualify the enum

        user.setResetToken(UUID.randomUUID().toString()); // Generate token
        userRepository.save(user);

        // Send Verification Email
        sendVerificationEmail(user.getEmail(), user.getResetToken());

        return "Registration Successful! Please verify your email.";
    }

    // Helper method
    private void sendVerificationEmail(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Email Verification - Internship Project");
        message.setText("Click the link to verify your account: " +
                "https://internshipproject-backend.onrender.com/api/auth/verify?token=" + token);
        mailSender.send(message);
    }

    @GetMapping("/verify")
    public String verifyEmail(@RequestParam String token) {
        Optional<User> userOptional = userRepository.findByResetToken(token);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setStatus(User.Status.active);

            user.setResetToken(null); // clear token
            userRepository.save(user);
            return "Email Verified Successfully! You can now login.";
        } else {
            return "Error: Invalid verification link.";
        }
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
    
    
    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setResetToken(UUID.randomUUID().toString());
            userRepository.save(user);

            sendResetLinkEmail(user.getEmail(), user.getResetToken());
            return "Reset link sent to your email.";
        } else {
            return "Email not registered.";
        }
    }
    private void sendResetLinkEmail(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Reset Your Password - Internship Project");
        message.setText("Click here to reset your password: " +
            "https://internshipproject-frontend.onrender.com/reset-password/" + token);
        mailSender.send(message);
    }


	@PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        Optional<User> userOptional = userRepository.findByResetToken(token);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPasswordHash(passwordEncoder.encode(newPassword));
            user.setResetToken(null); // clear token
            userRepository.save(user);
            return "Password reset successful!";
        } else {
            return "Invalid or expired token.";
        }
    }

}
