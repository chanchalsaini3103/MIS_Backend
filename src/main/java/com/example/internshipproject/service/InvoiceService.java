package com.example.internshipproject.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.internshipproject.PdfGenerator;
import com.example.internshipproject.model.Chain;
import com.example.internshipproject.model.Estimate;
import com.example.internshipproject.model.Invoice;
import com.example.internshipproject.repo.ChainRepository;
import com.example.internshipproject.repo.EstimateRepository;
import com.example.internshipproject.repo.InvoiceRepository;

@Service
public class InvoiceService {

    @Autowired private InvoiceRepository invoiceRepo;
    @Autowired private EstimateRepository estimateRepo;
    @Autowired private ChainRepository chainRepo;
    @Autowired private PdfGenerator pdfGenerator;

    public Invoice generateInvoice(Long estimateId, String emailId) {
        Estimate estimate = estimateRepo.findById(estimateId).orElseThrow();
        Chain chain = estimate.getChain();

        Invoice invoice = new Invoice();
        invoice.setInvoiceNo(generateUniqueInvoiceNo());
        invoice.setEstimate(estimate);
        invoice.setChain(chain);
        invoice.setServiceDetails(estimate.getService()); // `service` from Estimate
        invoice.setQty(estimate.getQty());
        invoice.setCostPerQty(estimate.getCostPerUnit());
        invoice.setAmountPayable(estimate.getTotalCost());
        invoice.setBalance(0);
        invoice.setDateOfPayment(LocalDateTime.now());
        invoice.setDateOfService(estimate.getDeliveryDate()); // `deliveryDate` -> service date
        invoice.setDeliveryDetails(estimate.getDeliveryDetails());
        invoice.setEmailId(emailId);

        return invoiceRepo.save(invoice);
    }


    private int generateUniqueInvoiceNo() {
        Random rand = new Random();
        int num;
        do {
            num = 1000 + rand.nextInt(9000);
        } while (invoiceRepo.existsByInvoiceNo(num));
        return num;
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepo.findAll();
    }

    public Invoice updateEmail(Long id, String email) {
        Invoice invoice = invoiceRepo.findById(id).orElseThrow();
        invoice.setEmailId(email);
        return invoiceRepo.save(invoice);
    }

    public void deleteInvoice(Long id) {
        invoiceRepo.deleteById(id);
    }
}
