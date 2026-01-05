package com.example.demo.dto.request;

import java.time.LocalDateTime;
import java.util.UUID;

public class JobDetailResponse {
    private UUID id;
    private String jobTitle;
    private String salary;
    private String locate;
    private LocalDateTime applicationDeadline;
    private LocalDateTime postedDate;


    private String jobDescription;
    private String requirements;
    private String benefits;

    public JobDetailResponse(UUID id, String jobTitle, String salary, String locate, LocalDateTime applicationDeadline, LocalDateTime postedDate, String jobDescription, String requirements, String benefits, String jobType, String category, UUID employerId, String companyName, String companyEmail, String companySize, String licenseDocument, String companyDescription, Double latitude, Double longitude, String companyAddress, String companyLogo) {
        this.id = id;
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.locate = locate;
        this.applicationDeadline = applicationDeadline;
        this.postedDate = postedDate;
        this.jobDescription = jobDescription;
        this.requirements = requirements;
        this.benefits = benefits;
        this.jobType = jobType;
        this.category = category;
        this.employerId = employerId;
        this.companyName = companyName;
        this.companyEmail = companyEmail;
        this.companySize = companySize;
        this.licenseDocument = licenseDocument;
        this.companyDescription = companyDescription;
        this.latitude = latitude;
        this.longitude = longitude;
        this.companyAddress = companyAddress;
        this.companyLogo = companyLogo;
    }

    private String jobType;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getLocate() {
        return locate;
    }

    public void setLocate(String locate) {
        this.locate = locate;
    }

    public LocalDateTime getApplicationDeadline() {
        return applicationDeadline;
    }

    public void setApplicationDeadline(LocalDateTime applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
    }

    public LocalDateTime getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(LocalDateTime postedDate) {
        this.postedDate = postedDate;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public UUID getEmployerId() {
        return employerId;
    }

    public void setEmployerId(UUID employerId) {
        this.employerId = employerId;
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

    public String getCompanySize() {
        return companySize;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }

    public String getLicenseDocument() {
        return licenseDocument;
    }

    public void setLicenseDocument(String licenseDocument) {
        this.licenseDocument = licenseDocument;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
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

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    private String category;

    private UUID employerId;
    private String companyName;
    private String companyEmail;
    private String companySize;
    private String licenseDocument;
    private String companyDescription;
    private Double latitude;
    private Double longitude;
    private String companyAddress;
    private String companyLogo;
}
