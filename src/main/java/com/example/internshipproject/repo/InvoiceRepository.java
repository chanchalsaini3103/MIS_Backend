package com.example.internshipproject.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.internshipproject.model.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Optional<Invoice> findByInvoiceNo(Integer invoiceNo);
    boolean existsByInvoiceNo(Integer invoiceNo);
}
