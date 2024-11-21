package com.avirantenterprises.infocollector.controller.business;

import com.avirantenterprises.infocollector.service.business.PdfService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

@Controller
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @GetMapping("/asset-registration/download-document/{id}")
    public ResponseEntity<byte[]> downloadAssetDocument(@PathVariable Long id) {
        try {
            String filePath = pdfService.getAssetDocumentPath(id);
            HttpHeaders headers = new HttpHeaders();
            byte[] content;

            if (filePath.endsWith(".pdf") || filePath.matches(".*\\.(jpg|jpeg|png)$")) {
                content = pdfService.getDocumentAsByteArray(filePath);
                headers.setContentType(filePath.endsWith(".pdf") ? MediaType.APPLICATION_PDF : MediaType.IMAGE_JPEG);
                headers.setContentDispositionFormData("attachment", "asset_document_" + id + filePath.substring(filePath.lastIndexOf(".")));
            } else {
                content = pdfService.generateAssetFormPdf(id);
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData("attachment", "asset_form_" + id + ".pdf");
            }

            return ResponseEntity.ok().headers(headers).body(content);
        } catch (IOException | DocumentException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/employee-form/download-document/{id}")
    public ResponseEntity<byte[]> downloadEmployeePerformanceDocument(@PathVariable Long id) {
        try {
            String filePath = pdfService.getEmployeePerformanceDocumentPath(id);
            HttpHeaders headers = new HttpHeaders();
            byte[] content;

            if (filePath.endsWith(".pdf") || filePath.matches(".*\\.(jpg|jpeg|png)$")) {
                content = pdfService.getDocumentAsByteArray(filePath);
                headers.setContentType(filePath.endsWith(".pdf") ? MediaType.APPLICATION_PDF : MediaType.IMAGE_JPEG);
                headers.setContentDispositionFormData("attachment", "employee_performance_document_" + id + filePath.substring(filePath.lastIndexOf(".")));
            } else {
                content = pdfService.generateEmployeeFormPdf(id);
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData("attachment", "employee_form_" + id + ".pdf");
            }

            return ResponseEntity.ok().headers(headers).body(content);
        } catch (IOException | DocumentException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/asset-registration/download-form-only/{id}")
    public ResponseEntity<byte[]> downloadAssetFormPdf(@PathVariable Long id) {
        try {
            byte[] pdfContent = pdfService.generateAssetFormPdf(id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "asset_form_" + id + ".pdf");
            return ResponseEntity.ok().headers(headers).body(pdfContent);
        } catch (IOException | DocumentException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/employee-form/download-form-only/{id}")
    public ResponseEntity<byte[]> downloadEmployeeFormPdf(@PathVariable Long id) {
        try {
            byte[] pdfContent = pdfService.generateEmployeeFormPdf(id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "employee_form_" + id + ".pdf");
            return ResponseEntity.ok().headers(headers).body(pdfContent);
        } catch (IOException | DocumentException e) {
            return ResponseEntity.status(500).build();
        }
    }

    // New mappings for expense report form
    @GetMapping("/expense-report/download-document/{id}")
    public ResponseEntity<byte[]> downloadExpenseDocument(@PathVariable Long id) {
        try {
            String filePath = pdfService.getExpenseDocumentPath(id);
            HttpHeaders headers = new HttpHeaders();
            byte[] content;

            if (filePath.endsWith(".pdf") || filePath.matches(".*\\.(jpg|jpeg|png)$")) {
                content = pdfService.getDocumentAsByteArray(filePath);
                headers.setContentType(filePath.endsWith(".pdf") ? MediaType.APPLICATION_PDF : MediaType.IMAGE_JPEG);
                headers.setContentDispositionFormData("attachment", "expense_document_" + id + filePath.substring(filePath.lastIndexOf(".")));
            } else {
                content = pdfService.generateExpenseFormPdf(id);
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData("attachment", "expense_form_" + id + ".pdf");
            }

            return ResponseEntity.ok().headers(headers).body(content);
        } catch (IOException | DocumentException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/expense-report/download-form-only/{id}")
    public ResponseEntity<byte[]> downloadExpenseFormPdf(@PathVariable Long id) {
        try {
            byte[] pdfContent = pdfService.generateExpenseFormPdf(id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "expense_form_" + id + ".pdf");
            return ResponseEntity.ok().headers(headers).body(pdfContent);
        } catch (DocumentException e) {
            return ResponseEntity.status(500).build();
        }
    }

    // New mappings for invoice submission form
    @GetMapping("/invoice-submission/download-document/{id}")
    public ResponseEntity<byte[]> downloadInvoiceDocument(@PathVariable Long id) {
        try {
            String filePath = pdfService.getInvoiceDocumentPath(id);
            HttpHeaders headers = new HttpHeaders();
            byte[] content;

            if (filePath.endsWith(".pdf") || filePath.matches(".*\\.(jpg|jpeg|png)$")) {
                content = pdfService.getDocumentAsByteArray(filePath);
                headers.setContentType(filePath.endsWith(".pdf") ? MediaType.APPLICATION_PDF : MediaType.IMAGE_JPEG);
                headers.setContentDispositionFormData("attachment", "invoice_document_" + id + filePath.substring(filePath.lastIndexOf(".")));
            } else {
                content = pdfService.generateInvoiceFormPdf(id);
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData("attachment", "invoice_form_" + id + ".pdf");
            }

            return ResponseEntity.ok().headers(headers).body(content);
        } catch (IOException | DocumentException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/invoice-submission/download-form-only/{id}")
    public ResponseEntity<byte[]> downloadInvoiceFormPdf(@PathVariable Long id) {
        try {
            byte[] pdfContent = pdfService.generateInvoiceFormPdf(id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "invoice_form_" + id + ".pdf");
            return ResponseEntity.ok().headers(headers).body(pdfContent);
        } catch (IOException | DocumentException e) {
            return ResponseEntity.status(500).build();
        }
    }

    // New mappings for tax form
    @GetMapping("/tax-form/download-document/{id}")
    public ResponseEntity<byte[]> downloadTaxDocument(@PathVariable Long id) {
        try {
            String filePath = pdfService.getTaxDocumentPath(id);
            HttpHeaders headers = new HttpHeaders();
            byte[] content;

            if (filePath.endsWith(".pdf") || filePath.matches(".*\\.(jpg|jpeg|png)$")) {
                content = pdfService.getDocumentAsByteArray(filePath);
                headers.setContentType(filePath.endsWith(".pdf") ? MediaType.APPLICATION_PDF : MediaType.IMAGE_JPEG);
                headers.setContentDispositionFormData("attachment", "tax_document_" + id + filePath.substring(filePath.lastIndexOf(".")));
            } else {
                content = pdfService.generateTaxFormPdf(id);
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData("attachment", "tax_form_" + id + ".pdf");
            }

            return ResponseEntity.ok().headers(headers).body(content);
        } catch (IOException | DocumentException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/tax-form/download-form-only/{id}")
    public ResponseEntity<byte[]> downloadTaxFormPdf(@PathVariable Long id) {
        try {
            byte[] pdfContent = pdfService.generateTaxFormPdf(id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "tax_form_" + id + ".pdf");
            return ResponseEntity.ok().headers(headers).body(pdfContent);
        } catch (IOException | DocumentException e) {
            return ResponseEntity.status(500).build();
        }
    }
}
