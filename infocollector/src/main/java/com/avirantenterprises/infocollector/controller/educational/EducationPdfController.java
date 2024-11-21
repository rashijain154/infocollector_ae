package com.avirantenterprises.infocollector.controller.educational;

import com.avirantenterprises.infocollector.service.educational.EducationPdfService;
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
public class EducationPdfController {

    @Autowired
    private EducationPdfService pdfService;

    @GetMapping("/certification-upload-form/download-document/{id}")
    public ResponseEntity<byte[]> downloadCertificationDocument(@PathVariable Long id) {
        try {
            String filePath = pdfService.getCertificationDocumentPath(id);
            HttpHeaders headers = new HttpHeaders();
            byte[] content;

            if (filePath.endsWith(".pdf") || filePath.matches(".*\\.(jpg|jpeg|png)$")) {
                content = pdfService.getDocumentAsByteArray(filePath);
                headers.setContentType(filePath.endsWith(".pdf") ? MediaType.APPLICATION_PDF : MediaType.IMAGE_JPEG);
                headers.setContentDispositionFormData("attachment", "certification_document_" + id + filePath.substring(filePath.lastIndexOf(".")));
            } else {
                content = pdfService.generateCertificationFormPdf(id);
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData("attachment", "certification_form_" + id + ".pdf");
            }

            return ResponseEntity.ok().headers(headers).body(content);
        } catch (IOException | DocumentException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/certification-upload-form/download-form-only/{id}")
    public ResponseEntity<byte[]> downloadCertificationFormPdf(@PathVariable Long id) {
        try {
            byte[] pdfContent = pdfService.generateCertificationFormPdf(id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "certification_form_" + id + ".pdf");
            return ResponseEntity.ok().headers(headers).body(pdfContent);
        } catch (IOException | DocumentException e) {
            return ResponseEntity.status(500).build();
        }
    }
}
