	package com.example.internshipproject.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.internshipproject.model.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByGroupName(String groupName);
    boolean existsByGroupName(String groupName);
}
