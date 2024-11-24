package com.avirantenterprises.infocollector.controller.business;

import com.avirantenterprises.infocollector.service.business.OcrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class OcrController {

    @Autowired
    private OcrService ocrService;

    // Show the OCR upload image page with a unique mapping
    @GetMapping("/business-dashboard/ocr-upload-image")
    public String uploadImagePage(Model model) {
        model.addAttribute("title", "Upload Image for OCR");
        return "business/upload-image";  // Returning to the image upload page
    }

    // Process the uploaded image for OCR
    @PostMapping("/processimage")
    public String processImage(@RequestParam("imageFile") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("error", "Please select an image file.");
            return "business/upload-image";  // Returning to the upload page with error message
        }

        try {
            // Create a temporary file with the same extension as the original file
            String fileName = file.getOriginalFilename();
            if (fileName == null) {
                model.addAttribute("error", "Invalid file name.");
                return "business/upload-image";
            }

            File tempFile = File.createTempFile("uploaded-", fileName);
            tempFile.deleteOnExit(); // Automatically delete when the JVM exits

            // Transfer the uploaded file to the temporary file
            file.transferTo(tempFile);

            // Perform OCR to extract text from the image
            String extractedText = ocrService.extractTextFromImage(tempFile);

            // Add extracted text to the model
            model.addAttribute("extractedText", extractedText);

            // Delete the temporary file after OCR processing (if needed)
            tempFile.delete();

            // Return the result page with OCR text
            return "business/ocr-result";
        } catch (IOException e) {
            model.addAttribute("error", "Failed to process the image: " + e.getMessage());
            return "business/upload-image";  // Returning to upload page with error message
        }
    }

    // Optionally, you could add a method to view the OCR result separately
    @GetMapping("/business-dashboard/ocr-result/{id}")
    public String viewOcrResult(@PathVariable Long id, Model model) {
        // Logic to retrieve the OCR result by ID if needed
        model.addAttribute("ocrResult", "Result for OCR " + id);  // Example
        return "business/ocr-result";  // View the result page
    }
}

