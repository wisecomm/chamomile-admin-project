package net.lotte.chamomile.admin.groupuser.api;

import java.io.IOException;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import net.lotte.chamomile.admin.groupuser.api.dto.GroupUserResponse;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;

/**
 * <pre>
 * 그룹 유저 API 스웨거 문서.
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
@Tag(name = "캐모마일 어드민 그룹 유저 매핑 API")
public interface GroupUserControllerDoc {
    @Operation(summary = "그룹 유저 매핑 상세 요청", description = "그룹 유저 매핑의 상세를 조회 합니다.")
    ChamomileResponse<GroupUserResponse> getGroupUser(String groupId);
    @Operation(summary = "그룹 유저 매핑 수정 요청", description = "그룹 유저 매핑을 수정 합니다.")
    ChamomileResponse<Void> updateGroupUser(String groupId, List<String> userIds);
    @Operation(summary = "그룹 유저 매핑 엑셀 업로드 요청", description = "그룹 유저 매핑을 엑셀로 업로드 합니다.")
    ChamomileResponse<Void> excelUpload(MultipartFile file);
    @Operation(summary = "그룹 유저 매핑 엑셀 다운로드 요청", description = "그룹 유저 매핑을 엑셀로 다운로드 합니다.")
    ResponseEntity<byte[]> excelDownload(Pageable pageable) throws IOException;
    @Operation(summary = "그룹 유저 매핑 엑셀 샘플 요청", description = "그룹 유저 매핑 샘플을 엑셀로 다운로드 합니다.")
    ResponseEntity<byte[]> excelSample() throws IOException;

}
