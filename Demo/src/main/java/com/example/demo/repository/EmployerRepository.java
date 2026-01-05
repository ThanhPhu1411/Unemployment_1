package com.example.demo.repository;

import com.example.demo.model.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface EmployerRepository
        extends JpaRepository<Employer, UUID> {
    boolean existsByCompanyName(String companyName);
    Optional<Employer> findByUserId(UUID userId);
}
