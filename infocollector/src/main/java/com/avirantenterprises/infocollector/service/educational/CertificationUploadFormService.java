package com.avirantenterprises.infocollector.service.educational;

import com.avirantenterprises.infocollector.model.educational.CertificationUploadForm;
import java.util.List;

public interface CertificationUploadFormService {
    CertificationUploadForm saveCertificationUploadForm(CertificationUploadForm form);
    List<CertificationUploadForm> getAllCertificationUploadForms();
    CertificationUploadForm getCertificationUploadFormById(Long id);
    void deleteCertificationUploadFormById(Long id);
}
