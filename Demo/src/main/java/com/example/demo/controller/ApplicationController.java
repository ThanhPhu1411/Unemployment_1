package com.example.demo.controller;

import com.example.demo.dto.request.ApplicationDTO;
import com.example.demo.dto.request.MyapplicationDTO;
import com.example.demo.model.Application;
import com.example.demo.model.CandidateProfile;
import com.example.demo.service.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/application")
@PreAuthorize("hasRole('USER')")
public class ApplicationController {
    private final ApplicationService applicationService;
    public ApplicationController(ApplicationService applicationService)
    {
        this.applicationService = applicationService;
    }
    @PostMapping("/apply/{jobId}")
    public ResponseEntity<?> applyJob
            (@PathVariable UUID jobId, Authentication authentication)
    {
        UUID userId = UUID.fromString(authentication.getName());
        Application app = applicationService.apply(userId,jobId);
        ApplicationDTO applicationDTO = new ApplicationDTO(
                app.getId(),
                app.getStatus(),

                "Đã ứng tuyển",
                app.getCandidateProfile().getId(),
                app.getCandidateProfile().getUserName(),
                app.getCandidateProfile().getUserEmail(),
                app.getCandidateProfile().getUserPhone(),
                app.getCandidateProfile().getUserAvatar()
                );
        return ResponseEntity.ok(applicationDTO);
    }
    @GetMapping("/my-applications")
    public ResponseEntity<List<MyapplicationDTO>> getApplication(Authentication authentication) {
        UUID userId = UUID.fromString(authentication.getName());
        List<Application> applications = applicationService.getApplications(userId);
        List<MyapplicationDTO> dtos = applications.stream().map(app -> {
            return new MyapplicationDTO(
                    app.getId(),
                    app.getJob().getId(),
                    app.getJob().getJobTitle(),
                    app.getJob().getLocate(),
                    app.getJob().getSalary(),
                    app.getJob().getJobType().getName(),
                    app.getJob().getEmployer().getCompanyLogo(),
                    app.getJob().getEmployer().getCompanyName(),
                    app.getStatus(),
                    app.getApplyDate()
            );
        }).toList();

        return ResponseEntity.ok(dtos);
    }
}
