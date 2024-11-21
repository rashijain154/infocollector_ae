package com.avirantenterprises.infocollector.service.educational;

import com.avirantenterprises.infocollector.model.educational.CertificationUploadForm;
import com.avirantenterprises.infocollector.repository.educational.CertificationUploadFormRepository;
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
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class EducationPdfService {

    @Autowired
    private CertificationUploadFormRepository formRepository;

    private final Path rootLocation = Paths.get("C:/Users/chkrb/Downloads/infocollector/uploads/educational");

    public byte[] generateCertificationFormPdf(Long id) throws IOException, DocumentException {
        CertificationUploadForm form = formRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Certification Upload Form not found"));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        document.add(new Paragraph("Certification Upload Form"));
        document.add(new Paragraph("\n"));

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        table.addCell("Course Name");
        table.addCell(form.getCourseName());

        table.addCell("Completion Date");
        table.addCell(form.getCompletionDate());

        table.addCell("Certification Name");
        table.addCell(form.getCertificationName());

        table.addCell("Issuing Organization");
        table.addCell(form.getIssuingOrganization());

        table.addCell("Validity Start Date");
        table.addCell(form.getValidityStartDate());

        table.addCell("Validity End Date");
        table.addCell(form.getValidityEndDate());

        document.add(table);

        document.close();

        return outputStream.toByteArray();
    }

    public byte[] getDocumentAsByteArray(String filePath) throws IOException {
        return Files.readAllBytes(Paths.get(filePath));
    }

    public String getCertificationDocumentPath(Long id) {
        CertificationUploadForm form = formRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Certification Upload Form not found"));
        return form.getCertificationPath();
    }
}
