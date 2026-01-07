package com.example.demo.service;

import com.example.demo.dto.request.CandidateProfileDTO;
import com.example.demo.model.CandidateProfile;
import com.example.demo.repository.CandidateProfileRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Service
public class CandidateProfileService {

    private final CandidateProfileRepository candidateProfileRepository;
    private final UserRepository userRepository;

    private static final String STATIC_UPLOAD_ROOT =
            new File("src/main/resources/static/uploads").getAbsolutePath();
    private static final String AVATAR_DIR = STATIC_UPLOAD_ROOT + "/avatars/";

    public CandidateProfileService(
            CandidateProfileRepository candidateProfileRepository,
            UserRepository userRepository
    ) {
        this.candidateProfileRepository = candidateProfileRepository;
        this.userRepository = userRepository;
    }

    /* ===================== MAP ENTITY -> DTO ===================== */
    private CandidateProfileDTO mapToDTO(CandidateProfile c) {
        return new CandidateProfileDTO(
                c.getId(),
                c.getUserName(),
                c.getUserPosition(),
                c.getUserEmail(),
                c.getUserPhone(),
                c.getUserAddress(),
                c.getUserBirthDate(),
                c.getUserFacebook(),
                c.getUserAvatar(),
                c.getCareerObjective(),
                c.getEducationYear(),
                c.getEducation(),
                c.getExperienceYear(),
                c.getExperience(),
                c.getDesiredSalary(),
                c.getUserDesiredJob(),
                c.getCertificateYear(),
                c.getCertificateName(),
                c.getPrizeYear(),
                c.getPrizeDesc(),
                c.getLanguage(),
                c.getSoftSkill(),
                c.getInterest()
        );
    }

    /* ===================== GET ALL ===================== */
    public List<CandidateProfileDTO> getCandidateProfiles() {
        return candidateProfileRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    /* ===================== GET BY ID ===================== */
    public CandidateProfileDTO getCandidateProfile(UUID id) {
        CandidateProfile entity = candidateProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ"));
        return mapToDTO(entity);
    }
    public CandidateProfile getEntityById(UUID id) {
        return candidateProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ"));
    }

    /* ===================== GET MY PROFILE ===================== */
    public CandidateProfileDTO getMyCandidate(UUID userId) {
        CandidateProfile entity = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Bạn chưa có hồ sơ"));
        return mapToDTO(entity);
    }

    /* ===================== CREATE ===================== */
    public CandidateProfileDTO create(
            CandidateProfile candidateProfile,
            MultipartFile avatar,
            UUID userId
    ) throws IOException {

        candidateProfile.setUser(
                userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User không tồn tại"))
        );

        if (avatar != null && !avatar.isEmpty()) {
            Files.createDirectories(Path.of(AVATAR_DIR));
            String fileName = avatar.getOriginalFilename();
            avatar.transferTo(new File(AVATAR_DIR + fileName));
            candidateProfile.setUserAvatar("/uploads/avatars/" + fileName);
        }

        CandidateProfile saved = candidateProfileRepository.save(candidateProfile);
        return mapToDTO(saved);
    }

    /* ===================== UPDATE ===================== */
    public CandidateProfileDTO updateCandidateProfile(
            UUID id,
            CandidateProfile newData,
            MultipartFile avatar
    ) throws IOException {

        CandidateProfile existing = candidateProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không có hồ sơ"));

        if (newData.getUserName() != null)
            existing.setUserName(newData.getUserName());

        if (newData.getUserPosition() != null)
            existing.setUserPosition(newData.getUserPosition());

        if (newData.getUserEmail() != null)
            existing.setUserEmail(newData.getUserEmail());

        if (newData.getUserPhone() != null)
            existing.setUserPhone(newData.getUserPhone());

        if (newData.getUserAddress() != null)
            existing.setUserAddress(newData.getUserAddress());

        if (newData.getUserBirthDate() != null)
            existing.setUserBirthDate(newData.getUserBirthDate());

        if (newData.getUserFacebook() != null)
            existing.setUserFacebook(newData.getUserFacebook());

        if (newData.getCareerObjective() != null)
            existing.setCareerObjective(newData.getCareerObjective());

        if (newData.getEducationYear() != null)
            existing.setEducationYear(newData.getEducationYear());

        if (newData.getEducation() != null)
            existing.setEducation(newData.getEducation());

        if (newData.getExperienceYear() != null)
            existing.setExperienceYear(newData.getExperienceYear());

        if (newData.getExperience() != null)
            existing.setExperience(newData.getExperience());

        if (newData.getDesiredSalary() != null)
            existing.setDesiredSalary(newData.getDesiredSalary());

        if (newData.getUserDesiredJob() != null)
            existing.setUserDesiredJob(newData.getUserDesiredJob());

        if (newData.getCertificateYear() != null)
            existing.setCertificateYear(newData.getCertificateYear());

        if (newData.getCertificateName() != null)
            existing.setCertificateName(newData.getCertificateName());

        if (newData.getPrizeYear() != null)
            existing.setPrizeYear(newData.getPrizeYear());

        if (newData.getPrizeDesc() != null)
            existing.setPrizeDesc(newData.getPrizeDesc());

        if (newData.getLanguage() != null)
            existing.setLanguage(newData.getLanguage());

        if (newData.getSoftSkill() != null)
            existing.setSoftSkill(newData.getSoftSkill());

        if (newData.getInterest() != null)
            existing.setInterest(newData.getInterest());

        if (avatar != null && !avatar.isEmpty()) {
            Files.createDirectories(Path.of(AVATAR_DIR));
            String fileName = avatar.getOriginalFilename();
            avatar.transferTo(new File(AVATAR_DIR + fileName));
            existing.setUserAvatar("/uploads/avatars/" + fileName);
        }

        return mapToDTO(candidateProfileRepository.save(existing));
    }
//    public CandidateProfileDTO updateCandidateProfile(
//            UUID id,
//            CandidateProfile newData // chỉ nhận JSON
//    ) {
//        if (newData == null) {
//            throw new RuntimeException("Dữ liệu hồ sơ không được để trống");
//        }
//
//        CandidateProfile existing = candidateProfileRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Không có hồ sơ"));
//
//        if (newData.getUserName() != null)
//            existing.setUserName(newData.getUserName());
//
//        if (newData.getUserPosition() != null)
//            existing.setUserPosition(newData.getUserPosition());
//
//        if (newData.getUserEmail() != null)
//            existing.setUserEmail(newData.getUserEmail());
//
//        if (newData.getUserPhone() != null)
//            existing.setUserPhone(newData.getUserPhone());
//
//        if (newData.getUserAddress() != null)
//            existing.setUserAddress(newData.getUserAddress());
//
//        if (newData.getUserBirthDate() != null)
//            existing.setUserBirthDate(newData.getUserBirthDate());
//
//        if (newData.getUserFacebook() != null)
//            existing.setUserFacebook(newData.getUserFacebook());
//
//        if (newData.getCareerObjective() != null)
//            existing.setCareerObjective(newData.getCareerObjective());
//
//        if (newData.getEducationYear() != null)
//            existing.setEducationYear(newData.getEducationYear());
//
//        if (newData.getEducation() != null)
//            existing.setEducation(newData.getEducation());
//
//        if (newData.getExperienceYear() != null)
//            existing.setExperienceYear(newData.getExperienceYear());
//
//        if (newData.getExperience() != null)
//            existing.setExperience(newData.getExperience());
//
//        if (newData.getDesiredSalary() != null)
//            existing.setDesiredSalary(newData.getDesiredSalary());
//
//        if (newData.getUserDesiredJob() != null)
//            existing.setUserDesiredJob(newData.getUserDesiredJob());
//
//        if (newData.getCertificateYear() != null)
//            existing.setCertificateYear(newData.getCertificateYear());
//
//        if (newData.getCertificateName() != null)
//            existing.setCertificateName(newData.getCertificateName());
//
//        if (newData.getPrizeYear() != null)
//            existing.setPrizeYear(newData.getPrizeYear());
//
//        if (newData.getPrizeDesc() != null)
//            existing.setPrizeDesc(newData.getPrizeDesc());
//
//        if (newData.getLanguage() != null)
//            existing.setLanguage(newData.getLanguage());
//
//        if (newData.getSoftSkill() != null)
//            existing.setSoftSkill(newData.getSoftSkill());
//
//        if (newData.getInterest() != null)
//            existing.setInterest(newData.getInterest());
//
//        // Không còn avatar
//        // if (avatar != null ...) { ... }
//
//        return mapToDTO(candidateProfileRepository.save(existing));
//    }


    /* ===================== DELETE ===================== */
    public void deleteCandidateProfile(UUID id) {
        candidateProfileRepository.deleteById(id);
    }
}
