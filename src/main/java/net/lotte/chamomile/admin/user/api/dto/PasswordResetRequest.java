package net.lotte.chamomile.admin.user.api.dto;

import java.time.LocalDateTime;

import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;

@Data
public class PasswordResetRequest {
    private String email;
    private String id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime requestTime;
}

