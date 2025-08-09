package net.lotte.chamomile.admin.logging.domain;

import org.apache.ibatis.annotations.Mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.lotte.chamomile.admin.logging.api.dto.ErrorLoggingQuery;
import net.lotte.chamomile.admin.logging.api.dto.FileStateLoggingQuery;
import net.lotte.chamomile.admin.logging.api.dto.PrivacyLoggingQuery;
import net.lotte.chamomile.admin.logging.api.dto.UserAccessLoggingQuery;

@Mapper
public interface LoggingMapper {

    Page<ErrorLoggingVO> findErrorLoggingListData(ErrorLoggingQuery query, Pageable pageable);

    Page<FileStateLoggingVO> findFileStateLoggingListData(FileStateLoggingQuery query, Pageable pageable);


    Page<PrivacyLoggingVO> findPrivacyLoggingListData(PrivacyLoggingQuery query, Pageable pageable);


    Page<UserAccessLoggingVO> findUserAccessLoggingListData(UserAccessLoggingQuery query, Pageable pageable);

}
