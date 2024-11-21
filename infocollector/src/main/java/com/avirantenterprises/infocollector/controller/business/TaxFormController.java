package com.avirantenterprises.infocollector.controller.business;

import com.avirantenterprises.infocollector.model.business.TaxForm;
import com.avirantenterprises.infocollector.service.business.TaxFormService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TaxFormController {

    private static final Logger logger = Logger.getLogger(TaxFormController.class.getName());

    @Autowired
    private TaxFormService taxFormService;

    private final String uploadDir = "D:/infocollector (2)/infocollector/src/main/java/com/avirantenterprises/infocollector/controller/business";

    @GetMapping("/tax-form")
    public String showTaxForm(Model model) {
        model.addAttribute("taxForm", new TaxForm());
        return "business/taxForm";
    }

    @PostMapping("/tax-form/submit")
    public String submitTaxForm(@RequestParam(value = "id", required = false) Long id,
                                @RequestParam("taxYear") int taxYear,
                                @RequestParam("taxpayerName") String taxpayerName,
                                @RequestParam("taxDocument") MultipartFile taxDocument,
                                @RequestParam("taxAmount") BigDecimal taxAmount,
                                @RequestParam("submissionDate") String submissionDate,
                                @RequestParam("approvalStatus") String approvalStatus,
                                Model model) {
        TaxForm taxForm;
        if (id != null) {
            taxForm = taxFormService.getTaxFormById(id);
            if (taxForm == null) {
                model.addAttribute("error", "Form not found for ID: " + id);
                return "business/taxForm";
            }
        } else {
            taxForm = new TaxForm();
        }

        taxForm.setTaxYear(taxYear);
        taxForm.setTaxpayerName(taxpayerName);
        taxForm.setTaxAmount(taxAmount);
        taxForm.setSubmissionDate(submissionDate);
        taxForm.setApprovalStatus(approvalStatus);

        try {
            if (!taxDocument.isEmpty()) {
                String fileName = taxDocument.getOriginalFilename();
                Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, taxDocument.getBytes());
                taxForm.setTaxDocumentPath(filePath.toString());
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error uploading tax document", e);
            model.addAttribute("error", "Error uploading tax document. Please try again.");
            return "business/taxForm";
        }

        try {
            taxFormService.saveTaxForm(taxForm);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error saving tax form", e);
            model.addAttribute("error", "Error saving tax form. Please try again.");
            return "business/taxForm";
        }

        return "redirect:/tax-form/forms";
    }

    @GetMapping("/tax-form/forms")
    public String showAllTaxForms(Model model) {
        model.addAttribute("taxForms", taxFormService.getAllTaxForms());
        return "business/taxForms";
    }

    @GetMapping("/tax-form/edit/{id}")
    public String editTaxForm(@PathVariable Long id, Model model) {
        TaxForm taxForm = taxFormService.getTaxFormById(id);
        model.addAttribute("taxForm", taxForm);
        return "business/taxForm";
    }

    @GetMapping("/tax-form/delete/{id}")
    public String deleteTaxForm(@PathVariable Long id) {
        taxFormService.deleteTaxFormById(id);
        return "redirect:/tax-form/forms";
    }
}
