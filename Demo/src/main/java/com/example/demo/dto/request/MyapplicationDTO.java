package com.example.demo.dto.request;

import java.time.LocalDateTime;
import java.util.UUID;

public class MyapplicationDTO {
    private UUID myapplicationId;  // id bản ghi saved job
    private UUID jobId;      // id thực của job
    private String jobTitle;
    private String locate;
    private String salary;
    private String jobType;
    private String companyLogo;


    public MyapplicationDTO(UUID myapplicationId, UUID jobId, String jobTitle, String locate, String salary, String jobType, String companyLogo, String companyName, String status, LocalDateTime applyDate) {
        this.myapplicationId = myapplicationId;
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.locate = locate;
        this.salary = salary;
        this.jobType = jobType;
        this.companyLogo = companyLogo;
        this.companyName = companyName;
        this.status = status;
        ApplyDate = applyDate;
    }

    public UUID getMyapplicationId() {
        return myapplicationId;
    }

    public void setMyapplicationId(UUID myapplicationId) {
        this.myapplicationId = myapplicationId;
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

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getApplyDate() {
        return ApplyDate;
    }

    public void setApplyDate(LocalDateTime applyDate) {
        ApplyDate = applyDate;
    }

    private String companyName;
    private String status;
    private LocalDateTime ApplyDate = LocalDateTime.now();
}
