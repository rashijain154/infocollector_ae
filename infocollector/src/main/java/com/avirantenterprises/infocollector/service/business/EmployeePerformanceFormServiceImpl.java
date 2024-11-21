package com.avirantenterprises.infocollector.service.business;

import com.avirantenterprises.infocollector.model.business.EmployeePerformanceForm;
import com.avirantenterprises.infocollector.repository.business.EmployeePerformanceFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeePerformanceFormServiceImpl implements EmployeePerformanceFormService {

    @Autowired
    private EmployeePerformanceFormRepository employeePerformanceFormRepository;

    @Override
    public EmployeePerformanceForm saveEmployeePerformanceForm(EmployeePerformanceForm employeePerformanceForm) {
        return employeePerformanceFormRepository.save(employeePerformanceForm);
    }

    @Override
    public List<EmployeePerformanceForm> getAllEmployeePerformanceForms() {
        return employeePerformanceFormRepository.findAll();
    }

    @Override
    public EmployeePerformanceForm getEmployeePerformanceFormById(Long id) {
        return employeePerformanceFormRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteEmployeePerformanceFormById(Long id) {
        employeePerformanceFormRepository.deleteById(id);
    }
}
