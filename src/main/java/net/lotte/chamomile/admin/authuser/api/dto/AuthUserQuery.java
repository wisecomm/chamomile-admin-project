package net.lotte.chamomile.admin.authuser.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserQuery {
    private String userId;
    private String showEmptyTreeYn;
}
