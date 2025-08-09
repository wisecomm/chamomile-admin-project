package net.lotte.chamomile.admin.user.api.dto;

import lombok.Data;

@Data
public class PasswordChangeRequest {
    private String verifyCode;
    private String userId;
    private String newPassword;
}

