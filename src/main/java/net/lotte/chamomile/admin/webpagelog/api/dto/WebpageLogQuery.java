package net.lotte.chamomile.admin.webpagelog.api.dto;

import lombok.Data;

@Data
public class WebpageLogQuery {
    private String searchWebpageLogId;
    private String searchUrl;
    private String searchSysInsertUserId;
    private String searchStartLogDate;
    private String searchEndLogDate;
}
