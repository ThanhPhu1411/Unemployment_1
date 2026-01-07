package com.example.demo.dto.request;

import java.time.LocalDateTime;
import java.util.UUID;

public class JobHomeDTO {
    private UUID id;
    private String jobTitle;
    private String jobDescription;
    private String locate;
    private String salary;

    public JobHomeDTO(UUID id, String jobTitle, String jobDescription, String locate, String salary, LocalDateTime applicationDeadline, String companyLogo) {
        this.id = id;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.locate = locate;
        this.salary = salary;
        this.applicationDeadline = applicationDeadline;
        this.companyLogo = companyLogo;
    }

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

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getLocate() {
        return locate;
    }

    public void setLocate(String locate) {
        this.locate = locate;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public LocalDateTime getApplicationDeadline() {
        return applicationDeadline;
    }

    public void setApplicationDeadline(LocalDateTime applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    private LocalDateTime applicationDeadline;
    private String companyLogo;
}
