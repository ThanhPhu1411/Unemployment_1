package com.example.demo.dto.request;

import java.time.LocalDate;
import java.util.UUID;

public class CandidateDetailDTO {
    private UUID id;
    private String userName;
    private String userPosition;
    private String userEmail;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public CandidateDetailDTO(UUID id, String userName, String userPosition, String userEmail, String userPhone, String userAddress, LocalDate userBirthDate, String userFacebook, String userAvatar, String careerObjective, String educationYear, String education, String experienceYear, String experience, String desiredSalary, String userDesiredJob, String certificateYear, String certificateName, String prizeYear, String prizeDesc, String language, String softSkill, String interest) {
        this.id = id;
        this.userName = userName;
        this.userPosition = userPosition;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userAddress = userAddress;
        this.userBirthDate = userBirthDate;
        this.userFacebook = userFacebook;
        this.userAvatar = userAvatar;
        this.careerObjective = careerObjective;
        this.educationYear = educationYear;
        this.education = education;
        this.experienceYear = experienceYear;
        this.experience = experience;
        this.desiredSalary = desiredSalary;
        this.userDesiredJob = userDesiredJob;
        this.certificateYear = certificateYear;
        this.certificateName = certificateName;
        this.prizeYear = prizeYear;
        this.prizeDesc = prizeDesc;
        this.language = language;
        this.softSkill = softSkill;
        this.interest = interest;
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

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getExperienceYear() {
        return experienceYear;
    }

    public void setExperienceYear(String experienceYear) {
        this.experienceYear = experienceYear;
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

    private String userPhone;
    private String userAddress;
    private LocalDate userBirthDate;
    private String userFacebook;
    private String userAvatar;
    private String careerObjective;
    private String educationYear;
    private String education;
    private String experienceYear;
    private String experience;
    private String desiredSalary;
    private String userDesiredJob;
    private String certificateYear;
    private String certificateName;
    private String prizeYear;
    private String prizeDesc;
    private String language;
    private String softSkill;
    private String interest;
}
