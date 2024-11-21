package com.avirantenterprises.infocollector.service.business;

import com.avirantenterprises.infocollector.model.business.AssetRegistrationForm;
import java.util.List;

public interface AssetRegistrationFormService {
    AssetRegistrationForm saveAssetRegistrationForm(AssetRegistrationForm assetRegistrationForm);
    List<AssetRegistrationForm> getAllAssetRegistrationForms();
    AssetRegistrationForm getAssetRegistrationFormById(Long id);
    void deleteAssetRegistrationFormById(Long id);
}
