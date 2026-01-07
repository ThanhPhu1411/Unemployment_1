package com.example.demo.controller;

import com.example.demo.dto.request.NotificationDTO;
import com.example.demo.model.Notification;
import com.example.demo.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService ;
    public NotificationController(NotificationService notificationService)
    {
        this.notificationService = notificationService;
    }
    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getMyNotifications (Authentication authentication)
    {
        UUID userId = UUID.fromString(authentication.getName());
        List<Notification> noti = notificationService.getNoti(userId);
        List<NotificationDTO> dtos = noti.stream().map(app->{
            return new NotificationDTO(
                    app.isRead(),
                    app.getSentDate(),
                    app.getMessage(),
                    app.getTitle(),
                    app.getId()
            );
        }).toList();

        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/{notiId}")
    public ResponseEntity<NotificationDTO> readNoti(@PathVariable Long notiId,
                                                 Authentication authentication)
    {
        UUID userId = UUID.fromString(authentication.getName());
        Notification noti = notificationService.readNoti(userId, notiId);

        NotificationDTO dto = new NotificationDTO(
                noti.isRead(),
                noti.getSentDate(),
                noti.getMessage(),
                noti.getTitle(),
                noti.getId()
        );
        return ResponseEntity.ok(dto);
    }

}
