package net.lotte.chamomile.admin.user.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class VerificationToken {
    private String email;
    private LocalDateTime expiryDate;
    private String userId;
}
