package net.lotte.chamomile.admin.user.api.dto;

import lombok.Data;

@Data
public class ConfirmUserRequest {
    private String userId;
    private String password;
}

