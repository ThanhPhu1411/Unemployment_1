package com.example.demo.repository;

import com.example.demo.model.SavedJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface SaveJobRepository
extends JpaRepository<SavedJob, Long> {

    void deleteByUserIdAndJobId(UUID userId, UUID jobId);

    boolean existsByUserIdAndJobId(UUID userId, UUID jobId);

    List<SavedJob> findByUserId(UUID userId);
}
