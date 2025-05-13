package com.example.internshipproject.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.internshipproject.model.Chain;

public interface ChainRepository extends JpaRepository<Chain, Long> {
    Optional<Chain> findByGstnNo(String gstnNo);
    List<Chain> findByIsActiveTrue();
    List<Chain> findByGroup_GroupIdAndIsActiveTrue(Long groupId);
}
