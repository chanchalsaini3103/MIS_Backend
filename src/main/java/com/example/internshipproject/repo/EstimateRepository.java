package com.example.internshipproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.internshipproject.model.Estimate;

@Repository
public interface EstimateRepository extends JpaRepository<Estimate, Long> {
}
