package com.example.demo.dto.request;

import org.hibernate.annotations.Nationalized;

import java.time.LocalDateTime;

public class NotificationDTO {
    private long id;
    @Nationalized
    private String title = "";
    @Nationalized
    private String message;

    private LocalDateTime sentDate = LocalDateTime.now();

    public NotificationDTO(boolean isRead, LocalDateTime sentDate, String message, String title, long id) {
        this.isRead = isRead;
        this.sentDate = sentDate;
        this.message = message;
        this.title = title;
        this.id = id;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public LocalDateTime getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDateTime sentDate) {
        this.sentDate = sentDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private boolean isRead = false;
}
