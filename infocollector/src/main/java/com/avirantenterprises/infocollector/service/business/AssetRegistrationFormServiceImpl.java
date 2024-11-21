package com.avirantenterprises.infocollector.service.business;

import com.avirantenterprises.infocollector.model.business.AssetRegistrationForm;
import com.avirantenterprises.infocollector.repository.business.AssetRegistrationFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetRegistrationFormServiceImpl implements AssetRegistrationFormService {

    @Autowired
    private AssetRegistrationFormRepository assetRegistrationFormRepository;

    @Override
    public AssetRegistrationForm saveAssetRegistrationForm(AssetRegistrationForm assetRegistrationForm) {
        return assetRegistrationFormRepository.save(assetRegistrationForm);
    }

    @Override
    public List<AssetRegistrationForm> getAllAssetRegistrationForms() {
        return assetRegistrationFormRepository.findAll();
    }

    @Override
    public AssetRegistrationForm getAssetRegistrationFormById(Long id) {
        return assetRegistrationFormRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteAssetRegistrationFormById(Long id) {
        assetRegistrationFormRepository.deleteById(id);
    }
}
