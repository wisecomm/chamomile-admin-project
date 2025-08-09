package net.lotte.chamomile.admin.message.api;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import net.lotte.chamomile.admin.message.api.dto.LanguageQuery;
import net.lotte.chamomile.admin.message.api.dto.LanguageUpdate;
import net.lotte.chamomile.admin.message.api.dto.MessageQuery;
import net.lotte.chamomile.admin.message.domain.LanguageVO;
import net.lotte.chamomile.admin.message.domain.MessageVO;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;

/**
 * <pre>
 * Admin 메시지 관련 REST API Swagger Doc.
 * </pre>
 *
 * @ClassName   : MessageControllerDoc.java
 * @Description : Admin 메시지 관련(CRUD 등) REST API Swagger Doc.
 * @author chaelynJang
 * @since 2023.10.05
 * @version 3.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023.10.05     chaelynJang            최초 생성
 *
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@Tag(name = "캐모마일 Message API")
public interface MessageControllerDoc {
    @Operation(summary = "메시지 목록 요청", description = "메시지 목록을 보여줍니다.")
    ChamomileResponse<Page<MessageVO>> getMessageList(MessageQuery request, Pageable pageable);

    @Operation(summary = "메시지 전체 목록 요청", description = "메시지 전체 목록을 보여줍니다.")
    ChamomileResponse<List<MessageVO>> getMessageAllList(MessageQuery request);

    @Operation(summary = "언어별 메시지 전체 목록 요청", description = "언어별 메시지 전체 목록을 보여줍니다.")
    ChamomileResponse<List<MessageVO>> getMessageAllListByLanguage(MessageQuery request);

    @Operation(summary = "메시지 상세 요청", description = "메시지 상세를 보여줍니다.")
    ChamomileResponse<List<MessageVO>> getMessageDetail(MessageQuery request);

    @Operation(summary = "메시지 생성 요청", description = "메시지를 생성합니다.")
    ChamomileResponse<Void> createMessage(List<MessageVO> request);

    @Operation(summary = "메시지 삭제 요청", description = "메시지를 삭제합니다.")
    ChamomileResponse<Void> deleteMessage(List<MessageVO> request);

    @Operation(summary = "메시지 수정 요청", description = "메시지를 수정합니다.")
    ChamomileResponse<Void> updateMessage(List<MessageVO> request);

    @Operation(summary = "언어코드 생성 요청", description = "언어코드를 생성합니다.")
    ChamomileResponse<Void> createLanguage(LanguageVO request);

    @Operation(summary = "언어코드 수정 요청", description = "언어코드를 수정합니다.")
    ChamomileResponse<Void> updateLanguage(List<LanguageUpdate> request);

    @Operation(summary = "언어코드 목록 요청", description = "언어코드 목록을 조회합니다.")
    ChamomileResponse<List<LanguageVO>> getLanguageList();

    @Operation(summary = "언어코드 상세 요청", description = "언어코드 상세를 조회합니다.")
    ChamomileResponse<List<LanguageVO>> getLanguageDetail(LanguageQuery request);

    @Operation(summary = "다국어 관리 메시지 엑셀 다운로드 요청", description = "다국어 관리 메시지 목록 엑셀을 다운로드합니다.")
    ResponseEntity<byte[]> exportMenuExcel(MessageQuery request) throws Exception;

    @Operation(summary = "다국어 관리 메시지 엑셀 템플릿 다운로드 요청", description = "다국어 관리 메시지 엑셀 템플릿을 다운로드합니다.")
    ResponseEntity<byte[]> exportMessageTemplateExcel() throws Exception;

    @Operation(summary = "다국어 관리 메시지 엑셀 업로드 요청", description = "다국어 관리 메시지 엑셀 업로드를 합니다.")
    ChamomileResponse<Void> excelUpload(MultipartFile file);

    @Operation(summary = "미등록 메시지 목록 요청", description = "미등록된 메시지 목록을 보여줍니다.")
    ChamomileResponse<Page<MessageVO>> getAlertMessageList(MessageQuery request, Pageable pageable);

    @Operation(summary = "언어를 삭제합니다.", description = "언어를 삭제합니다.")
    ChamomileResponse<Void> deleteLanguage(List<LanguageVO> request);
}
