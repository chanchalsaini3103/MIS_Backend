package com.example.internshipproject.model;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;

@Table(name = "users")
@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String role;

    @Enumerated(EnumType.STRING)
    private Status status = Status.active;

  

    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());
    
	public enum Status {
		active, inactive
	}

}
