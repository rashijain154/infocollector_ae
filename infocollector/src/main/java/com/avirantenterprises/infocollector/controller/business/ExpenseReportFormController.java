package com.avirantenterprises.infocollector.controller.business;

import com.avirantenterprises.infocollector.model.business.ExpenseReportForm;
import com.avirantenterprises.infocollector.service.business.ExpenseReportFormService;
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
import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class ExpenseReportFormController {

    private static final Logger logger = Logger.getLogger(ExpenseReportFormController.class.getName());

    @Autowired
    private ExpenseReportFormService expenseReportFormService;

    private final String uploadDir = "uploads/receipts/";

    @GetMapping("/expense-report")
    public String showExpenseReportForm(Model model) {
        model.addAttribute("expenseReportForm", new ExpenseReportForm());
        return "business/expenseReport";
    }

    @PostMapping("/expense-report/submit")
    public String submitExpenseReportForm(@RequestParam(value = "id", required = false) Long id,
                                          @RequestParam("expenseAmount") BigDecimal expenseAmount,
                                          @RequestParam("category") String category,
                                          @RequestParam("receipt") MultipartFile receipt,
                                          @RequestParam("description") String description,
                                          @RequestParam("dateIncurred") LocalDate dateIncurred,
                                          @RequestParam("paymentMethod") String paymentMethod,
                                          Model model) {
        ExpenseReportForm expenseReportForm;
        if (id != null) {
            expenseReportForm = expenseReportFormService.getExpenseReportFormById(id);
            if (expenseReportForm == null) {
                model.addAttribute("error", "Form not found for ID: " + id);
                return "business/expenseReport";
            }
        } else {
            expenseReportForm = new ExpenseReportForm();
        }

        expenseReportForm.setExpenseAmount(expenseAmount);
        expenseReportForm.setCategory(category);
        expenseReportForm.setDescription(description);
        expenseReportForm.setDateIncurred(dateIncurred);
        expenseReportForm.setPaymentMethod(paymentMethod);

        try {
            if (!receipt.isEmpty()) {
                String fileName = receipt.getOriginalFilename();
                Path filePath = Paths.get(uploadDir + fileName);
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, receipt.getBytes());
                expenseReportForm.setReceiptFilePath(filePath.toString());
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error uploading receipt", e);
            model.addAttribute("error", "Error uploading receipt. Please try again.");
            return "business/expenseReport";
        }

        try {
            expenseReportFormService.saveExpenseReportForm(expenseReportForm);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error saving expense report form", e);
            model.addAttribute("error", "Error saving expense report form. Please try again.");
            return "business/expenseReport";
        }

        return "redirect:/expense-report/forms";
    }

    @GetMapping("/expense-report/forms")
    public String showAllExpenseReportForms(Model model) {
        model.addAttribute("expenseReportForms", expenseReportFormService.getAllExpenseReportForms());
        return "business/expenseReportForms";
    }

    @GetMapping("/expense-report/download-form/{id}")
    public ResponseEntity<byte[]> downloadExpenseFormPdf(@PathVariable Long id) {
        try {
            byte[] pdfContent = expenseReportFormService.generateExpenseFormPdf(id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "expense_form_" + id + ".pdf");
            return ResponseEntity.ok().headers(headers).body(pdfContent);
        } catch (DocumentException e) {
            return ResponseEntity.status(500).build();
        }
    }


    @GetMapping("/expense-report/edit/{id}")
    public String editExpenseReportForm(@PathVariable Long id, Model model) {
        ExpenseReportForm expenseReportForm = expenseReportFormService.getExpenseReportFormById(id);
        model.addAttribute("expenseReportForm", expenseReportForm);
        return "business/expenseReport";
    }

    @GetMapping("/expense-report/delete/{id}")
    public String deleteExpenseReportForm(@PathVariable Long id) {
        expenseReportFormService.deleteExpenseReportFormById(id);
        return "redirect:/expense-report/forms";
    }
}
