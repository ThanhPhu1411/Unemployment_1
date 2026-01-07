package com.example.demo.dto.request;

import java.time.LocalDateTime;

public class JobUpdateRequestDTO {
    private String jobTitle;
    private String locate;
    private String jobDescription;
    private String requirements;
    private String benefits;
    private String salary;
    private LocalDateTime applicationDeadline;

    public String getJobTitle() {
        return jobTitle;
    }

    public JobUpdateRequestDTO(String jobTitle, String locate, String jobDescription, String requirements, String benefits, String salary, LocalDateTime applicationDeadline, Long categoryId, Long jobTypeId) {
        this.jobTitle = jobTitle;
        this.locate = locate;
        this.jobDescription = jobDescription;
        this.requirements = requirements;
        this.benefits = benefits;
        this.salary = salary;
        this.applicationDeadline = applicationDeadline;
        this.categoryId = categoryId;
        this.jobTypeId = jobTypeId;
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

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getJobTypeId() {
        return jobTypeId;
    }

    public void setJobTypeId(Long jobTypeId) {
        this.jobTypeId = jobTypeId;
    }

    private Long categoryId;
    private Long jobTypeId;

}
