package net.lotte.chamomile.admin.webpagelog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.lotte.chamomile.admin.webpagelog.api.dto.WebpageLogQuery;
import net.lotte.chamomile.admin.webpagelog.domain.WebpageLogVO;

public interface WebpageLogService {
    void createLog(WebpageLogVO command);
    Page<WebpageLogVO> getWebpageLogList(WebpageLogQuery request, Pageable pageable);
}
