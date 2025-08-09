package net.lotte.chamomile.admin.grouphierarchy.api;

import java.io.IOException;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import net.lotte.chamomile.admin.grouphierarchy.domain.GroupHierarchyVO;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;

/**
 * <pre>
 * 그룹 상하관계 API 스웨거 문서.
 * </pre>
 *
 * @author MoonHKLee
 * @since 2023-11-09
 * @version 3.0
 * @Modification
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-11-09     MoonHKLee            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@Tag(name = "캐모마일 그룹 상하 관계 API")
public interface GroupHierarchyControllerDoc {
    @Operation(summary = "그룹 상하 관계 목록 요청", description = "그룹 상하 관계의 목록을 조회 합니다.")
    ChamomileResponse<Page<GroupHierarchyVO>> getGroupHierarchyList(Pageable pageable);
    @Operation(summary = "그룹 상하 관계 상세 요청", description = "그룹 상하 관계의 상세를 조회 합니다.")
    ChamomileResponse<List<GroupHierarchyVO>> getGroupHierarchy(String groupId);
    @Operation(summary = "그룹 상하 관계 생성 요청", description = "그룹 상하 관계를 생성 합니다.")
    ChamomileResponse<Void> createGroupHierarchy(GroupHierarchyVO request);
    @Operation(summary = "그룹 상하 관계 삭제 요청", description = "그룹 상하 관계를 삭제 합니다.")
    ChamomileResponse<Void> deleteGroupHierarchy(List<GroupHierarchyVO> request);
    @Operation(summary = "그룹 상하 관계 엑셀 업로드 요청", description = "그룹 상하 관계를 엑셀로 업로드 합니다.")
    ChamomileResponse<Void> excelUpload(MultipartFile file);
    @Operation(summary = "그룹 상하 관계 엑셀 다운로드 요청", description = "그룹 상하 관계를 엑셀로 다운로드 합니다.")
    ResponseEntity<byte[]> excelDownload(Pageable pageable) throws IOException;
    @Operation(summary = "그룹 상하 관계 엑셀 샘플 요청", description = "그룹 상하 관계 샘플을 엑셀로 다운로드 합니다.")
    ResponseEntity<byte[]> excelSample() throws IOException;
}
