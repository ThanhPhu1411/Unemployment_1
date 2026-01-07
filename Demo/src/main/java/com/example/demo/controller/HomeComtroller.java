package com.example.demo.controller;

import com.example.demo.dto.request.JobHomeDTO;
import com.example.demo.model.Job;
import com.example.demo.service.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeComtroller {
    private final JobService jobService;

    public HomeComtroller(JobService jobService) {
        this.jobService = jobService;
    }
    @GetMapping("/jobs")
    public ResponseEntity<List<JobHomeDTO>> getApprovedJobs() {
        return ResponseEntity.ok(jobService.getApprovedJobs());

    }
}
