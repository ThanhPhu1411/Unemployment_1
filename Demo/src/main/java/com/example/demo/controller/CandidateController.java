package com.example.demo.controller;

import com.example.demo.dto.request.CandidateProfileDTO;
import com.example.demo.model.CandidateProfile;
import com.example.demo.service.CandidateProfileService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/candidate")
public class CandidateController {
    private final CandidateProfileService candidateProfileService;
    public CandidateController(CandidateProfileService candidateProfileService)
    {
        this.candidateProfileService = candidateProfileService;
    }
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/createCandidate")
    public CandidateProfileDTO createCandidate(
            @RequestPart("candidate") CandidateProfile candidateProfile,
            @RequestPart(value = "avatar", required = false) MultipartFile avatar,
            Authentication authentication
    ) throws IOException {

        UUID userId = UUID.fromString(authentication.getName());

        return candidateProfileService.create(candidateProfile, avatar, userId);
    }
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/updateCandidate/{id}")
//    public CandidateProfileDTO updateCandidate(
//            @PathVariable("id") UUID id,
//            @RequestPart(value = "candidate", required = false) CandidateProfile candidateProfile,
//            @RequestPart(value = "avatar" , required = false)MultipartFile avatar,
//            Authentication authentication
//    ) throws IOException{
//        UUID userId = UUID.fromString(authentication.getName());
//        CandidateProfile  existing = candidateProfileService.getEntityById(id);
//        if(!existing.getUser().getId().equals(userId))
//        {
//            throw new RuntimeException("Bạn không có quyền chính sửa hồ sơ này");
//        }
//        return candidateProfileService.updateCandidateProfile(id,candidateProfile,avatar);
//    }

    public CandidateProfileDTO updateCandidate(
            @PathVariable("id") UUID id,
            @RequestBody CandidateProfile candidateProfile, // chỉ JSON
            Authentication authentication
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        CandidateProfile existing = candidateProfileService.getEntityById(id);

        if (!existing.getUser().getId().equals(userId)) {
            throw new RuntimeException("Bạn không có quyền chỉnh sửa hồ sơ này");
        }

        // Gọi service, avatar = null
        try {
            return candidateProfileService.updateCandidateProfile(id, candidateProfile, null);
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi cập nhật hồ sơ", e);
        }

    }
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public String deleteCandidate(@PathVariable("id") UUID id,Authentication authentication)
    { UUID userId = UUID.fromString(authentication.getName());
        CandidateProfile existing = candidateProfileService.getEntityById(id);
        if(!existing.getUser().getId().equals(userId))
        {
            throw new RuntimeException("Bạn không có quyền chính sửa hồ sơ này");
        }
        candidateProfileService.deleteCandidateProfile(id);
        return "Hồ sơ đã được xóa";
    }

    @PreAuthorize("hasRole('USER')")
        @GetMapping("/me")
    public CandidateProfileDTO getMyCandidate(Authentication authentication)
    {
        UUID userId = UUID.fromString(authentication.getName());
        return candidateProfileService.getMyCandidate(userId);
    }
    @GetMapping("/{id}")
    public CandidateProfileDTO  getCandidateById(@PathVariable("id") UUID id,
                                             Authentication authentication)
    {
        UUID userId = UUID.fromString(authentication.getName());
        CandidateProfile candidateProfile = candidateProfileService.getEntityById(id);
        if (!candidateProfile.getUser().getId().equals(userId)) {
            throw new RuntimeException("Bạn không có quyền xem hồ sơ của người khác!");
        }

        return candidateProfileService.getCandidateProfile(id);
    }
}
