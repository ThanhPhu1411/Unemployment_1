package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="Jobs")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Nationalized
    @Column(nullable = false)
    private String jobTitle;

    @Nationalized
    @Column(nullable = false)
    private String locate;


    @Nationalized
    @Column(nullable = false)
    private String jobDescription;

    @Nationalized
    @Column(nullable = false)
    private String requirements;

    private String salary;

    @Nationalized
    private String benefits;

    private LocalDateTime postedDate = LocalDateTime.now();

    @Nationalized
    private String status;

    private LocalDateTime applicationDeadline;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Application> applications = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="jobtypeId")
    private JobType jobType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="categoryId")
    private Category category;

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
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

    public LocalDateTime getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(LocalDateTime postedDate) {
        this.postedDate = postedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public LocalDateTime getApplicationDeadline() {
        return applicationDeadline;
    }

    public void setApplicationDeadline(LocalDateTime applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @ManyToOne
    @JoinColumn(name="employerId")
    private Employer employer;



}
