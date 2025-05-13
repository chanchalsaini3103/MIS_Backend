package com.example.internshipproject.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.internshipproject.model.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    List<Brand> findByIsActiveTrue();

    List<Brand> findByChain_Group_GroupIdAndIsActiveTrue(Long groupId);
}
