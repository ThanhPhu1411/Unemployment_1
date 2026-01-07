package com.example.demo.dto.request;

import java.util.UUID;

public class AdminEmployerDTO {
    private UUID id;
    private String companyName;
    private String companyEmail;
    private String companyLogo;

    public UUID getId() {
        return id;
    }

    public AdminEmployerDTO(UUID id, String companyName, String companyEmail, String companyLogo, String companySize, String companyDescription, String companyAddress, String status, Double latitude, Double longitude, String licenseDocument) {
        this.id = id;
        this.companyName = companyName;
        this.companyEmail = companyEmail;
        this.companyLogo = companyLogo;
        this.companySize = companySize;
        this.companyDescription = companyDescription;
        this.companyAddress = companyAddress;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
        this.licenseDocument = licenseDocument;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getCompanySize() {
        return companySize;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getLicenseDocument() {
        return licenseDocument;
    }

    public void setLicenseDocument(String licenseDocument) {
        this.licenseDocument = licenseDocument;
    }

    private String companySize;
    private String companyDescription;
    private String companyAddress;
    private String status;
    private Double latitude;  // Nullable
    private Double longitude;
    private String licenseDocument;
}
