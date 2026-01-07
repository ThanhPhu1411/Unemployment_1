package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="applications")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID  id;

    @Nationalized
    private String status;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public CandidateProfile getCandidateProfile() {
        return candidateProfile;
    }

    public void setCandidateProfile(CandidateProfile candidateProfile) {
        this.candidateProfile = candidateProfile;
    }

    public LocalDateTime getApplyDate() {
        return ApplyDate;
    }

    public void setApplyDate(LocalDateTime applyDate) {
        ApplyDate = applyDate;
    }

    public String getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(String saveStatus) {
        this.saveStatus = saveStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Nationalized
    private String saveStatus;
    private LocalDateTime  ApplyDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name="userID")
    private CandidateProfile candidateProfile;

    @ManyToOne
    @JoinColumn(name="jobId")
    private Job job;


}
