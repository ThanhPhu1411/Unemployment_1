package com.example.demo.service;

import com.example.demo.dto.request.*;
import com.example.demo.model.Category;
import com.example.demo.model.Employer;
import com.example.demo.model.Job;
import com.example.demo.model.JobType;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class JobService {
    private final JobRepository jobRepository;
    private final EmployerRepository employerRepository;
    private final CategoryRepository categoryRepository;
    private final JobTypeRepository jobTypeRepository;
    private final ApplicationRepository applicationRepository;

    public JobService(JobRepository jobRepository, ApplicationRepository applicationRepository,EmployerRepository employerRepository,CategoryRepository categoryRepository,JobTypeRepository jobTypeRepository) {
        this.jobRepository = jobRepository;
        this.employerRepository = employerRepository;
        this.categoryRepository= categoryRepository;
        this.jobTypeRepository=jobTypeRepository;
        this.applicationRepository = applicationRepository;
    }

    public Job createJobForEmployer(UUID userId, JobCreateRequestDTO dto) {

        Employer employer = employerRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Bạn chưa tạo thông tin công ty"));

        if (!"Đã duyệt".equalsIgnoreCase(employer.getStatus())) {
            throw new RuntimeException("Công ty chưa được duyệt");
        }

        JobType jobType = jobTypeRepository.findById(dto.getJobTypeId())
                .orElseThrow(() -> new RuntimeException("JobType không tồn tại"));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category không tồn tại"));

        Job job = new Job();
        job.setJobTitle(dto.getJobTitle());
        job.setLocate(dto.getLocate());
        job.setJobDescription(dto.getJobDescription());
        job.setRequirements(dto.getRequirements());
        job.setSalary(dto.getSalary());
        job.setBenefits(dto.getBenefits());
        job.setApplicationDeadline(dto.getApplicationDeadline());

        job.setEmployer(employer);
        job.setJobType(jobType);
        job.setCategory(category);
        job.setStatus("Đang chờ");

        return jobRepository.save(job);
    }

    public Job getJobById(UUID id)
    {
        return  jobRepository.findById(id).orElse(null);
    }

    public List<Job> getJobByEmployer(UUID employerId)
    {
        return jobRepository.findByEmployerId(employerId);
    }
    public JobDetailResponse getJobDetail(UUID id)
    {
        Job job = jobRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Không tìm thấy công việc"));
        return new JobDetailResponse(
                job.getId(),
                job.getJobTitle(),
                job.getSalary(),
                job.getLocate(),
                job.getApplicationDeadline(),
                job.getPostedDate(),

                job.getJobDescription(),
                job.getRequirements(),
                job.getBenefits(),
                job.getJobType().getName(),
                job.getCategory().getName(),
                job.getEmployer().getId(),
                job.getEmployer().getCompanyName(),
                job.getEmployer().getCompanyEmail(),
                job.getEmployer().getCompanySize(),
                job.getEmployer().getLicenseDocument(),
                job.getEmployer().getCompanyDescription(),
                job.getEmployer().getLatitude(),
                job.getEmployer().getLongitude(),
                job.getEmployer().getCompanyAddress(),
                job.getEmployer().getCompanyLogo(),
                job.getStatus()
        );
    }

    public JobDetailResponseDTO updateJobForEmployer(
            UUID userId,
            UUID jobId,
            JobUpdateRequestDTO dto
    ) {

        Job existing = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy công việc"));

        Employer employer = employerRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Bạn chưa tạo thông tin công ty"));

        // check quyền
        if (!existing.getEmployer().getId().equals(employer.getId())) {
            throw new RuntimeException("Bạn không có quyền sửa công việc này");
        }

        // update các field
        existing.setJobTitle(dto.getJobTitle());
        existing.setLocate(dto.getLocate());
        existing.setJobDescription(dto.getJobDescription());
        existing.setRequirements(dto.getRequirements());
        existing.setBenefits(dto.getBenefits());
        existing.setSalary(dto.getSalary());
        existing.setApplicationDeadline(dto.getApplicationDeadline());

        // update category
        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category không tồn tại"));
            existing.setCategory(category);
        }

        // update jobType
        if (dto.getJobTypeId() != null) {
            JobType jobType = jobTypeRepository.findById(dto.getJobTypeId())
                    .orElseThrow(() -> new RuntimeException("JobType không tồn tại"));
            existing.setJobType(jobType);
        }

        existing.setStatus("Đang chờ"); // reset trạng thái

        Job saved = jobRepository.save(existing);

        // map sang DTO (trả cả ID + name)
        return new JobDetailResponseDTO(
                saved.getId(),
                saved.getJobTitle(),
                saved.getLocate(),
                saved.getJobDescription(),
                saved.getRequirements(),
                saved.getBenefits(),
                saved.getSalary(),
                saved.getStatus(),
                saved.getPostedDate(),
                saved.getApplicationDeadline(),
                saved.getCategory() != null ? saved.getCategory().getName() : null, // phải là String
                saved.getJobType() != null ? saved.getJobType().getName() : null     // phải là String
        );

    }



    public void deleteJobForEmployer(UUID userId, UUID jobId) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy công việc"));

        Employer employer = employerRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Bạn chưa tạo thông tin công ty"));

        if (!job.getEmployer().getId().equals(employer.getId())) {
            throw new RuntimeException("Bạn không có quyền xóa công việc này");
        }

        jobRepository.delete(job);
    }

    public Job approvedJob(UUID id)
    {
        Job existingJob = getJobById(id);
        existingJob.setStatus("Đã duyệt");
        return jobRepository.save(existingJob);
    }
    public Job rejectJob(UUID id)
    {
        Job existingJob = getJobById(id);
        existingJob.setStatus("Từ chối");
        return jobRepository.save(existingJob);
    }

    public List<JobHomeDTO> getApprovedJobs() {
        return jobRepository.findByStatus("Đã duyệt")
                .stream()
                .map(
                        job -> new JobHomeDTO(
                                job.getId(),
                                job.getJobTitle(),
                                job.getJobDescription(),
                                job.getLocate(),
                                job.getSalary(),
                                job.getApplicationDeadline(),
                                job.getEmployer().getCompanyLogo()
                )).toList();
    }


    public List<JobDTO> searchJobsForCandidate(
            String keyword,
            String locate,
            String categoryName,
            String jobTypeName
    ) {
        List<Job> jobs = jobRepository.searchJobs(
                "Đã duyệt",
                keyword,   // để, null nếu không filter
                locate,
                categoryName,
                jobTypeName
        );

        return jobs.stream()
                .map(job -> new JobDTO(
                        job.getId(),
                        job.getJobTitle(),
                        job.getLocate(),
                        job.getSalary(),
                        job.getJobType().getName(),
                        job.getCategory().getName(),
                        job.getPostedDate(),
                        job.getApplicationDeadline(),
                        job.getEmployer().getCompanyName(),
                       job.getEmployer().getCompanyLogo(),
                        job.getStatus()
                ))
                .collect(Collectors.toList());
    }

    public List<JobDTO> getJob()
    {
        return jobRepository.findAll().stream().map(
                job ->  new JobDTO(
                        job.getId(),
                        job.getJobTitle(),
                        job.getLocate(),
                        job.getSalary(),
                        job.getJobType().getName(),
                        job.getCategory().getName(),
                        job.getPostedDate(),
                        job.getApplicationDeadline(),
                        job.getEmployer().getCompanyName(),
                        job.getEmployer().getCompanyLogo(),
                        job.getStatus()
                )
        ).toList();
    }

    public List<MyJobDTO> searchJobByTitleForEmployer(UUID employerId, String keyword) {

        if (keyword != null && keyword.trim().isEmpty()) {
            keyword = null;
        }
        return  jobRepository.searchJobsForEmployer(employerId, keyword).stream().map(
                job -> new MyJobDTO(
                        job.getId(),
                        job.getJobTitle(),
                        job.getLocate(),
                        job.getSalary(),
                        job.getStatus(),
                        job.getCategory().getName(),
                        job.getJobType().getName(),
                        applicationRepository.countByJobId(job.getId())
                )).toList();
    }


    public List<MyJobDTO> getJobEmployer(UUID userId)
    {
        Employer employer =  employerRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Bạn chưa tạo thông tin công ty"));
        return jobRepository.findByEmployerId(employer.getId())
                .stream()
                .map(job -> new MyJobDTO(
                        job.getId(),
                        job.getJobTitle(),
                        job.getLocate(),
                        job.getSalary(),
                        job.getStatus(),
                        job.getCategory().getName(),
                        job.getJobType().getName(),
                        applicationRepository.countByJobId(job.getId())
                ))
                .toList();
    }
    public JobDetailResponseDTO getJobDetailForEmployer(UUID userId, UUID jobId) {
        Employer employer = employerRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Bạn chưa tạo thông tin công ty"));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy công việc"));

        if (!job.getEmployer().getId().equals(employer.getId())) {
            throw new RuntimeException("Bạn không có quyền xem công việc này");
        }

        return new JobDetailResponseDTO(
                job.getId(),
                job.getJobTitle(),
                job.getLocate(),
                job.getJobDescription(),
                job.getRequirements(),
                job.getBenefits(),
                job.getSalary(),
                job.getStatus(),
                job.getPostedDate(),
                job.getApplicationDeadline(),
                job.getCategory().getName(),
                job.getJobType().getName()
        );
    }

}
