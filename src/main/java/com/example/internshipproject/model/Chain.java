package com.example.internshipproject.model;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Setter
@Getter
public class Chain {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long chainId;
	
	private String companyName;
	
	@Column(length = 15,unique = true)
	private String gstnNo;
	
	private Boolean isActive=true;
	
	private LocalDateTime createdAt=LocalDateTime.now();
	private LocalDateTime updatedAt=LocalDateTime.now();
	
	@ManyToOne
	@JoinColumn(name="group_id")
	private Group group;
}
