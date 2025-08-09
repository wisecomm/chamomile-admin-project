package net.lotte.chamomile.admin.resource.api;

import java.io.IOException;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import net.lotte.chamomile.admin.resource.api.dto.ResourceQuery;
import net.lotte.chamomile.admin.resource.domain.ResourceVO;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;

/**
 * <pre>
 * 리소스 관련 컨트롤러 스웨거 문서.
 * </pre>
 *
 * @author MoonHKLee
 * @version 3.0
 * @Modification
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-09-15     MoonHKLee            최초 생성
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 * @see ResourceController
 * @since 2023-09-15
 */
@Tag(name = "캐모마일 어드민 ResourceAPI")
public interface ResourceControllerDoc {
    @Operation(summary = "리소스 목록 요청", description = "리소스의 목록을 조회합니다.")
    ChamomileResponse<Page<ResourceVO>> getResourceList(ResourceQuery request, Pageable pageable);

    @Operation(summary = "리소스 상세 요청", description = "리소스의 상세를 조회합니다.")
    ChamomileResponse<ResourceVO> getResource(String resourceId);

    @Operation(summary = "리소스 체크 요청", description = "리소스의 ID를 체크합니다.")
    ChamomileResponse<Boolean> getResourceCheck(String resourceId);

    @Operation(summary = "리소스 생성 요청", description = "리소스를 생성합니다.")
    ChamomileResponse<Void> createResource(ResourceVO request);

    @Operation(summary = "리소스 수정 요청", description = "리소스를 수정합니다.")
    ChamomileResponse<Void> updateResource(ResourceVO request);

    @Operation(summary = "리소스 삭제 요청", description = "리소스 목록을 삭제합니다.")
    ChamomileResponse<Void> deleteResource(List<ResourceVO> request);
    @Operation(summary = "리소스 엑셀 업로드 요청", description = "리소스 목록을 엑셀로 업로드 합니다.")
    ChamomileResponse<Void> excelUpload(MultipartFile file);
    @Operation(summary = "리소스 엑셀 다운로드 요청", description = "리소스 목록을 엑셀로 다운로드 합니다.")
    ResponseEntity<byte[]> excelDownload(ResourceQuery request, Pageable pageable) throws IOException;
    @Operation(summary = "리소스 엑셀 샘플 요청", description = "리소스 목록 샘플을 엑셀로 다운로드 합니다.")
    ResponseEntity<byte[]> excelSample() throws IOException;
}
