package net.lotte.chamomile.admin.authuser.api;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.multipart.MultipartFile;

import net.lotte.chamomile.admin.authtree.RoleHierarchyTree;
import net.lotte.chamomile.admin.authuser.api.dto.AuthUserQuery;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;

/**
 * <pre>
 * 유저 권한 스웨거 문서.
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
@Tag(name = "캐모마일 사용자 권한 API")
public interface AuthUserControllerDoc {
    @Operation(summary = "사용자 권한 목록 요청", description = "사용자의 권한 목록을 요청 합니다.")
    ChamomileResponse<RoleHierarchyTree> getAuthUser(AuthUserQuery request);
    @Operation(summary = "사용자 권한 수정 요청", description = "사용자의 권한을 수정 합니다.")
    ChamomileResponse<Void> updateAuthUser(String userId, List<String> roleIds);
    @Operation(summary = "사용자 권한 엑셀 업로드 요청", description = "사용자의 권한을 엑셀로 업로드 합니다.")
    ChamomileResponse<Void> excelUpload(MultipartFile file);
}
