package net.lotte.chamomile.admin.user.api.dto;

import lombok.Data;

@Data
public class VerifyCodeRequest {
    private String email;
    private String verifyCode;
}
