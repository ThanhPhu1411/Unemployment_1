package com.example.demo.dto.request;

import java.time.LocalDateTime;

public class JobCreateRequestDTO {
    private String jobTitle;
    private String locate;
    private String jobDescription;
    private String requirements;
    private String salary;
    private String benefits;
    private LocalDateTime applicationDeadline;

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

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public LocalDateTime getApplicationDeadline() {
        return applicationDeadline;
    }

    public void setApplicationDeadline(LocalDateTime applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
    }

    public long getJobTypeId() {
        return jobTypeId;
    }

    public void setJobTypeId(long jobTypeId) {
        this.jobTypeId = jobTypeId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public JobCreateRequestDTO(String jobTitle, String locate, String jobDescription, String requirements, String salary, String benefits, LocalDateTime applicationDeadline, long jobTypeId, long categoryId) {
        this.jobTitle = jobTitle;
        this.locate = locate;
        this.jobDescription = jobDescription;
        this.requirements = requirements;
        this.salary = salary;
        this.benefits = benefits;
        this.applicationDeadline = applicationDeadline;
        this.jobTypeId = jobTypeId;
        this.categoryId = categoryId;
    }

    private long jobTypeId;
    private long categoryId;
}
