package com.example.demo.controller;

import com.example.demo.dto.request.*;
import com.example.demo.model.*;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.service.ApplicationService;
import com.example.demo.service.EmployerService;
import com.example.demo.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employer")
public class EmployerController {
    private final EmployerService employerService;
    private final ApplicationService applicationService;
    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;
    public EmployerController(EmployerService employerService,
                              ApplicationService applicationService,
                              NotificationService notificationService,
                              NotificationRepository notificationRepository)
    {
        this.employerService = employerService;
        this.applicationService = applicationService;
        this.notificationService = notificationService;
        this.notificationRepository =notificationRepository;
    }
    @PreAuthorize("hasRole('EMPLOYER')")
    @PostMapping("/createEmployer")
    public Employer createEmployer(
            @RequestPart("employer") Employer employer,
            @RequestPart(value = "logo", required = false) MultipartFile logo,
            @RequestPart(value = "license", required = false) MultipartFile license,
            Authentication authentication
    ) throws IOException {
         UUID userId = UUID.fromString(authentication.getName());
        return employerService.create(employer, logo, license);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public AdminEmployerDTO getEmployer(@PathVariable("id")UUID id)
    {
        return  employerService.getEmployerAdminById(id);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<EmployerDTO> getEmployers()
    {
        return  employerService.getEmployers();
    }
    @PreAuthorize("hasRole('EMPLOYER')")
    @PutMapping("/{id}")
//    public Employer updateEmployer(
//            @PathVariable UUID id,
//            @RequestPart(value="employer",required = false) Employer employer,
//            @RequestPart(value = "logo", required = false) MultipartFile logo,
//            @RequestPart(value = "license", required = false) MultipartFile license,
//            Authentication authentication
//    ) throws IOException {
//
//        UUID userId = UUID.fromString(authentication.getName());
//
//        Employer myEmployer = employerService.getMyEmployer(userId);
//
//        if (!myEmployer.getId().equals(id)) {
//            throw new RuntimeException("Bạn không có quyền cập nhật công ty này");
//        }
//        return employerService.updateEmployer(id, employer, logo, license);
//    }

    public Employer updateEmployer(
            @PathVariable UUID id,
            @RequestBody Employer employer,  // chỉ nhận JSON
            Authentication authentication
    ) {

        // Lấy userId từ token
        UUID userId = UUID.fromString(authentication.getName());

        // Kiểm tra quyền
        Employer myEmployer = employerService.getMyEmployer(userId);
        if (!myEmployer.getId().equals(id)) {
            throw new RuntimeException("Bạn không có quyền cập nhật công ty này");
        }

        // Gọi service update, logo & license = null
        try {
            return employerService.updateEmployer(id, employer, null, null);
        } catch (IOException e) {
            throw new RuntimeException("Lỗi cập nhật công ty", e);
        }
    }


    @PreAuthorize("hasRole('EMPLOYER')")
    @DeleteMapping("/me")
    public ResponseEntity<String> deleteMyCompany(Authentication authentication) {

        UUID userId = UUID.fromString(authentication.getName());

        employerService.deleteMyEmployer(userId);

        return ResponseEntity.ok("Công ty của bạn đã được xóa");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteEmployer(@PathVariable("id") UUID id)
    {
        employerService.deleteEmployer(id);
        return "Công ty đã được xóa";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/approve")
    public Employer approveEmployer(@PathVariable UUID id) {
        Employer employer = employerService.approvedEmpployer(id);
        User employerUser = employer.getUser();
        Notification noti = new Notification();
        noti.setUser(employerUser);
        noti.setTitle("Công ty được phê duyệt");
        noti.setMessage(
                "Chúc mừng! Công ty <strong>" + employer.getCompanyName() +
                        "</strong> đã được duyệt thành công. " +
                        "Giờ bạn có thể đăng tin tuyển dụng và quản lý hồ sơ ứng viên."
        );
        noti.setSentDate(LocalDateTime.now());
        noti.setRead(false);

        notificationRepository.save(noti);

        return employer;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/reject")
    public Employer rejectEmployer(@PathVariable UUID id) {
        Employer employer = employerService.rejectEmployer(id);
        User employerUser = employer.getUser();
        Notification noti = new Notification();
        noti.setUser(employerUser);
        noti.setTitle("Công ty được phê duyệt");
        noti.setMessage(
                "Rất tiếc! Công ty <strong>" + employer.getCompanyName() +
                        "</strong> chưa đáp ứng điều kiện phê duyệt. " +
                        "Vui lòng kiểm tra lại thông tin hồ sơ và gửi yêu cầu duyệt lại."
        );
        noti.setSentDate(LocalDateTime.now());
        noti.setRead(false);

        notificationRepository.save(noti);

        return employer;
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<MyEmployerDTO> getMyCompany(Authentication authentication) {
        UUID userId = UUID.fromString(authentication.getName());
        return ResponseEntity.ok(employerService.getMyEmployerDTO(userId));
    }
    @GetMapping("/{jobId}/candidates")
    public ResponseEntity<List<ApplicationDTO>> getApplicationForJob(
            @PathVariable UUID jobId,
            Authentication authentication
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        UUID employerId = employerService.getMyEmployer(userId).getId();

        List<Application> applications = applicationService.getApplicationForJob(jobId, employerId);
//applications.stream() – biến danh sách applications thành stream, cho phép xử lý từng phần tử.
//.map(app -> { ... }) – chuyển đổi từng Application thành một ApplicationDTO.
        // map Application -> ApplicationDTO
        List<ApplicationDTO> dtos = applications.stream().map(app -> {
            return new ApplicationDTO(
                    app.getId(),
                    app.getStatus(),
                    app.getSaveStatus(),
                    app.getCandidateProfile().getId(),
                    app.getCandidateProfile().getUserName(),
                    app.getCandidateProfile().getUserEmail(),
                    app.getCandidateProfile().getUserPhone(),
                    app.getCandidateProfile().getUserAvatar()
            );
        }).toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/detail/{applicationId}")
    public ResponseEntity<CandidateDetailDTO> getApplicationDetail(
            @PathVariable UUID applicationId,
            Authentication authentication
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        UUID employerId = employerService.getMyEmployer(userId).getId();

        Application app = applicationService.getApplicationDetail(employerId, applicationId);
        CandidateProfile candidate = app.getCandidateProfile();

        CandidateDetailDTO dto = new CandidateDetailDTO(
                candidate.getId(),
                candidate.getUserName(),
                candidate.getUserPosition(),
                candidate.getUserEmail(),
                candidate.getUserPhone(),
                candidate.getUserAddress(),
                candidate.getUserBirthDate(),
                candidate.getUserFacebook(),
                candidate.getUserAvatar(),
                candidate.getCareerObjective(),
                candidate.getEducationYear(),
                candidate.getEducation(),
                candidate.getExperienceYear(),
                candidate.getExperience(),
                candidate.getDesiredSalary(),
                candidate.getUserDesiredJob(),
                candidate.getCertificateYear(),
                candidate.getCertificateName(),
                candidate.getPrizeYear(),
                candidate.getPrizeDesc(),
                candidate.getLanguage(),
                candidate.getSoftSkill(),
                candidate.getInterest()
        );

        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @PutMapping("/application/approved-status/{applicationId}")
    public ResponseEntity<ApplicationDTO> approvedApplicationStatus(
            @PathVariable UUID applicationId,
            Authentication authentication
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        UUID employerId = employerService.getMyEmployer(userId).getId();
        Application updatedApp = applicationService.approvedApplication(employerId, applicationId);
        CandidateProfile candidate = updatedApp.getCandidateProfile();
        Job job = updatedApp.getJob();
        ApplicationDTO dto = new ApplicationDTO(
                updatedApp.getId(),
                updatedApp.getStatus(),
                updatedApp.getSaveStatus(),
                candidate.getId(),
                candidate.getUserName(),
                candidate.getUserEmail(),
                candidate.getUserPhone(),
                candidate.getUserAvatar()

        );
        notificationService.sendNotification(
                candidate.getUser(),
                "Đơn ứng tuyển được duyệt",
                "Chúc mừng bạn! Hồ sơ ứng tuyển của bạn đã được duyệt.<br/>" +
                        "Bạn được mời phỏng vấn vị trí <strong>" + updatedApp.getJob().getJobTitle() + "</strong> " +
                        "tại <strong>" + job.getEmployer().getCompanyName() + "</strong>.<br/>" +
                        "Vui lòng kiểm tra điện thoại để nhận thông tin phỏng vấn."

        );
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @PutMapping("/application/reject-status/{applicationId}")
    public ResponseEntity<ApplicationDTO> rejectApplicationStatus(
            @PathVariable UUID applicationId,
            Authentication authentication
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        //lấy id công ty tương ứng với người dùng đang đăng nhập
        UUID employerId = employerService.getMyEmployer(userId).getId();
        Application updatedApp = applicationService.rejectApplication(employerId, applicationId);
        CandidateProfile candidate = updatedApp.getCandidateProfile();
        Job job = updatedApp.getJob();
        ApplicationDTO dto = new ApplicationDTO(
                updatedApp.getId(),
                updatedApp.getStatus(),
                updatedApp.getSaveStatus(),
                candidate.getId(),
                candidate.getUserName(),
                candidate.getUserEmail(),
                candidate.getUserPhone(),
                candidate.getUserAvatar()
        );
        notificationService.sendNotification(
                candidate.getUser(),
                "Thông báo từ chối hồ sơ",
                "Rất tiếc! Hồ sơ ứng tuyển của bạn cho vị trí.<br/>" +
                        "<strong>" + updatedApp.getJob().getJobTitle() + "</strong> " +
                        "tại công ty <strong>" + job.getEmployer().getCompanyName() + "</strong>.<br/>" +
                        "Chưa phù hợp với yêu cầu tuyển dụng hiện tại. Chúng tôi rất cảm ơn bạn đã quan tâm và dành thời gian ứng tuyển." +
                        "Chúc bạn sớm tìm được công việc phù hợp trong thời gian tới!.<br/>" + "Bạn cũng có thể truy cập web hoặc app để xem lại chi tiết công việc.<br/>"
        );

        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @GetMapping("/application/count/{jobId}")
    public ResponseEntity<Long> countApplicationsForJob(
            @PathVariable UUID jobId,
            Authentication authentication
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        UUID employerId = employerService.getMyEmployer(userId).getId();
        long count = applicationService.countApplicationsForJob(jobId, employerId);
        return ResponseEntity.ok(count);
    }

}
