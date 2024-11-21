package com.avirantenterprises.infocollector.controller.educational;

import com.avirantenterprises.infocollector.model.educational.CertificationUploadForm;
import com.avirantenterprises.infocollector.service.educational.CertificationUploadFormService;
import com.avirantenterprises.infocollector.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CertificationUploadFormController {

    private static final Logger logger = Logger.getLogger(CertificationUploadFormController.class.getName());

    @Autowired
    private CertificationUploadFormService formService;

    @Autowired
    private EmailService emailService;

    private final String uploadDir = "C:/Users/chkrb/Downloads/infocollector/uploads/educational";

    @GetMapping("/certification-upload-form")
    public String showCertificationUploadForm(Model model) {
        model.addAttribute("form", new CertificationUploadForm());
        return "educational/certificationUploadForm";
    }

    @PostMapping("/certification-upload-form/submit")
    public String submitCertificationUploadForm(@RequestParam(value = "id", required = false) Long id,
                                                @RequestParam("courseName") String courseName,
                                                @RequestParam("completionDate") String completionDate,
                                                @RequestParam("certification") MultipartFile certification,
                                                @RequestParam("certificationName") String certificationName,
                                                @RequestParam("issuingOrganization") String issuingOrganization,
                                                @RequestParam("validityStartDate") String validityStartDate,
                                                @RequestParam("validityEndDate") String validityEndDate,
                                                @RequestParam("userEmail") String userEmail, // New field for user email
                                                Model model) {
        CertificationUploadForm form;
        if (id != null) {
            form = formService.getCertificationUploadFormById(id);
            if (form == null) {
                model.addAttribute("error", "Form not found for ID: " + id);
                return "educational/certificationUploadForm";
            }
        } else {
            form = new CertificationUploadForm();
        }

        form.setCourseName(courseName);
        form.setCompletionDate(completionDate);
        form.setCertificationName(certificationName);
        form.setIssuingOrganization(issuingOrganization);
        form.setValidityStartDate(validityStartDate);
        form.setValidityEndDate(validityEndDate);
        form.setUserEmail(userEmail); // Set the user email

        try {
            if (!certification.isEmpty()) {
                String fileName = certification.getOriginalFilename();
                Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, certification.getBytes());
                form.setCertificationPath(filePath.toString());
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error uploading certification", e);
            model.addAttribute("error", "Error uploading certification. Please try again.");
            return "educational/certificationUploadForm";
        }

        try {
            formService.saveCertificationUploadForm(form);
            emailService.sendEmail(form.getUserEmail(), "Certification Upload Confirmation",
                    "Your certification for the course " + form.getCourseName() + " has been successfully submitted.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error saving form", e);
            model.addAttribute("error", "Error saving form. Please try again.");
            return "educational/certificationUploadForm";
        }

        return "redirect:/certification-upload-form/forms";
    }

    @GetMapping("/certification-upload-form/forms")
    public String showAllCertificationUploadForms(Model model) {
        model.addAttribute("forms", formService.getAllCertificationUploadForms());
        return "educational/certificationUploadForms";
    }

    @GetMapping("/certification-upload-form/edit/{id}")
    public String editCertificationUploadForm(@PathVariable Long id, Model model) {
        CertificationUploadForm form = formService.getCertificationUploadFormById(id);
        model.addAttribute("form", form);
        return "educational/certificationUploadForm";
    }

    @GetMapping("/certification-upload-form/delete/{id}")
    public String deleteCertificationUploadForm(@PathVariable Long id) {
        formService.deleteCertificationUploadFormById(id);
        return "redirect:/certification-upload-form/forms";
    }
}
