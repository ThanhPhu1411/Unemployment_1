package com.example.demo.dto.request;

import org.hibernate.annotations.Nationalized;

import java.util.UUID;

public class EmployerDTO {
    private UUID id;
    private String companyName;
    private String email;
    private String status;

    public EmployerDTO(UUID id, String companyName, String email, String status, String companySize, String companyAddress) {
        this.id = id;
        this.companyName = companyName;
        this.email = email;
        this.status = status;
        this.companySize = companySize;
        this.companyAddress = companyAddress;
    }

    private String companySize;

    public UUID getId() {
        return id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompanySize() {
        return companySize;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    private String companyAddress; //
}
