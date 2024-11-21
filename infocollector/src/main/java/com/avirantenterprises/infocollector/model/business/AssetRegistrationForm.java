package com.avirantenterprises.infocollector.model.business;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AssetRegistrationForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String assetName;
    private String assetDescription;
    private String assetDocumentPath;

    // New fields
    private String assetLocation;
    private Double assetValue;
    private String assetAcquisitionDate;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetDescription() {
        return assetDescription;
    }

    public void setAssetDescription(String assetDescription) {
        this.assetDescription = assetDescription;
    }

    public String getAssetDocumentPath() {
        return assetDocumentPath;
    }

    public void setAssetDocumentPath(String assetDocumentPath) {
        this.assetDocumentPath = assetDocumentPath;
    }

    public String getAssetLocation() {
        return assetLocation;
    }

    public void setAssetLocation(String assetLocation) {
        this.assetLocation = assetLocation;
    }

    public Double getAssetValue() {
        return assetValue;
    }

    public void setAssetValue(Double assetValue) {
        this.assetValue = assetValue;
    }

    public String getAssetAcquisitionDate() {
        return assetAcquisitionDate;
    }

    public void setAssetAcquisitionDate(String assetAcquisitionDate) {
        this.assetAcquisitionDate = assetAcquisitionDate;
    }
}
