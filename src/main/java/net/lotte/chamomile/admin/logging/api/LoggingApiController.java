package net.lotte.chamomile.admin.logging.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.lotte.chamomile.admin.logging.api.dto.ErrorLoggingQuery;
import net.lotte.chamomile.admin.logging.api.dto.FileStateLoggingQuery;
import net.lotte.chamomile.admin.logging.api.dto.PrivacyLoggingQuery;
import net.lotte.chamomile.admin.logging.api.dto.UserAccessLoggingQuery;
import net.lotte.chamomile.admin.logging.domain.ErrorLoggingVO;
import net.lotte.chamomile.admin.logging.domain.FileStateLoggingVO;
import net.lotte.chamomile.admin.logging.domain.PrivacyLoggingVO;
import net.lotte.chamomile.admin.logging.domain.UserAccessLoggingVO;
import net.lotte.chamomile.admin.logging.service.LoggingService;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/chmm/logging")
public class LoggingApiController implements LoggingApiControllerDoc {

    private final LoggingService loggingService;

    @GetMapping("/privacy")
    public ChamomileResponse<Page<PrivacyLoggingVO>> getPrivacyLogging(Pageable pageable, PrivacyLoggingQuery privacyLoggingQuery) {
        Page<PrivacyLoggingVO> privacyLoggings = loggingService.findPrivacyLoggingListData(privacyLoggingQuery, pageable);
        return new ChamomileResponse<>(privacyLoggings);
    }


    @GetMapping("/error")
    public ChamomileResponse<Page<ErrorLoggingVO>> getErrorLoggingListData(Pageable pageable, ErrorLoggingQuery query) {
        Page<ErrorLoggingVO> errorLoggings = loggingService.findErrorLoggingListData(query, pageable);
        return new ChamomileResponse<>(errorLoggings);
    }


    @GetMapping("/file-state")
    public ChamomileResponse<Page<FileStateLoggingVO>> getFileLoggingListData(Pageable pageable, FileStateLoggingQuery query) {
        Page<FileStateLoggingVO> fileLoggings = loggingService.findFileStateLoggingListData(query, pageable);
        return new ChamomileResponse<>(fileLoggings);
    }


    @GetMapping("/user-access")
    public ChamomileResponse<Page<UserAccessLoggingVO>> getUserAccessLoggingListData(Pageable pageable, UserAccessLoggingQuery query) {
        Page<UserAccessLoggingVO> userAccessLoggings = loggingService.findUserAccessLoggingListData(query, pageable);
        return new ChamomileResponse<>(userAccessLoggings);
    }

}
