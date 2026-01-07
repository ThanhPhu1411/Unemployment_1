package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "candidate_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidateProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name="user-id")
    private User user;
    @Nationalized
    private String userName;
    @Nationalized
    private String userPosition;
    private String userEmail;
    private String userPhone;
    @Nationalized
    private String userAddress;

   private LocalDate userBirthDate;
    @Nationalized
    private String userFacebook;
    private String userAvatar;

    @Nationalized
    private String careerObjective;

    private String educationYear;
    @Nationalized
    private String education;

    private String experienceYear;
    @Nationalized
    private String experience;

    private String desiredSalary;
    @Nationalized
    private String userDesiredJob;

    private String certificateYear;
    @Nationalized
    private String certificateName;

    private String prizeYear;
    @Nationalized
    private String prizeDesc;
    @Nationalized
    private String language;
    @Nationalized
    private String softSkill;
    @Nationalized
    private String interest;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(String userPosition) {
        this.userPosition = userPosition;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public LocalDate getUserBirthDate() {
        return userBirthDate;
    }

    public void setUserBirthDate(LocalDate userBirthDate) {
        this.userBirthDate = userBirthDate;
    }

    public String getUserFacebook() {
        return userFacebook;
    }

    public void setUserFacebook(String userFacebook) {
        this.userFacebook = userFacebook;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getCareerObjective() {
        return careerObjective;
    }

    public void setCareerObjective(String careerObjective) {
        this.careerObjective = careerObjective;
    }

    public String getEducationYear() {
        return educationYear;
    }

    public void setEducationYear(String educationYear) {
        this.educationYear = educationYear;
    }

    public String getExperienceYear() {
        return experienceYear;
    }

    public void setExperienceYear(String experienceYear) {
        this.experienceYear = experienceYear;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getDesiredSalary() {
        return desiredSalary;
    }

    public void setDesiredSalary(String desiredSalary) {
        this.desiredSalary = desiredSalary;
    }

    public String getUserDesiredJob() {
        return userDesiredJob;
    }

    public void setUserDesiredJob(String userDesiredJob) {
        this.userDesiredJob = userDesiredJob;
    }

    public String getCertificateYear() {
        return certificateYear;
    }

    public void setCertificateYear(String certificateYear) {
        this.certificateYear = certificateYear;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public String getPrizeYear() {
        return prizeYear;
    }

    public void setPrizeYear(String prizeYear) {
        this.prizeYear = prizeYear;
    }

    public String getPrizeDesc() {
        return prizeDesc;
    }

    public void setPrizeDesc(String prizeDesc) {
        this.prizeDesc = prizeDesc;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSoftSkill() {
        return softSkill;
    }

    public void setSoftSkill(String softSkill) {
        this.softSkill = softSkill;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }
}
