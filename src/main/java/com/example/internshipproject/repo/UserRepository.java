package com.example.internshipproject.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.internshipproject.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByResetToken(String resetToken);

}
