package com.example.demo.repository;

import com.example.demo.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotificationRepository
extends JpaRepository<Notification, Long> {
    List<Notification> findByUserIdOrderBySentDateDesc(UUID userId);

    Optional<Notification> findByIdAndUserId(Long id, UUID userId);
}
