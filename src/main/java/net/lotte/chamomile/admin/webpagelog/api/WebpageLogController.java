package net.lotte.chamomile.admin.webpagelog.api;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.lotte.chamomile.admin.webpagelog.api.dto.WebpageLogCommand;
import net.lotte.chamomile.admin.webpagelog.api.dto.WebpageLogQuery;
import net.lotte.chamomile.admin.webpagelog.domain.WebpageLogVO;
import net.lotte.chamomile.admin.webpagelog.service.WebpageLogService;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;

@RestController
@RequestMapping(path = "/chmm/webpage-log")
@RequiredArgsConstructor
public class WebpageLogController implements WebpageLogControllerDoc {

    private final WebpageLogService webpageLogService;

    @GetMapping("/list")
    public ChamomileResponse<Page<WebpageLogVO>> getWebpageLog(WebpageLogQuery request, Pageable pageable) {
        Page<WebpageLogVO> result = webpageLogService.getWebpageLogList(request, pageable);
        return new ChamomileResponse<>(result);
    }

    @PostMapping("/create")
    public ChamomileResponse<Void> createWebpageLog(@RequestBody WebpageLogCommand request) {
        webpageLogService.createLog(request.toEntity());
        return new ChamomileResponse<>();
    }
}
