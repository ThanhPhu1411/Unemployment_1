package com.example.demo.repository;

import com.example.demo.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface
JobRepository
        extends JpaRepository<Job,UUID> {
    List<Job> findByStatus(String status);
    List<Job> findByEmployerId(UUID employerId);
    @Query("""
    SELECT j FROM Job j
    WHERE (:status IS NULL OR TRIM(j.status) = :status)
      AND (:keyword IS NULL OR LOWER(j.jobTitle) LIKE LOWER(CONCAT('%', :keyword, '%')))
    AND (:locate IS NULL OR LOWER(j.locate) LIKE LOWER(CONCAT('%', :locate, '%')))
    AND (:categoryName IS NULL OR LOWER(j.category.name) = LOWER(:categoryName))
    AND (:jobTypeName IS NULL OR LOWER(j.jobType.name) = LOWER(:jobTypeName))
  ORDER BY j.postedDate DESC
""")
    List<Job> searchJobs(
            @Param("status") String status,
            @Param("keyword") String keyword,
            @Param("locate") String locate,
            @Param("categoryName") String categoryName,
            @Param("jobTypeName") String jobTypeName
    );
    @Query("""
    SELECT j FROM Job j
    WHERE j.employer.id = :employerId
      AND (:keyword IS NULL OR LOWER(j.jobTitle) LIKE LOWER(CONCAT('%', :keyword, '%')))
    ORDER BY j.postedDate DESC
""")
    List<Job> searchJobsForEmployer(
            @Param("employerId") UUID employerId,
            @Param("keyword") String keyword
    );






}
