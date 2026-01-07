package com.example.demo.dto.request;

import java.util.UUID;

public class SaveJobDTO {
    private long saveJobId;  // id bản ghi saved job
    private UUID jobId;      // id thực của job
    private String jobTitle;
    private String locate;
    private String salary;
    private String jobType;


    public String getCompanyLogo() {
        return companyLogo;
    }

    public SaveJobDTO(long saveJobId, UUID jobId, String jobTitle, String locate, String salary, String jobType, String companyLogo, String companyName) {
        this.saveJobId = saveJobId;
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.locate = locate;
        this.salary = salary;
        this.jobType = jobType;
        this.companyLogo = companyLogo;
        this.companyName = companyName;
    }
    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public long getSaveJobId() {
        return saveJobId;
    }

    public void setSaveJobId(long saveJobId) {
        this.saveJobId = saveJobId;
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

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    private String companyLogo;
    private String companyName;

}
