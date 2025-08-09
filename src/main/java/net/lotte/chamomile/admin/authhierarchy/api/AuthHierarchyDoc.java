package net.lotte.chamomile.admin.authhierarchy.api;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import net.lotte.chamomile.admin.auth.api.AuthController;
import net.lotte.chamomile.admin.auth.api.dto.AuthQuery;
import net.lotte.chamomile.admin.auth.domain.AuthVO;
import net.lotte.chamomile.admin.authhierarchy.api.dto.AuthHierarchyQuery;
import net.lotte.chamomile.admin.authhierarchy.domain.AuthHierarchyVO;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;

/**
 * <pre>
 * 권한상하관계 관련 컨트롤러 스웨거 문서.
 * </pre>
 *
 * @author TaehoPark
 * @since 2023-10-05
 * @version 3.0
 * @see AuthController
 * @Modification
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-10-05     TaehoPark            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@Tag(name = "캐모마일 어드민 AuthHierarchyAPI")
public interface AuthHierarchyDoc {
    @Operation(summary = "권한상하관계 상세 요청", description = "권한상하관계 상세을 조회합니다.")
    ChamomileResponse<List<AuthHierarchyVO>> getAuthHierarchyDetail(AuthHierarchyQuery request);
    @Operation(summary = "권한상하관계 요청", description = "권한상하관계의 목록을 조회합니다.")
    ChamomileResponse<Page<AuthHierarchyVO>> getAuthHierarchyList(AuthHierarchyQuery request, Pageable pageable);
    @Operation(summary = "권한상하관계 생성 수정 요청", description = "권한상하관계를 생성, 수정합니다.")
    ChamomileResponse<Void> createAuthHierarchy(@RequestBody(
            content = @Content(
                    examples = @ExampleObject(value = "{\n" +
                            "    \"parentRoleId\": \"test5\",\n" +
                            "    \"childRoleId\": \"test4\"" +
                            "}")
            )
    ) AuthHierarchyVO request) throws Exception;

    @Operation(summary = "권한상하관계 삭제 요청", description = "권한상하관계를 삭제합니다.")
    ChamomileResponse<Void> deleteAuthHierarchy(@RequestBody(
            content = @Content(
                    examples = @ExampleObject(value = "[\n" +
                            "    {\n" +
                            "        \"parentRoleId\": \"test5\",\n" +
                            "        \"childRoleId\": \"test4\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "        \"parentRoleId\": \"test3\",\n" +
                            "        \"childRoleId\": \"test5\"\n" +
                            "    }\n" +
                            "]")
            )
    ) List<AuthHierarchyVO>  request) throws Exception;


    @Operation(summary = "권한리스트 조회 요청", description = "권한리스트 조회합니다.")
    ChamomileResponse<List<AuthVO>> getAuthList(AuthQuery authQuery);

    @Operation(summary = "엑세파일 저장  ", description = "엑셀 파일을 저장합니다.")
    ResponseEntity<byte[]> excelDownload(AuthHierarchyQuery request) throws Exception;

    @Operation(summary = "엑세템플릿 저장  ", description = "엑셀 템플릿을 저장합니다.")
    ResponseEntity<byte[]> excelSample() throws Exception;

    @Operation(summary = "엑셀 업로드   ", description = "엑셀을 업로드 합니다.")
    ChamomileResponse<Void> excelUpload(MultipartFile request) throws Exception;


}
