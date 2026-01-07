package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.ApplicationRepository;
import com.example.demo.repository.CandidateProfileRepository;
import com.example.demo.repository.JobRepository;
import com.example.demo.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ApplicationService {
    private CandidateProfileRepository candidateProfileRepository;
    private final ApplicationRepository applicationRepository;
    private final NotificationRepository notificationRepository;
    private final JobRepository jobRepository;
    public ApplicationService(ApplicationRepository applicationRepository,
                              JobRepository jobRepository,
                              CandidateProfileRepository candidateProfileRepository,
                              NotificationRepository notificationRepository)
    {
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
        this.candidateProfileRepository=candidateProfileRepository;
        this.notificationRepository = notificationRepository;
    }
    public Application apply (UUID userId, UUID jobId)
    {
        CandidateProfile candidateProfile = candidateProfileRepository.findByUserId(userId)
                .orElseThrow(()->new RuntimeException("Bạn chưa có hồ sơ ứng viên"));
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy công việc"));

        if (!"Đã duyệt".equals(job.getStatus()))
            throw new RuntimeException("Công việc này chưa được duyệt. Không thể ứng tuyển.");
        if(applicationRepository.existsByCandidateProfileIdAndJobId(candidateProfile.getId(),jobId))
        {
            throw new RuntimeException("Bạn đã ứng tuyển công việc này rồi");
        }
        Application app = new Application();
        app.setStatus("Đang chờ");
        app.setSaveStatus("Đã ứng tuyển");
        app.setCandidateProfile(candidateProfile);
        app.setJob(job);
        sendNotificationToEmployer(job, candidateProfile);
        return applicationRepository.save(app);
    }
    public List<Application> getApplications (UUID userId)
    {
        return applicationRepository.findByCandidateProfileUserId(userId);
    }

    public List<Application> getApplicationForJob(UUID jobId, UUID employerId)
    {
        return applicationRepository.findByJobEmployerIdAndJobId(employerId, jobId);
    }

    public Application getApplicationDetail (UUID employerId, UUID applicationId)
    {
        Application app = applicationRepository.findById(applicationId)
                .orElseThrow(()->new RuntimeException("Không tìm thấy đớn ứng tuyển"));
        if(!app.getJob().getEmployer().getId().equals(employerId))
            throw new RuntimeException("Bạn không có quyền xem hồ sơ ứng tuyển này");
        return app;
    }
    public Application approvedApplication(UUID employerId, UUID applicationId)
    {
        Application app = applicationRepository.findById(applicationId)
                .orElseThrow(()->new RuntimeException("Không tìm thấy hồ sơ ứng tuyển"));
        if(!app.getJob().getEmployer().getId().equals(employerId))
            throw new RuntimeException("Bạn không có quyền thay đổi trạng thái đơn ứng tuyển này");
        app.setStatus("Đã duyệt");
        return applicationRepository.save(app);
    }
    public Application rejectApplication(UUID employerId, UUID applicationId)
    {
        Application app = applicationRepository.findById(applicationId)
                .orElseThrow(()->new RuntimeException("Không tìm thấy hồ sơ ứng tuyển"));
        if(!app.getJob().getEmployer().getId().equals(employerId))
            throw new RuntimeException("Bạn không có quyền thay đổi trạng thái đơn ứng tuyển này");
        app.setStatus("Từ chối");
        return applicationRepository.save(app);
    }
    public long countApplicationsForJob(UUID jobId, UUID employerId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy công việc"));
        if (!job.getEmployer().getId().equals(employerId)) {
            throw new RuntimeException("Bạn không có quyền xem số lượng ứng tuyển cho công việc này");
        }
        return applicationRepository.countByJobId(jobId);
    }

    private void sendNotificationToEmployer(Job job, CandidateProfile candidateProfile)
    {
        Employer employer = job.getEmployer();
        if(employer == null || employer.getUser()==null)
        {
            throw new RuntimeException("Công việc không thuộc nhà tuyển dụng này");
        }
        User employerUser = employer.getUser();

        Notification noti = new Notification();
        noti.setUser(employerUser);
        noti.setTitle("Ứng viên ứng tuyển");
        noti.setMessage(
                "Ứng viên " + candidateProfile.getUserName() +
                        " vừa ứng tuyển vào công việc: " + job.getJobTitle()
        );
        noti.setSentDate(LocalDateTime.now());
        noti.setRead(false);
        notificationRepository.save(noti);

    }

}

