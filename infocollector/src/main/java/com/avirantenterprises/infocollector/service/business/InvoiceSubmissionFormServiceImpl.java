package com.avirantenterprises.infocollector.service.business;

import com.avirantenterprises.infocollector.model.business.InvoiceSubmissionForm;
import com.avirantenterprises.infocollector.repository.business.InvoiceSubmissionFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class InvoiceSubmissionFormServiceImpl implements InvoiceSubmissionFormService {

    @Autowired
    private InvoiceSubmissionFormRepository invoiceSubmissionFormRepository;

    private final String uploadDir = "C:/Users/chkrb/Downloads/infocollector/uploads/business";

    @Override
    public InvoiceSubmissionForm saveInvoiceSubmissionForm(InvoiceSubmissionForm invoiceSubmissionForm) {
        return invoiceSubmissionFormRepository.save(invoiceSubmissionForm);
    }

    @Override
    public List<InvoiceSubmissionForm> getAllInvoiceSubmissionForms() {
        return invoiceSubmissionFormRepository.findAll();
    }

    @Override
    public InvoiceSubmissionForm getInvoiceSubmissionFormById(Long id) {
        return invoiceSubmissionFormRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteInvoiceSubmissionFormById(Long id) {
        invoiceSubmissionFormRepository.deleteById(id);
    }

    @Override
    public String getInvoiceDocumentPath(Long id) {
        InvoiceSubmissionForm invoiceSubmissionForm = invoiceSubmissionFormRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice Submission Form not found"));
        return invoiceSubmissionForm.getInvoiceFilePath();
    }

    @Override
    public byte[] getDocumentAsByteArray(String filePath) throws IOException {
        return Files.readAllBytes(Paths.get(filePath));
    }
}
