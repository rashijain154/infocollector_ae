package com.avirantenterprises.infocollector.service.business;

import com.avirantenterprises.infocollector.model.business.AssetRegistrationForm;
import com.avirantenterprises.infocollector.model.business.EmployeePerformanceForm;
import com.avirantenterprises.infocollector.model.business.ExpenseReportForm;
import com.avirantenterprises.infocollector.model.business.InvoiceSubmissionForm;
import com.avirantenterprises.infocollector.model.business.TaxForm;
import com.avirantenterprises.infocollector.repository.business.AssetRegistrationFormRepository;
import com.avirantenterprises.infocollector.repository.business.EmployeePerformanceFormRepository;
import com.avirantenterprises.infocollector.repository.business.ExpenseReportFormRepository;
import com.avirantenterprises.infocollector.repository.business.InvoiceSubmissionFormRepository;
import com.avirantenterprises.infocollector.repository.business.TaxFormRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

@Service
public class PdfService {

    @Autowired
    private AssetRegistrationFormRepository assetRegistrationFormRepository;

    @Autowired
    private EmployeePerformanceFormRepository employeePerformanceFormRepository;

    @Autowired
    private ExpenseReportFormRepository expenseReportFormRepository;

    @Autowired
    private InvoiceSubmissionFormRepository invoiceSubmissionFormRepository;

    @Autowired
    private TaxFormRepository taxFormRepository;

    private final Path rootLocation = Paths.get("C:/Users/chkrb/Downloads/infocollector/uploads/business");

    public byte[] generateAssetFormPdf(Long id) throws IOException, DocumentException {
        AssetRegistrationForm assetRegistrationForm = assetRegistrationFormRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asset Registration Form not found"));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        document.add(new Paragraph("Asset Registration Form"));
        document.add(new Paragraph("\n"));

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        table.addCell("Asset Name");
        table.addCell(assetRegistrationForm.getAssetName());

        table.addCell("Asset Description");
        table.addCell(assetRegistrationForm.getAssetDescription());

        // Add new fields to the PDF
        table.addCell("Asset Location");
        table.addCell(assetRegistrationForm.getAssetLocation());

        table.addCell("Asset Value");
        table.addCell(String.valueOf(assetRegistrationForm.getAssetValue()));

        table.addCell("Asset Acquisition Date");
        table.addCell(assetRegistrationForm.getAssetAcquisitionDate());

        document.add(table);

        document.close();

        return outputStream.toByteArray();
    }

    public byte[] getDocumentAsByteArray(String filePath) throws IOException {
        return Files.readAllBytes(Paths.get(filePath));
    }

    public String getAssetDocumentPath(Long id) {
        AssetRegistrationForm assetRegistrationForm = assetRegistrationFormRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asset Registration Form not found"));
        return assetRegistrationForm.getAssetDocumentPath();
    }

    // EmployeeForm methods
    public byte[] generateEmployeeFormPdf(Long id) throws IOException, DocumentException {
        EmployeePerformanceForm employeePerformanceForm = employeePerformanceFormRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee Performance Form not found"));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        document.add(new Paragraph("Employee Performance Form"));
        document.add(new Paragraph("\n"));

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        table.addCell("Employee Name");
        table.addCell(employeePerformanceForm.getEmployeeName());

        table.addCell("Performance Metrics");
        table.addCell(employeePerformanceForm.getPerformanceMetrics());

        // Add new fields to the PDF
        table.addCell("Employee Role");
        table.addCell(employeePerformanceForm.getEmployeeRole());

        table.addCell("Review Period");
        table.addCell(employeePerformanceForm.getReviewPeriod());

        table.addCell("Performance Rating");
        table.addCell(String.valueOf(employeePerformanceForm.getPerformanceRating()));

        document.add(table);

        document.close();

        return outputStream.toByteArray();
    }

    public String getEmployeePerformanceDocumentPath(Long id) {
        EmployeePerformanceForm employeePerformanceForm = employeePerformanceFormRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee Performance Form not found"));
        return employeePerformanceForm.getSupportingDocumentPath();
    }

    // ExpenseReportForm methods
    public byte[] generateExpenseFormPdf(Long id) throws DocumentException {
        ExpenseReportForm expenseReportForm = expenseReportFormRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense Report Form not found"));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        document.add(new Paragraph("Expense Report Form"));
        document.add(new Paragraph("\n"));

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        table.addCell("Expense Amount");
        table.addCell(String.valueOf(expenseReportForm.getExpenseAmount()));

        table.addCell("Category");
        table.addCell(expenseReportForm.getCategory());

        table.addCell("Description");
        table.addCell(expenseReportForm.getDescription());

        table.addCell("Date Incurred");
        table.addCell(String.valueOf(expenseReportForm.getDateIncurred()));

        table.addCell("Payment Method");
        table.addCell(expenseReportForm.getPaymentMethod());

        document.add(table);

        document.close();

        return outputStream.toByteArray();
    }

    public String getExpenseDocumentPath(Long id) {
        ExpenseReportForm expenseReportForm = expenseReportFormRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense Report Form not found"));
        return expenseReportForm.getReceiptFilePath();
    }

    // InvoiceSubmissionForm methods
    public byte[] generateInvoiceFormPdf(Long id) throws IOException, DocumentException {
        InvoiceSubmissionForm invoiceSubmissionForm = invoiceSubmissionFormRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice Submission Form not found"));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        document.add(new Paragraph("Invoice Submission Form"));
        document.add(new Paragraph("\n"));

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        table.addCell("Invoice Amount");
        table.addCell(String.valueOf(invoiceSubmissionForm.getInvoiceAmount()));

        table.addCell("Invoice Number");
        table.addCell(invoiceSubmissionForm.getInvoiceNumber());

        table.addCell("Vendor Name");
        table.addCell(invoiceSubmissionForm.getVendorName());

        table.addCell("Invoice Date");
        table.addCell(invoiceSubmissionForm.getInvoiceDate());

        table.addCell("Due Date");
        table.addCell(invoiceSubmissionForm.getDueDate());

        table.addCell("Payment Status");
        table.addCell(invoiceSubmissionForm.getPaymentStatus());

        document.add(table);

        document.close();

        return outputStream.toByteArray();
    }

    public String getInvoiceDocumentPath(Long id) {
        InvoiceSubmissionForm invoiceSubmissionForm = invoiceSubmissionFormRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice Submission Form not found"));
        return invoiceSubmissionForm.getInvoiceFilePath();
    }

    // TaxForm methods
    public byte[] generateTaxFormPdf(Long id) throws IOException, DocumentException {
        TaxForm taxForm = taxFormRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tax Form not found"));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        document.add(new Paragraph("Tax Form"));
        document.add(new Paragraph("\n"));

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        table.addCell("Tax Year");
        table.addCell(String.valueOf(taxForm.getTaxYear()));

        table.addCell("Taxpayer Name");
        table.addCell(taxForm.getTaxpayerName());

        table.addCell("Tax Amount");
        table.addCell(String.valueOf(taxForm.getTaxAmount()));

        table.addCell("Submission Date");
        table.addCell(taxForm.getSubmissionDate());

        table.addCell("Approval Status");
        table.addCell(taxForm.getApprovalStatus());

        document.add(table);

        document.close();

        return outputStream.toByteArray();
    }

    public String getTaxDocumentPath(Long id) {
        TaxForm taxForm = taxFormRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tax Form not found"));
        return taxForm.getTaxDocumentPath();
    }
}
