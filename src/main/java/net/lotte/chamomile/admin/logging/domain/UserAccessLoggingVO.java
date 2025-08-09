package net.lotte.chamomile.admin.logging.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserAccessLoggingVO {
    private Long userAccessLoggingId;

    private String clientIp;

    private String requestUrl;

    private String requestId;

    private String sessionId;

    private String loginUserID;

    private String userAccessActionType;

    @Setter
    private String logDate;

    private LocalDateTime orgLogDate;
}
