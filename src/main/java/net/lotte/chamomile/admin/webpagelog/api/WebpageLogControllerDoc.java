package net.lotte.chamomile.admin.webpagelog.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.lotte.chamomile.admin.webpagelog.api.dto.WebpageLogCommand;
import net.lotte.chamomile.admin.webpagelog.api.dto.WebpageLogQuery;
import net.lotte.chamomile.admin.webpagelog.domain.WebpageLogVO;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;

@Tag(name = "캐모마일 Webpage 로그 적재 API")
public interface WebpageLogControllerDoc {
    @Operation(summary = "웹페이지 로그 목록 호출 API", description = "웹페이지 로그의 목록을 호출합니다.")
    ChamomileResponse<Page<WebpageLogVO>> getWebpageLog(WebpageLogQuery request, Pageable pageable);

    @Operation(summary = "웹페이지 로그 생성 API", description = "웹페이지 로그를 생성합니다.")
    ChamomileResponse<Void> createWebpageLog(WebpageLogCommand request);
}
