package com.example.internshipproject.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chainId;

    @Column(nullable = false)
    private String companyName;

    @Column(unique = true, nullable = false, length = 15)
    private String gstnNo;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    private boolean isActive = true;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
	
}
