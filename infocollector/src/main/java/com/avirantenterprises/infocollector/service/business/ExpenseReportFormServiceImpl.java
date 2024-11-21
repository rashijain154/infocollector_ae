package com.avirantenterprises.infocollector.service.business;

import com.avirantenterprises.infocollector.model.business.ExpenseReportForm;
import com.avirantenterprises.infocollector.repository.business.ExpenseReportFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseReportFormServiceImpl implements ExpenseReportFormService {

    @Autowired
    private ExpenseReportFormRepository expenseReportFormRepository;

    @Override
    public ExpenseReportForm saveExpenseReportForm(ExpenseReportForm expenseReportForm) {
        return expenseReportFormRepository.save(expenseReportForm);
    }

    @Override
    public List<ExpenseReportForm> getAllExpenseReportForms() {
        return expenseReportFormRepository.findAll();
    }

    @Override
    public ExpenseReportForm getExpenseReportFormById(Long id) {
        Optional<ExpenseReportForm> optionalExpenseReportForm = expenseReportFormRepository.findById(id);
        return optionalExpenseReportForm.orElse(null);
    }

    @Override
    public void deleteExpenseReportFormById(Long id) {
        expenseReportFormRepository.deleteById(id);
    }

    @Override
    public byte[] generateExpenseFormPdf(Long id) throws DocumentException {
        // Placeholder for PDF generation logic
        return new byte[0];
    }
}
