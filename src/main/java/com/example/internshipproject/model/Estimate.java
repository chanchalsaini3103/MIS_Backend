package com.example.internshipproject.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "estimates")
@Getter
@Setter
public class Estimate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long estimateId;

    @ManyToOne
    @JoinColumn(name = "chain_id", nullable = false)
    private Chain chain;

    @Column(nullable = false, length = 50)
    private String groupName;

    @Column(nullable = false, length = 50)
    private String brandName;

    @Column(nullable = false, length = 50)
    private String zoneName;

    @Column(nullable = false, length = 100)
    private String service;

    @Column(nullable = false)
    private int qty;

    @Column(nullable = false)
    private float costPerUnit;

    @Column(nullable = false)
    private float totalCost;

    @Column(nullable = false)
    private LocalDate deliveryDate;

    @Column(length = 100)
    private String deliveryDetails;

    private boolean isActive = true;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
