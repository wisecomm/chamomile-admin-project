package net.lotte.chamomile.admin.logging.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.lotte.chamomile.admin.logging.api.dto.ErrorLoggingQuery;
import net.lotte.chamomile.admin.logging.api.dto.FileStateLoggingQuery;
import net.lotte.chamomile.admin.logging.api.dto.PrivacyLoggingQuery;
import net.lotte.chamomile.admin.logging.api.dto.UserAccessLoggingQuery;
import net.lotte.chamomile.admin.logging.domain.ErrorLoggingVO;
import net.lotte.chamomile.admin.logging.domain.FileStateLoggingVO;
import net.lotte.chamomile.admin.logging.domain.PrivacyLoggingVO;
import net.lotte.chamomile.admin.logging.domain.UserAccessLoggingVO;

public interface LoggingService {

    Page<ErrorLoggingVO> findErrorLoggingListData(ErrorLoggingQuery query, Pageable pageable);

    Page<FileStateLoggingVO> findFileStateLoggingListData(FileStateLoggingQuery query, Pageable pageable);


    Page<PrivacyLoggingVO> findPrivacyLoggingListData(PrivacyLoggingQuery query, Pageable pageable);


    Page<UserAccessLoggingVO> findUserAccessLoggingListData(UserAccessLoggingQuery search, Pageable pageable);

}
