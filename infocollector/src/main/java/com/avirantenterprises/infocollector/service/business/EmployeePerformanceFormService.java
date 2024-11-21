package com.avirantenterprises.infocollector.service.business;

import com.avirantenterprises.infocollector.model.business.EmployeePerformanceForm;
import java.util.List;

public interface EmployeePerformanceFormService {
    EmployeePerformanceForm saveEmployeePerformanceForm(EmployeePerformanceForm employeePerformanceForm);
    List<EmployeePerformanceForm> getAllEmployeePerformanceForms();
    EmployeePerformanceForm getEmployeePerformanceFormById(Long id);
    void deleteEmployeePerformanceFormById(Long id);
}
