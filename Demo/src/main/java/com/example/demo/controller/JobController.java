package com.example.demo.controller;

import com.example.demo.dto.request.*;
import com.example.demo.model.Employer;
import com.example.demo.model.Job;
import com.example.demo.model.Notification;
import com.example.demo.model.User;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.service.AuthService;
import com.example.demo.service.EmployerService;
import com.example.demo.service.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/jobs")
public class JobController {
    private final JobService jobService;
    private final NotificationRepository notificationRepository;
    private final EmployerService employerService;

    public JobController(JobService jobService,
                         NotificationRepository notificationRepository,
                         EmployerService employerService) {
        this.jobService = jobService;
        this.notificationRepository = notificationRepository;
        this.employerService = employerService;
    }
    @PreAuthorize("hasRole('EMPLOYER')")
    @PostMapping
    public ResponseEntity<Job> createJob(
            @RequestBody JobCreateRequestDTO dto,
            Authentication authentication
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        return ResponseEntity.ok(
                jobService.createJobForEmployer(userId, dto)
        );
    }
    @PreAuthorize("hasRole('EMPLOYER')")
    @PutMapping("/{jobId}")
    public ResponseEntity<JobDetailResponseDTO> updateJob(
            @PathVariable UUID jobId,
            @RequestBody JobUpdateRequestDTO dto,
            Authentication authentication
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        JobDetailResponseDTO updated = jobService.updateJobForEmployer(userId, jobId, dto);
        return ResponseEntity.ok(updated);
    }


    @PreAuthorize("hasRole('EMPLOYER')")
    @DeleteMapping("/{jobId}")
    public ResponseEntity<String> deleteJob(
            @PathVariable UUID jobId,
            Authentication authentication
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        jobService.deleteJobForEmployer(userId, jobId);
        return ResponseEntity.ok("Xóa công việc thành công");
    }
    @GetMapping("/employer/{employerId}/jobs")
    public List<Job> getJobsByEmployer(@PathVariable("employerId") UUID employerId) {
        return jobService.getJobByEmployer(employerId);
    }

    @GetMapping(("/{id}"))
    public JobDetailResponse getJob (@PathVariable("id") UUID id)
    {
        return jobService.getJobDetail(id);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/approve")
    public Job approveJob(@PathVariable UUID id) {
        Job job = jobService.approvedJob(id);
        Employer employer = job.getEmployer();
        if (employer == null || employer.getUser() == null) {
            throw new RuntimeException("Công việc không thuộc nhà tuyển dụng");
        }
        User employerUser = employer.getUser();


        Notification noti = new Notification();
        noti.setUser(employerUser);
        noti.setTitle("Công việc được duyệt");
        noti.setMessage(
                "Công việc <strong>" + job.getJobTitle() + "</strong> " +
                        "đã được ADMIN phê duyệt.<br/>" +
                        "Bạn có thể bắt đầu nhận hồ sơ ứng tuyển từ ứng viên."
        );
        noti.setSentDate(LocalDateTime.now());
        noti.setRead(false);

        notificationRepository.save(noti);

        return job;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/reject")
    public Job rejectJob(@PathVariable UUID id) {
        Job job = jobService.rejectJob(id);
        Employer employer = job.getEmployer();
        if (employer == null || employer.getUser() == null) {
            throw new RuntimeException("Công việc không thuộc nhà tuyển dụng");
        }
        User employerUser = employer.getUser();


        Notification noti = new Notification();
        noti.setUser(employerUser);
        noti.setTitle("Công việc bị từ chối");
        noti.setMessage(
                "Rất tiếc! Công việc <strong>" + job.getJobTitle() + "</strong> " +
                        "chưa được ADMIN phê duyệt.<br/>" +
                        "Vui lòng kiểm tra lại nội dung tin tuyển dụng và gửi yêu cầu duyệt lại."
        );
        noti.setSentDate(LocalDateTime.now());
        noti.setRead(false);

        notificationRepository.save(noti);

        return job;
    }

    @GetMapping("/approved")
    public List<JobHomeDTO> getApprovedJobs() {
        return jobService.getApprovedJobs();
    }
    @GetMapping("/me")
    public List<MyJobDTO> getMyJobs (Authentication authentication)
    {
        UUID userId = UUID.fromString(authentication.getName());
        return jobService.getJobEmployer(userId);
    }
    @PreAuthorize("hasRole('EMPLOYER')")
    @GetMapping("/employer/{jobId}")
    public ResponseEntity<JobDetailResponseDTO> getJobDetail(
            @PathVariable UUID jobId,
            Authentication authentication
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        JobDetailResponseDTO response = jobService.getJobDetailForEmployer(userId, jobId);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/search")
    public List<JobDTO> searchJobs(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String locate,
            @RequestParam(required = false)  String categoryName,
            @RequestParam(required = false) String jobTypeName
    ) {
        System.out.println("keyword=[" + keyword + "], locate=[" + locate + "]");
        return jobService.searchJobsForCandidate(keyword, locate, categoryName, jobTypeName);
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @GetMapping("/employer/search")
    public List<MyJobDTO> searchMyJobs(
            @RequestParam(required = false) String keyword,
            Authentication authentication
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        UUID employerId = employerService.getMyEmployer(userId).getId();
        return jobService.searchJobByTitleForEmployer(employerId, keyword);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public List<JobDTO> getAllJobsForAdmin() {
        return jobService.getJob();
    }

}

