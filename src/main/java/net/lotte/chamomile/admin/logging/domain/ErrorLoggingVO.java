package net.lotte.chamomile.admin.logging.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ErrorLoggingVO {
    private Long errorLoggingId;

    private String clientId;

    private String clientIp;

    private String requestUrl;

    private String requestId;

    private String sessionId;

    private String exceptionOccurLine;

    private String exceptionMethodCause;

    private String exceptionMethodInfo;

    private String exceptionLogMsg;

    private String logDate;

}
