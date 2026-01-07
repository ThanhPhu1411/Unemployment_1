package com.example.demo.controller;

import com.example.demo.model.JobType;
import com.example.demo.service.JobTypeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/JobType")
public class JobTypeController {
    private final JobTypeService jobTypeService;
    public JobTypeController(JobTypeService jobTypeService)
    {
        this.jobTypeService = jobTypeService;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createJobType")
    public JobType createJobType (@RequestBody JobType jobType)
    {
        return jobTypeService.createJobType(jobType);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public JobType getJobType (@PathVariable("id") Long id)
    {
        return jobTypeService.getJobTypeById(id);
    }
    @GetMapping()
    public List<JobType> getJobType()
    {
        return jobTypeService.getJobType();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public JobType updateJobType (@PathVariable("id") Long id, @RequestBody JobType jobType)
    {
        return jobTypeService.updateJobType(id,jobType);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteJobType(@PathVariable("id") Long id)
    {
        jobTypeService.deleteJobType(id);
        return "Hình thức làm việc đã được xóa thành công";
    }
}
