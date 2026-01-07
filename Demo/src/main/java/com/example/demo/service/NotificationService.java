package com.example.demo.service;

import com.example.demo.model.Notification;
import com.example.demo.model.User;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    public NotificationService(NotificationRepository notificationRepository,
                               UserRepository userRepository)
    {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }
    public List<Notification> getNoti(UUID userId)
    {
        return notificationRepository.findByUserIdOrderBySentDateDesc(userId);
    }
    public Notification readNoti(UUID userId, long notiId)
    {
        Notification noti = notificationRepository.findByIdAndUserId(notiId, userId)
                .orElseThrow(() ->new RuntimeException("Không tìm thấy thông báo"));
        if(!noti.isRead())
        {
            noti.setRead(true);
            notificationRepository.save(noti);
        }
        return noti;
    }

    public void sendNotification (User user, String title, String message)
    {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setRead(false);
        notificationRepository.save(notification);
    }
}
