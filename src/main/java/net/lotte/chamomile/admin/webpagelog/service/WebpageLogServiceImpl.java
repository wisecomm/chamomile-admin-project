package net.lotte.chamomile.admin.webpagelog.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.lotte.chamomile.admin.webpagelog.api.dto.WebpageLogQuery;
import net.lotte.chamomile.admin.webpagelog.domain.WebpageLogMapper;
import net.lotte.chamomile.admin.webpagelog.domain.WebpageLogVO;
import net.lotte.chamomile.module.util.string.StringUtil;

@Service
@RequiredArgsConstructor
public class WebpageLogServiceImpl implements WebpageLogService {
    private final WebpageLogMapper webpageLogMapper;

    private final DateTimeFormatter yyyyMMddFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void createLog(WebpageLogVO command) {
        command.onCreate();
        webpageLogMapper.insertWebpageLog(command);
    }

    @Override
    public Page<WebpageLogVO> getWebpageLogList(WebpageLogQuery query, Pageable pageable) {
        if (StringUtil.isNotBlank(query.getSearchStartLogDate())) {
            LocalDate searchStartDtm = LocalDate.parse(query.getSearchStartLogDate(), yyyyMMddFormatter);
            query.setSearchStartLogDate(searchStartDtm.format(dtFormatter) + " 00:00:00");
        }
        if (StringUtil.isNotBlank(query.getSearchEndLogDate())) {
            LocalDate searchEndDtm = LocalDate.parse(query.getSearchEndLogDate(), yyyyMMddFormatter);
            query.setSearchEndLogDate(searchEndDtm.plusDays(1).format(dtFormatter) + " 00:00:00");
        }
        return webpageLogMapper.findWebpageLogList(query, pageable);
    }
}
