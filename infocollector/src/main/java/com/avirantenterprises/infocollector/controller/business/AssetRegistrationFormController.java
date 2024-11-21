package com.avirantenterprises.infocollector.controller.business;

import com.avirantenterprises.infocollector.model.business.AssetRegistrationForm;
import com.avirantenterprises.infocollector.service.business.AssetRegistrationFormService;
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
public class AssetRegistrationFormController {

    private static final Logger logger = Logger.getLogger(AssetRegistrationFormController.class.getName());

    @Autowired
    private AssetRegistrationFormService assetRegistrationFormService;

    @Autowired
    private PdfService pdfService;

    private final String uploadDir = "D:/infocollector (2)/infocollector/src/main/java/com/avirantenterprises/infocollector/controller/business";

    @GetMapping("/asset-registration")
    public String showAssetRegistrationForm(Model model) {
        model.addAttribute("assetRegistrationForm", new AssetRegistrationForm());
        return "business/assetRegistrationForm";
    }

    @PostMapping("/asset-registration/submit")
    public String submitAssetRegistrationForm(@RequestParam(value = "id", required = false) Long id,
                                              @RequestParam("assetName") String assetName,
                                              @RequestParam("assetDescription") String assetDescription,
                                              @RequestParam("assetDocument") MultipartFile assetDocument,
                                              @RequestParam("assetLocation") String assetLocation,
                                              @RequestParam("assetValue") Double assetValue,
                                              @RequestParam("assetAcquisitionDate") String assetAcquisitionDate,
                                              Model model) {
        AssetRegistrationForm assetRegistrationForm;
        if (id != null) {
            assetRegistrationForm = assetRegistrationFormService.getAssetRegistrationFormById(id);
            if (assetRegistrationForm == null) {
                model.addAttribute("error", "Form not found for ID: " + id);
                return "business/assetRegistrationForm";
            }
        } else {
            assetRegistrationForm = new AssetRegistrationForm();
        }

        assetRegistrationForm.setAssetName(assetName);
        assetRegistrationForm.setAssetDescription(assetDescription);
        assetRegistrationForm.setAssetLocation(assetLocation);
        assetRegistrationForm.setAssetValue(assetValue);
        assetRegistrationForm.setAssetAcquisitionDate(assetAcquisitionDate);

        try {
            if (!assetDocument.isEmpty()) {
                String fileName = assetDocument.getOriginalFilename();
                Path filePath = Paths.get(uploadDir + fileName);
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, assetDocument.getBytes());
                assetRegistrationForm.setAssetDocumentPath(filePath.toString());
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error uploading asset document", e);
            model.addAttribute("error", "Error uploading asset document. Please try again.");
            return "business/assetRegistrationForm";
        }

        try {
            assetRegistrationFormService.saveAssetRegistrationForm(assetRegistrationForm);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error saving asset registration form", e);
            model.addAttribute("error", "Error saving asset registration form. Please try again.");
            return "business/assetRegistrationForm";
        }

        return "redirect:/asset-registration/forms";
    }

    @GetMapping("/asset-registration/forms")
    public String showAllAssetRegistrationForms(Model model) {
        model.addAttribute("assetRegistrationForms", assetRegistrationFormService.getAllAssetRegistrationForms());
        return "business/assetRegistrationForms";
    }

    @GetMapping("/asset-registration/download-form/{id}")
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

    @GetMapping("/asset-registration/edit/{id}")
    public String editAssetRegistrationForm(@PathVariable Long id, Model model) {
        AssetRegistrationForm assetRegistrationForm = assetRegistrationFormService.getAssetRegistrationFormById(id);
        model.addAttribute("assetRegistrationForm", assetRegistrationForm);
        return "business/assetRegistrationForm";
    }

    @GetMapping("/asset-registration/delete/{id}")
    public String deleteAssetRegistrationForm(@PathVariable Long id) {
        assetRegistrationFormService.deleteAssetRegistrationFormById(id);
        return "redirect:/asset-registration/forms";
    }
}
