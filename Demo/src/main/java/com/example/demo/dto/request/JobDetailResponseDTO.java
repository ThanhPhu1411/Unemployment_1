package com.example.demo.dto.request;

import java.time.LocalDateTime;
import java.util.UUID;

public class JobDetailResponseDTO {
    private UUID jobId;
    private String jobTitle;
    private String locate;
    private String jobDescription;
    private String requirements;
    private String benefits;

    public JobDetailResponseDTO(UUID jobId, String jobTitle, String locate, String jobDescription, String requirements, String benefits, String salary, String status, LocalDateTime postedDate, LocalDateTime applicationDeadline, String category, String jobType) {
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.locate = locate;
        this.jobDescription = jobDescription;
        this.requirements = requirements;
        this.benefits = benefits;
        this.salary = salary;
        this.status = status;
        this.postedDate = postedDate;
        this.applicationDeadline = applicationDeadline;
        this.category = category;
        this.jobType = jobType;
    }

    public UUID getJobId() {
        return jobId;
    }

    public void setJobId(UUID jobId) {
        this.jobId = jobId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    private String salary;
    private String status;
    private LocalDateTime postedDate;
    private LocalDateTime applicationDeadline;
    private String category;
    private String jobType;
}
