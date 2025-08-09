package net.lotte.chamomile.admin.logging.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

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
import net.lotte.chamomile.module.web.variable.ChamomileResponse;

@Tag(name = "캐모마일 로그 내용 읽기 API")
public interface LoggingApiControllerDoc {
    @Operation(summary = "개인정보 관리 이력 로깅", description = "개인정보 접근,수정,삭제 내역 로깅을 가져옵니다")
    ChamomileResponse<Page<PrivacyLoggingVO>> getPrivacyLogging(Pageable pageable, PrivacyLoggingQuery query);

    @Operation(summary = "에러 로깅", description = "에러 로깅 정보를 가져옵니다")
    ChamomileResponse<Page<ErrorLoggingVO>> getErrorLoggingListData(Pageable pageable, ErrorLoggingQuery query);

    @Operation(summary = "파일 로깅", description = "파일 로깅 정보를 가져옵니다")
    ChamomileResponse<Page<FileStateLoggingVO>> getFileLoggingListData(Pageable pageable, FileStateLoggingQuery query);

    @Operation(summary = "사용자 접근 로깅", description = "사용자 접근 로깅 정보를 가져옵니다")
    ChamomileResponse<Page<UserAccessLoggingVO>> getUserAccessLoggingListData(Pageable pageable, UserAccessLoggingQuery query);

}
