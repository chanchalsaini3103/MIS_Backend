package com.example.internshipproject.controller;

import com.example.internshipproject.model.User;
import com.example.internshipproject.repo.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = {
        "http://localhost:5173",
        "https://groupmanagement-frontend.onrender.com"
}, allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // ✅ Register
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already registered.");
        }

        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        user.setRole(user.getRole() != null ? user.getRole() : "USER");
        user.setStatus(User.Status.active);
        userRepository.save(user);

        return ResponseEntity.ok("Registration Successful!");
    }

    // ✅ Login (creates session)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser, HttpSession session) {
        Optional<User> userOptional = userRepository.findByEmail(loginUser.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(loginUser.getPasswordHash(), user.getPasswordHash())) {
                session.setAttribute("user", user); // ✅ Store user in session
                return ResponseEntity.ok().body(user.getRole());
            }
        }
        return ResponseEntity.status(401).body("Invalid email or password");
    }

    // ✅ Logout
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logged out successfully.");
    }

    // ✅ Get Current Logged-in User (optional)
    @GetMapping("/me")
    public ResponseEntity<?> currentUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(401).body("Not logged in");
        }
    }
}
