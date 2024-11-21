package com.avirantenterprises.infocollector.controller.business;

import com.avirantenterprises.infocollector.model.business.InvoiceSubmissionForm;
import com.avirantenterprises.infocollector.service.business.InvoiceSubmissionFormService;
import com.avirantenterprises.infocollector.service.business.PdfService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class InvoiceSubmissionFormController {

    private static final Logger logger = Logger.getLogger(InvoiceSubmissionFormController.class.getName());

    @Autowired
    private InvoiceSubmissionFormService invoiceSubmissionFormService;

    @Autowired
    private PdfService pdfService;

    private final String uploadDir = "D:/infocollector (2)/infocollector/src/main/java/com/avirantenterprises/infocollector/controller/business";

    @GetMapping("/invoice-submission")
    public String showInvoiceSubmissionForm(Model model) {
        model.addAttribute("invoiceSubmissionForm", new InvoiceSubmissionForm());
        return "business/invoiceSubmission";
    }

    @PostMapping("/invoice-submission/submit")
    public String submitInvoiceSubmissionForm(@RequestParam(value = "id", required = false) Long id,
                                              @RequestParam("invoiceAmount") BigDecimal invoiceAmount,
                                              @RequestParam("invoiceNumber") String invoiceNumber,
                                              @RequestParam("invoiceFile") MultipartFile invoiceFile,
                                              @RequestParam("vendorName") String vendorName,
                                              @RequestParam("invoiceDate") String invoiceDate,
                                              @RequestParam("dueDate") String dueDate,
                                              @RequestParam("paymentStatus") String paymentStatus,
                                              Model model) {
        InvoiceSubmissionForm invoiceSubmissionForm;
        if (id != null) {
            invoiceSubmissionForm = invoiceSubmissionFormService.getInvoiceSubmissionFormById(id);
            if (invoiceSubmissionForm == null) {
                model.addAttribute("error", "Form not found for ID: " + id);
                return "business/invoiceSubmission";
            }
        } else {
            invoiceSubmissionForm = new InvoiceSubmissionForm();
        }

        invoiceSubmissionForm.setInvoiceAmount(invoiceAmount);
        invoiceSubmissionForm.setInvoiceNumber(invoiceNumber);
        invoiceSubmissionForm.setVendorName(vendorName);
        invoiceSubmissionForm.setInvoiceDate(invoiceDate);
        invoiceSubmissionForm.setDueDate(dueDate);
        invoiceSubmissionForm.setPaymentStatus(paymentStatus);

        try {
            if (!invoiceFile.isEmpty()) {
                String fileName = invoiceFile.getOriginalFilename();
                Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, invoiceFile.getBytes());
                invoiceSubmissionForm.setInvoiceFilePath(filePath.toString());
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error uploading invoice", e);
            model.addAttribute("error", "Error uploading invoice. Please try again.");
            return "business/invoiceSubmission";
        }

        try {
            invoiceSubmissionFormService.saveInvoiceSubmissionForm(invoiceSubmissionForm);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error saving invoice submission form", e);
            model.addAttribute("error", "Error saving invoice submission form. Please try again.");
            return "business/invoiceSubmission";
        }

        return "redirect:/invoice-submission/forms";
    }

    @GetMapping("/invoice-submission/forms")
    public String showAllInvoiceSubmissionForms(Model model) {
        model.addAttribute("invoiceSubmissionForms", invoiceSubmissionFormService.getAllInvoiceSubmissionForms());
        return "business/invoiceSubmissionForms";
    }

    @GetMapping("/invoice-submission/edit/{id}")
    public String editInvoiceSubmissionForm(@PathVariable Long id, Model model) {
        InvoiceSubmissionForm invoiceSubmissionForm = invoiceSubmissionFormService.getInvoiceSubmissionFormById(id);
        model.addAttribute("invoiceSubmissionForm", invoiceSubmissionForm);
        return "business/invoiceSubmission";
    }

    @GetMapping("/invoice-submission/delete/{id}")
    public String deleteInvoiceSubmissionForm(@PathVariable Long id) {
        invoiceSubmissionFormService.deleteInvoiceSubmissionFormById(id);
        return "redirect:/invoice-submission/forms";
    }
}
