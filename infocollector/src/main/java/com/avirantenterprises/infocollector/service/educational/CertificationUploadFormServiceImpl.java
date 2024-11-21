package com.avirantenterprises.infocollector.service.educational;

import com.avirantenterprises.infocollector.model.educational.CertificationUploadForm;
import com.avirantenterprises.infocollector.repository.educational.CertificationUploadFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificationUploadFormServiceImpl implements CertificationUploadFormService {

    @Autowired
    private CertificationUploadFormRepository formRepository;

    @Override
    public CertificationUploadForm saveCertificationUploadForm(CertificationUploadForm form) {
        return formRepository.save(form);
    }

    @Override
    public List<CertificationUploadForm> getAllCertificationUploadForms() {
        return formRepository.findAll();
    }

    @Override
    public CertificationUploadForm getCertificationUploadFormById(Long id) {
        return formRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteCertificationUploadFormById(Long id) {
        formRepository.deleteById(id);
    }
}
