package net.lotte.chamomile.admin.logging.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrivacyLoggingQuery {

    private String searchClientId;

    private String searchClientIp;

    private String searchRequestUrl;

    private String searchRequestId;

    private String searchSessionId;

    private String searchPrivacyActionType;

    private String searchPrivacyFieldList;

    private String searchPrivacyUserIdList;

    private String searchStartLogDate;

    private String searchEndLogDate;

}
