package com.avirantenterprises.infocollector.controller.business;

import com.avirantenterprises.infocollector.model.business.EmployeePerformanceForm;
import com.avirantenterprises.infocollector.service.business.EmployeePerformanceFormService;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class EmployeePerformanceFormController {

    private static final Logger logger = Logger.getLogger(EmployeePerformanceFormController.class.getName());

    @Autowired
    private EmployeePerformanceFormService employeePerformanceFormService;

    @Autowired
    private PdfService pdfService;

    private final String uploadDir = "D:/infocollector (2)/infocollector/src/main/java/com/avirantenterprises/infocollector/controller/business";

    @GetMapping("/employee-form")
    public String showEmployeePerformanceForm(Model model) {
        model.addAttribute("employeePerformanceForm", new EmployeePerformanceForm());
        return "business/employeePerformanceForm";
    }

    @PostMapping("/employee-form/submit")
    public String submitEmployeePerformanceForm(@RequestParam(value = "id", required = false) Long id,
                                                @RequestParam("employeeName") String employeeName,
                                                @RequestParam("performanceMetrics") String performanceMetrics,
                                                @RequestParam("supportingDocument") MultipartFile supportingDocument,
                                                @RequestParam("employeeRole") String employeeRole,
                                                @RequestParam("reviewPeriod") String reviewPeriod,
                                                @RequestParam("performanceRating") int performanceRating,
                                                Model model) {
        EmployeePerformanceForm employeePerformanceForm;
        if (id != null) {
            employeePerformanceForm = employeePerformanceFormService.getEmployeePerformanceFormById(id);
            if (employeePerformanceForm == null) {
                model.addAttribute("error", "Form not found for ID: " + id);
                return "business/employeePerformanceForm";
            }
        } else {
            employeePerformanceForm = new EmployeePerformanceForm();
        }

        employeePerformanceForm.setEmployeeName(employeeName);
        employeePerformanceForm.setPerformanceMetrics(performanceMetrics);
        employeePerformanceForm.setEmployeeRole(employeeRole);
        employeePerformanceForm.setReviewPeriod(reviewPeriod);
        employeePerformanceForm.setPerformanceRating(performanceRating);

        try {
            if (!supportingDocument.isEmpty()) {
                String fileName = supportingDocument.getOriginalFilename();
                Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, supportingDocument.getBytes());
                employeePerformanceForm.setSupportingDocumentPath(filePath.toString());
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error uploading supporting document", e);
            model.addAttribute("error", "Error uploading supporting document. Please try again.");
            return "business/employeePerformanceForm";
        }

        try {
            employeePerformanceFormService.saveEmployeePerformanceForm(employeePerformanceForm);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error saving employee performance form", e);
            model.addAttribute("error", "Error saving employee performance form. Please try again.");
            return "business/employeePerformanceForm";
        }

        return "redirect:/employee-form/forms";
    }


    @GetMapping("/employee-form/forms")
    public String showAllEmployeePerformanceForms(Model model) {
        model.addAttribute("employeePerformanceForms", employeePerformanceFormService.getAllEmployeePerformanceForms());
        return "business/employeePerformanceForms";
    }

    @GetMapping("/employee-form/download-form/{id}")
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


    @GetMapping("/employee-form/edit/{id}")
    public String editEmployeePerformanceForm(@PathVariable Long id, Model model) {
        EmployeePerformanceForm employeePerformanceForm = employeePerformanceFormService.getEmployeePerformanceFormById(id);
        model.addAttribute("employeePerformanceForm", employeePerformanceForm);
        return "business/employeePerformanceForm";
    }

    @GetMapping("/employee-form/delete/{id}")
    public String deleteEmployeePerformanceForm(@PathVariable Long id) {
        employeePerformanceFormService.deleteEmployeePerformanceFormById(id);
        return "redirect:/employee-form/forms";
    }
}
