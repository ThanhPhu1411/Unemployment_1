package com.example.demo.repository;

import com.example.demo.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface ApplicationRepository
extends JpaRepository<Application, UUID> {
    List<Application> findByCandidateProfileUserId(UUID userId);
    boolean existsByCandidateProfileIdAndJobId(UUID candidateId, UUID jobId);
    List<Application> findByJobEmployerIdAndJobId(UUID employerId, UUID jobId);
    long countByJobId(UUID jobId);
}
