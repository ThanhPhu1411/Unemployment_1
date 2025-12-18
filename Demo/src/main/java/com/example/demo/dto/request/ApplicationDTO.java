package com.example.demo.dto.request;

import java.util.UUID;

public class ApplicationDTO {
    private UUID applicationId;
    private String status;
    private String saveStatus;
    private UUID candidateId;

    public ApplicationDTO(UUID applicationId, String status, String saveStatus, UUID candidateId, String fullName, String email, String phone) {
        this.applicationId = applicationId;
        this.status = status;
        this.saveStatus = saveStatus;
        this.candidateId = candidateId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
    }

    public UUID getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(UUID applicationId) {
        this.applicationId = applicationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(String saveStatus) {
        this.saveStatus = saveStatus;
    }

    public UUID getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(UUID candidateId) {
        this.candidateId = candidateId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String fullName;
    private String email;
    private String phone;
}
