package com.avirantenterprises.infocollector.repository.business;

import com.avirantenterprises.infocollector.model.business.TaxForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxFormRepository extends JpaRepository<TaxForm, Long> {
}
