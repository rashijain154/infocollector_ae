package com.avirantenterprises.infocollector.service.business;

import com.avirantenterprises.infocollector.model.business.TaxForm;
import java.util.List;

public interface TaxFormService {
    TaxForm saveTaxForm(TaxForm taxForm);
    List<TaxForm> getAllTaxForms();
    TaxForm getTaxFormById(Long id);
    void deleteTaxFormById(Long id);
}
