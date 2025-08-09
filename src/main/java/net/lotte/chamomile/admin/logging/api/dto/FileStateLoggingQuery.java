package net.lotte.chamomile.admin.logging.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileStateLoggingQuery {
    private String searchClientId;

    private String searchClientIp;

    private String searchRequestUrl;

    private String searchRequestId;

    private String searchSessionId;

    private String searchFileActionType;

    private String searchOriginalFileName;

    private String searchUploadedFileName;

    private boolean searchIsPrivacyFile;

    private String searchFileSize;

    private String searchFilePath;

    private String searchFileHash;

    private String searchStartLogDate;

    private String searchEndLogDate;
}
