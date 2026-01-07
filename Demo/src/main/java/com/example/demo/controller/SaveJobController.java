package com.example.demo.controller;

import com.example.demo.dto.request.ApplicationDTO;
import com.example.demo.dto.request.SaveJobDTO;
import com.example.demo.model.Job;
import com.example.demo.model.SavedJob;
import com.example.demo.service.AuthService;
import com.example.demo.service.SavaJobService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/savejob")
@PreAuthorize("hasRole('USER')")
public class SaveJobController {
    private final SavaJobService savaJobService;
    public SaveJobController (SavaJobService savaJobService)
    {
        this.savaJobService = savaJobService;
    }
    @PostMapping("/save/{jobId}")
    public ResponseEntity<?> saveJob
            (@PathVariable UUID jobId, Authentication authentication)
    {

        UUID userId = UUID.fromString(authentication.getName());
        SavedJob savedJob = savaJobService.savedJob(userId, jobId);
        Job job = savedJob.getJob();

        SaveJobDTO dto = new SaveJobDTO(
                savedJob.getId(),
                savedJob.getJob().getId(),
                savedJob.getJob().getJobTitle(),
                savedJob.getJob().getLocate(),
                savedJob.getJob().getSalary(),
                savedJob.getJob().getJobType().getName(),
                savedJob.getJob().getEmployer().getCompanyLogo(),
                savedJob.getJob().getEmployer().getCompanyName()
                );

        return ResponseEntity.ok(dto);
    }
    @GetMapping
    public ResponseEntity<List<SaveJobDTO>> getSaveJob(Authentication authentication)
    {
        UUID userId = UUID.fromString(authentication.getName());
        List<SavedJob> savedJobs = savaJobService.getSaveJob(userId);
        List<SaveJobDTO> dtos = savedJobs.stream().map(savedJob -> {
            return new SaveJobDTO(
                    savedJob.getId(),
                    savedJob.getJob().getId(),
                    savedJob.getJob().getJobTitle(),
                    savedJob.getJob().getLocate(),
                    savedJob.getJob().getSalary(),
                    savedJob.getJob().getJobType().getName(),
                    savedJob.getJob().getEmployer().getCompanyLogo(),
                    savedJob.getJob().getEmployer().getCompanyName()
            );
        }).toList();
        return ResponseEntity.ok(dtos);
    }
    @Transactional
    @DeleteMapping("/save/{jobId}")
    public ResponseEntity<?> unSave
            (@PathVariable UUID jobId, Authentication authentication)
    {

        UUID userId = UUID.fromString(authentication.getName());
        savaJobService.unSave(userId, jobId);
        return ResponseEntity.ok("Đã hủy lưu công việc");
    }
}
