package net.lotte.chamomile.admin.logging.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class FileStateLoggingVO {
    private Long fileStateLoggingId;

    private String clientId;

    private String clientIp;

    private String requestUrl;

    private String requestId;

    private String sessionId;

    private String fileActionType;

    private String isPrivacyFile;

    private String originalFileName;

    private String uploadedFileName;

    private String fileSize;

    private String filePath;

    private String fileHash;

    private String logDate;

}
