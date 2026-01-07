package com.example.demo.service;

import com.example.demo.model.JobType;
import com.example.demo.repository.JobTypeRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class JobTypeService {

    private final JobTypeRepository jobTypeRepository;
    public JobTypeService(JobTypeRepository jobTypeRepository)
    {
        this.jobTypeRepository = jobTypeRepository;
    }
    public List<JobType> getJobType(){
        return jobTypeRepository.findAll();
    }
    public JobType getJobTypeById(Long id)
    {
        return jobTypeRepository.findById(id).orElse(null);
    }
    public JobType createJobType (JobType jobType)
    {
        if (jobTypeRepository.existsByName(jobType.getName()))
            throw  new RuntimeException("Hình thức làm việc này đã tồn tại");
        return jobTypeRepository.save(jobType);
    }
    public JobType updateJobType(Long id, JobType jobType)
    {
        if (jobTypeRepository.existsByName(jobType.getName()))
            throw  new RuntimeException("Hình thức làm việc này đã tồn tại");
        JobType existingJobType = getJobTypeById(id);
        existingJobType.setName(jobType.getName());
        return jobTypeRepository.save(existingJobType);
    }
    public void deleteJobType(long id)
    {
        jobTypeRepository.deleteById(id);
    }
}
