package com.example.internshipproject;

import org.springframework.stereotype.Component;

import com.example.internshipproject.model.Invoice;

@Component
public class PdfGenerator {
    public byte[] generateInvoicePdf(Invoice invoice) {
        // Replace this with actual PDF generation (e.g., iText or OpenPDF)
        return ("Invoice PDF for " + invoice.getInvoiceNo()).getBytes();
    }
}
