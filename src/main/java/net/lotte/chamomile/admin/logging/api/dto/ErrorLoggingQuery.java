package net.lotte.chamomile.admin.logging.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorLoggingQuery {

    private String searchClientId;

    private String searchClientIp;

    private String searchRequestUrl;

    private String searchRequestId;

    private String searchSessionId;

    private String searchExceptionMethodCause;

    private String searchExceptionMethodInfo;

    private String searchExceptionLogMsg;

    private String searchStartLogDate;

    private String searchEndLogDate;

}
