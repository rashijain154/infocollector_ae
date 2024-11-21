package com.avirantenterprises.infocollector.service.business;

import com.avirantenterprises.infocollector.model.business.TaxForm;
import com.avirantenterprises.infocollector.repository.business.TaxFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaxFormServiceImpl implements TaxFormService {

    @Autowired
    private TaxFormRepository taxFormRepository;

    @Override
    public TaxForm saveTaxForm(TaxForm taxForm) {
        return taxFormRepository.save(taxForm);
    }

    @Override
    public List<TaxForm> getAllTaxForms() {
        return taxFormRepository.findAll();
    }

    @Override
    public TaxForm getTaxFormById(Long id) {
        return taxFormRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteTaxFormById(Long id) {
        taxFormRepository.deleteById(id);
    }
}
