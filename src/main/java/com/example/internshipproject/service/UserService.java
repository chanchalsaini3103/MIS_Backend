package com.example.internshipproject.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.internshipproject.model.User;
import com.example.internshipproject.repo.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        return userRepo.save(user);
    }

    public Optional<User> authenticate(String email, String password) {
        return userRepo.findByEmail(email)
                       .filter(user -> passwordEncoder.matches(password, user.getPasswordHash()));
    }
    
    
    public Optional<User> getUserByEmail(String email){
        return userRepo.findByEmail(email);
    }

    public Optional<User> getByResetToken(String token){
        return userRepo.findByResetToken(token);
    }

    public void save(User user){
        userRepo.save(user);
    }

}