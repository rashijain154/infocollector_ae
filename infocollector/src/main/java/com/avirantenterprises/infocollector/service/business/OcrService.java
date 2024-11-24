package com.avirantenterprises.infocollector.service.business;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class OcrService {

    public String extractTextFromImage(File imageFile) {
        Tesseract tesseract = new Tesseract();

        // Set the path to the Tesseract data files
        String tessDataPath = "C:/Program Files/Tesseract-OCR/tessdata";

        // Check if the Tesseract data directory exists
        File tessDataDir = new File(tessDataPath);
        if (!tessDataDir.exists() || !tessDataDir.isDirectory()) {
            return "Tesseract data folder not found at: " + tessDataDir.getAbsolutePath();
        }

        try {
            // Set the datapath if the directory is correct
            tesseract.setDatapath(tessDataPath);

            // Perform OCR and extract text from the image
            return tesseract.doOCR(imageFile);
        } catch (TesseractException e) {
            e.printStackTrace();
            return "Error during OCR processing: " + e.getMessage();
        }
    }
}
