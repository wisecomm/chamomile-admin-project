package net.lotte.chamomile.admin.webpagelog.domain;

import org.apache.ibatis.annotations.Mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.lotte.chamomile.admin.webpagelog.api.dto.WebpageLogQuery;

@Mapper
public interface WebpageLogMapper {
    Page<WebpageLogVO> findWebpageLogList(WebpageLogQuery query, Pageable pageable);
    void insertWebpageLog(WebpageLogVO command);
}
