package com.avirantenterprises.infocollector.model.educational;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CertificationUploadForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String courseName;
    private String completionDate;
    private String certificationPath;
    private String certificationName;
    private String issuingOrganization;
    private String validityStartDate;
    private String validityEndDate;
    private String userEmail;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    public String getCertificationPath() {
        return certificationPath;
    }

    public void setCertificationPath(String certificationPath) {
        this.certificationPath = certificationPath;
    }

    public String getCertificationName() {
        return certificationName;
    }

    public void setCertificationName(String certificationName) {
        this.certificationName = certificationName;
    }

    public String getIssuingOrganization() {
        return issuingOrganization;
    }

    public void setIssuingOrganization(String issuingOrganization) {
        this.issuingOrganization = issuingOrganization;
    }

    public String getValidityStartDate() {
        return validityStartDate;
    }

    public void setValidityStartDate(String validityStartDate) {
        this.validityStartDate = validityStartDate;
    }

    public String getValidityEndDate() {
        return validityEndDate;
    }

    public void setValidityEndDate(String validityEndDate) {
        this.validityEndDate = validityEndDate;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
