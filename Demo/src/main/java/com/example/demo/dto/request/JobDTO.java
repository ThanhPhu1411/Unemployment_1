package com.example.demo.dto.request;

import java.time.LocalDateTime;
import java.util.UUID;

public class JobDTO {
    private UUID id;
    private String jobTitle;
    private String locate;
    private String salary;
    private String jobType;
    private String category;
    private LocalDateTime postedDate = LocalDateTime.now();
    private LocalDateTime applicationDeadline;

    public JobDTO(UUID id, String jobTitle, String locate, String salary, String jobType, String category, LocalDateTime postedDate, LocalDateTime applicationDeadline, String companyName, String companyLogo) {
        this.id = id;
        this.jobTitle = jobTitle;
        this.locate = locate;
        this.salary = salary;
        this.jobType = jobType;
        this.category = category;
        this.postedDate = postedDate;
        this.applicationDeadline = applicationDeadline;
        this.companyName = companyName;
        this.companyLogo = companyLogo;
    }

    public JobDTO() {

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

    public LocalDateTime getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(LocalDateTime postedDate) {
        this.postedDate = postedDate;
    }

    public LocalDateTime getApplicationDeadline() {
        return applicationDeadline;
    }

    public void setApplicationDeadline(LocalDateTime applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    private String companyName;
    private String companyLogo;
}
