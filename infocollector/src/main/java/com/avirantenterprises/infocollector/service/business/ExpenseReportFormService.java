package com.avirantenterprises.infocollector.service.business;

import com.avirantenterprises.infocollector.model.business.ExpenseReportForm;
import com.itextpdf.text.DocumentException;

import java.util.List;

public interface ExpenseReportFormService {
    ExpenseReportForm saveExpenseReportForm(ExpenseReportForm expenseReportForm);
    List<ExpenseReportForm> getAllExpenseReportForms();
    ExpenseReportForm getExpenseReportFormById(Long id);
    void deleteExpenseReportFormById(Long id);
    byte[] generateExpenseFormPdf(Long id) throws DocumentException;
}

