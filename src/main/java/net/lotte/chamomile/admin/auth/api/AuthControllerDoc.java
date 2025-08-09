package net.lotte.chamomile.admin.auth.api;

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
import net.lotte.chamomile.module.web.variable.ChamomileResponse;

/**
 * <pre>
 * 권한 관련 컨트롤러 스웨거 문서.
 * </pre>
 *
 * @author TaehoPark
 * @since 2023-09-26
 * @version 3.0
 * @see AuthController
 * @Modification
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-09-26     TaehoPark            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@Tag(name = "캐모마일 어드민 권한 AuthAPI")
public interface AuthControllerDoc {
    @Operation(summary = "권한 목록 요청", description = "권한의 목록을 조회합니다.")
    ChamomileResponse<Page<AuthVO>> getAuthList(AuthQuery request, Pageable pageable);
    @Operation(summary = "권한 생성 요청", description = "권한를 생성합니다.")
    ChamomileResponse<Void> createAuth(@RequestBody(
            content = @Content(
                    examples = @ExampleObject(value = "{\n" +
                            "    \"roleId\": \"test1\",\n" +
                            "    \"roleName\": \"테스트1\",\n" +
                            "    \"roleDesc\": \"데스트1\",\n" +
                            "    \"roleStartDt\": \"20231010\",\n" +
                            "    \"roleEndDt\": \"20241031\",\n" +
                            "    \"useYn\": \"1\"\n" +
                            "}")
            )
    ) AuthVO request) throws Exception;

    @Operation(summary = "권한 수정 요청", description = "권한를 수정합니다.")
    ChamomileResponse<Void> updateAuth(@RequestBody(
            content = @Content(
                    examples = @ExampleObject(value = "{\n" +
                            "    \"roleId\": \"test1\",\n" +
                            "    \"roleName\": \"테스트1 수정\",\n" +
                            "    \"roleDesc\": \"데스트\",\n" +
                            "    \"roleStartDt\": \"20231010\",\n" +
                            "    \"roleEndDt\": \"20241031\",\n" +
                            "    \"useYn\": \"1\"\n" +
                            "}")
            )
    ) AuthVO request) throws Exception;
    @Operation(summary = "권한 삭제 요청", description = "권한를 삭제합니다.")
    ChamomileResponse<Void> deleteAuth(@RequestBody(
            content = @Content(
                    examples = @ExampleObject(value = "[{\n" +
                            "    \"roleId\": \"test1\"\n" +
                            "}]")
            )
    ) List<AuthVO>  request) throws Exception;
    @Operation(summary = "권한 중복확인 ", description = "권한 중복확인을 합니다. ")
    ChamomileResponse<AuthVO> authCheckId(@RequestBody(
            content = @Content(
                    examples = @ExampleObject(value = "{\n" +
                            "    \"searchRoleId\": \"test1\"\n" +
                            "}")
            )
    ) AuthQuery request) throws Exception;

    @Operation(summary = "엑세파일 저장  ", description = "엑셀 파일을 저장합니다.")
    ResponseEntity<byte[]> excelDownload(AuthQuery request) throws Exception;

    @Operation(summary = "엑세템플릿 저장  ", description = "엑셀 템플릿을 저장합니다.")
    ResponseEntity<byte[]> excelSample() throws Exception;

    @Operation(summary = "엑셀 업로드   ", description = "엑셀을 업로드 합니다.")
    ChamomileResponse<Void> excelUpload(MultipartFile request) throws Exception;


}
