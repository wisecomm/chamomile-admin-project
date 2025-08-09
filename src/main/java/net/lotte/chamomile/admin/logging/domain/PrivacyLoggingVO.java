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
public class PrivacyLoggingVO {
    private Long privacyLoggingId;

    private String clientId;

    private String clientIp;

    private String requestUrl;

    private String requestId;

    private String sessionId;

    private Long privacyUserCount;

    private String privacyActionType;

    private String privacyFieldList;

    private String privacyUserIdList;

    @Setter
    private String logDate;

    private LocalDateTime orgLogDate;
}

