package com.avirantenterprises.infocollector.service.business;

import com.avirantenterprises.infocollector.model.business.InvoiceSubmissionForm;

import java.io.IOException;
import java.util.List;

public interface InvoiceSubmissionFormService {
    InvoiceSubmissionForm saveInvoiceSubmissionForm(InvoiceSubmissionForm invoiceSubmissionForm);
    List<InvoiceSubmissionForm> getAllInvoiceSubmissionForms();
    InvoiceSubmissionForm getInvoiceSubmissionFormById(Long id);
    void deleteInvoiceSubmissionFormById(Long id);
    String getInvoiceDocumentPath(Long id);
    byte[] getDocumentAsByteArray(String filePath) throws IOException;
}
