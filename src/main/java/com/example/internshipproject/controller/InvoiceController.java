
package com.example.internshipproject.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.internshipproject.model.Invoice;
import com.example.internshipproject.service.InvoiceService;

@RestController
@RequestMapping("/api/invoices")
@CrossOrigin(origins = {
	    "http://localhost:5173",
	    "https://mia-frontend-mu0v.onrender.com"
	}, allowCredentials = "true")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/generate/{estimateId}")
    public Invoice generateInvoice(@PathVariable Long estimateId, @RequestBody Map<String, String> payload) {
        return invoiceService.generateInvoice(estimateId, payload.get("emailId"));
    }

    @GetMapping
    public List<Invoice> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

    @PutMapping("/{id}/update-email")
    public Invoice updateEmail(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        return invoiceService.updateEmail(id, payload.get("emailId"));
    }

    @DeleteMapping("/{id}")
    public void deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
    }
}
