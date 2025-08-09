package net.lotte.chamomile.admin.logging.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.TimeZone;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.lotte.chamomile.admin.logging.api.dto.ErrorLoggingQuery;
import net.lotte.chamomile.admin.logging.api.dto.FileStateLoggingQuery;
import net.lotte.chamomile.admin.logging.api.dto.PrivacyLoggingQuery;
import net.lotte.chamomile.admin.logging.api.dto.UserAccessLoggingQuery;
import net.lotte.chamomile.admin.logging.domain.ErrorLoggingVO;
import net.lotte.chamomile.admin.logging.domain.FileStateLoggingVO;
import net.lotte.chamomile.admin.logging.domain.LoggingMapper;
import net.lotte.chamomile.admin.logging.domain.PrivacyLoggingVO;
import net.lotte.chamomile.admin.logging.domain.UserAccessLoggingVO;
import net.lotte.chamomile.module.util.string.StringUtil;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LoggingServiceImpl implements LoggingService {
    private final LoggingMapper loggingMapper;
    private final DateTimeFormatter yyyyMMddFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public Page<ErrorLoggingVO> findErrorLoggingListData(ErrorLoggingQuery query, Pageable pageable) {
        if (StringUtil.isNotBlank(query.getSearchStartLogDate())) {
            LocalDate searchStartLogDate = LocalDate.parse(query.getSearchStartLogDate(), yyyyMMddFormatter);
            query.setSearchStartLogDate(searchStartLogDate.format(dtFormatter) + " 00:00:00");
        }
        if (StringUtil.isNotBlank(query.getSearchEndLogDate())) {
            LocalDate searchEndLogDate = LocalDate.parse(query.getSearchEndLogDate(), yyyyMMddFormatter);
            query.setSearchEndLogDate(searchEndLogDate.plusDays(1).format(dtFormatter) + " 00:00:00");
        }
        return loggingMapper.findErrorLoggingListData(query, pageable);
    }

    @Override
    public Page<FileStateLoggingVO> findFileStateLoggingListData(FileStateLoggingQuery query, Pageable pageable) {
        if (StringUtil.isNotBlank(query.getSearchStartLogDate())) {
            LocalDate searchStartLogDate = LocalDate.parse(query.getSearchStartLogDate(), yyyyMMddFormatter);
            query.setSearchStartLogDate(searchStartLogDate.format(dtFormatter) + " 00:00:00");
        }
        if (StringUtil.isNotBlank(query.getSearchEndLogDate())) {
            LocalDate searchEndLogDate = LocalDate.parse(query.getSearchEndLogDate(), yyyyMMddFormatter);
            query.setSearchEndLogDate(searchEndLogDate.plusDays(1).format(dtFormatter) + " 00:00:00");
        }
        return loggingMapper.findFileStateLoggingListData(query, pageable);
    }


    @Override
    public Page<PrivacyLoggingVO> findPrivacyLoggingListData(PrivacyLoggingQuery query, Pageable pageable) {
        if (StringUtil.isNotBlank(query.getSearchStartLogDate())) {
            LocalDate searchStartLogDate = LocalDate.parse(query.getSearchStartLogDate(), yyyyMMddFormatter);
            query.setSearchStartLogDate(searchStartLogDate.format(dtFormatter) + " 00:00:00");
        }
        if (StringUtil.isNotBlank(query.getSearchEndLogDate())) {
            LocalDate searchEndLogDate = LocalDate.parse(query.getSearchEndLogDate(), yyyyMMddFormatter);
            query.setSearchEndLogDate(searchEndLogDate.plusDays(1).format(dtFormatter) + " 00:00:00");
        }

        Page<PrivacyLoggingVO> result = loggingMapper.findPrivacyLoggingListData(query, pageable);
        result.getContent().forEach(privacyLoggingVO -> {
            LocalDateTime logDate = (privacyLoggingVO.getOrgLogDate() != null) ?
                    privacyLoggingVO.getOrgLogDate().plus(TimeZone.getDefault().getRawOffset(), ChronoUnit.MILLIS) :
                    LocalDateTime.now();
            privacyLoggingVO.setLogDate(logDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        });

        return result;
    }


    @Override
    public Page<UserAccessLoggingVO> findUserAccessLoggingListData(UserAccessLoggingQuery query, Pageable pageable) {
        if (StringUtil.isNotBlank(query.getSearchStartLogDate())) {
            LocalDate searchStartLogDate = LocalDate.parse(query.getSearchStartLogDate(), yyyyMMddFormatter);
            query.setSearchStartLogDate(searchStartLogDate.format(dtFormatter) + " 00:00:00");
        }
        if (StringUtil.isNotBlank(query.getSearchEndLogDate())) {
            LocalDate searchEndLogDate = LocalDate.parse(query.getSearchEndLogDate(), yyyyMMddFormatter);
            query.setSearchEndLogDate(searchEndLogDate.plusDays(1).format(dtFormatter) + " 00:00:00");
        }

        Page<UserAccessLoggingVO> result = loggingMapper.findUserAccessLoggingListData(query, pageable);
        result.getContent().forEach(accessLoggingVO -> {
            LocalDateTime logDate = (accessLoggingVO.getOrgLogDate() != null) ?
                    accessLoggingVO.getOrgLogDate().plus(TimeZone.getDefault().getRawOffset(), ChronoUnit.MILLIS) :
                    LocalDateTime.now();
            accessLoggingVO.setLogDate(logDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        });

        return result;
    }

}
