package net.lotte.chamomile.admin.loggingconfig.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;

import net.lotte.chamomile.module.logging.config.AppenderInfoVO;
import net.lotte.chamomile.module.logging.config.LoggerInfoVO;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;

@Tag(name = "로그 설정 API")
public interface LogConfigApiControllerDoc {
    @Operation(summary = "로거 목록 요청", description = "로거 목록을 반환합니다.")
    ChamomileResponse<Page<LoggerInfoVO>> getLoggerList(Pageable pageable);

    @Operation(summary = "어펜더 목록 요청", description = "어펜더 목록을 반환합니다.")
    ChamomileResponse<Page<AppenderInfoVO>> getAppenderList(Pageable pageable);


    @Operation(summary = "로거 레벨 수정 요청", description = "로거 레벨 수정 요청.")
    ChamomileResponse<Void> updateLoggerLevel(LoggerInfoVO loggerInfoVO);


    @Operation(summary = "로그 파일 설정 보기 요청", description = "로그 설정 파일의 내용을 보여줍니다.")
    ChamomileResponse<String> getConfigDetail();

    @Operation(summary = "로그 파일 수정 요청", description = "수정된 로그 설정 파일을 업데이트 합니다.")
    ChamomileResponse<Void> updateConfig(@RequestBody String document);
}
