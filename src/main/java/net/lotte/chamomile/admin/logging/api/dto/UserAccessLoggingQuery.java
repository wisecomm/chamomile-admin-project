package net.lotte.chamomile.admin.logging.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccessLoggingQuery {

    private String searchClientIp;

    private String searchRequestUrl;

    private String searchRequestId;

    private String searchSessionId;

    private String searchLoginUserId;

    private String searchUserAccessActionType;

    private String searchStartLogDate;

    private String searchEndLogDate;
}
