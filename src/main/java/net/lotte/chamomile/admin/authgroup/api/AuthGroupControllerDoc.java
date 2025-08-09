package net.lotte.chamomile.admin.authgroup.api;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.multipart.MultipartFile;

import net.lotte.chamomile.admin.authgroup.api.AuthGroupController;
import net.lotte.chamomile.admin.authgroup.api.dto.AuthGroupQuery;
import net.lotte.chamomile.admin.authtree.RoleHierarchyTree;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;

/**
 * <pre>
 * 권한 그룹 스웨거.
 * </pre>
 *
 * @author MoonHKLee
 * @since 2023-11-01
 * @version 3.0
 * @see AuthGroupController
 * @Modification
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-11-01     MoonHKLee            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@Tag(name = "캐모마일 권한 그룹 API")
public interface AuthGroupControllerDoc {
    @Operation(summary = "그룹 권한 목록 요청", description = "그룹의 권한 목록을 조회 합니다.")
    ChamomileResponse<RoleHierarchyTree> getAuthGroupDetail(AuthGroupQuery reuqest);
    @Operation(summary = "그룹 권한 수정 요청", description = "그룹의 권한을 수정 합니다.")
    ChamomileResponse<Void> updateAuthGroup(String groupId, List<String> roleIds);
    @Operation(summary = "그룹 권한 엑셀 업로드 요청", description = "그룹의 권한을 엑셀로 업로드 합니다.")
    ChamomileResponse<Void> excelUpload(MultipartFile file);
}
