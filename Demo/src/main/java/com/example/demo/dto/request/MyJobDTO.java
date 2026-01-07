package com.example.demo.dto.request;

import java.util.UUID;

public class MyJobDTO {
    private UUID id;
    private String jobTitle;
    private String locate;
    private String salary;
    private String status;
    private String categoryName;

    public UUID getId() {
        return id;
    }

    public MyJobDTO(UUID id, String jobTitle, String locate, String salary, String status, String categoryName, String jobTypeName, long applicantCount) {
        this.id = id;
        this.jobTitle = jobTitle;
        this.locate = locate;
        this.salary = salary;
        this.status = status;
        this.categoryName = categoryName;
        this.jobTypeName = jobTypeName;
        this.applicantCount = applicantCount;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getJobTypeName() {
        return jobTypeName;
    }

    public void setJobTypeName(String jobTypeName) {
        this.jobTypeName = jobTypeName;
    }

    public long getApplicantCount() {
        return applicantCount;
    }

    public void setApplicantCount(long applicantCount) {
        this.applicantCount = applicantCount;
    }

    private String jobTypeName;
    private long applicantCount;
}
