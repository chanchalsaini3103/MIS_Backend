package com.example.internshipproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoices")
@Getter
@Setter
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Integer invoiceNo; // 4-digit unique

    @ManyToOne
    @JoinColumn(name = "estimated_id")
    private Estimate estimate;

    @ManyToOne
    @JoinColumn(name = "chain_id")
    private Chain chain;

    private String serviceDetails;
    private int qty;
    private float costPerQty;
    private float amountPayable;
    private float balance;
    private LocalDateTime dateOfPayment;
    private LocalDate dateOfService;

    @Column(length = 100)
    private String deliveryDetails;

    @Column(length = 50)
    private String emailId;
}
