package net.lotte.chamomile.admin.authresource.api;

import java.io.IOException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import net.lotte.chamomile.admin.authresource.api.dto.AuthResourceCommand;
import net.lotte.chamomile.admin.authresource.api.dto.AuthResourceQuery;
import net.lotte.chamomile.admin.authresource.api.dto.AuthResourceResponse;
import net.lotte.chamomile.admin.authresource.api.dto.ResourceRoleResponse;
import net.lotte.chamomile.admin.resource.api.dto.ResourceQuery;
import net.lotte.chamomile.admin.resource.domain.ResourceVO;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;

/**
 * <pre>
 * 캐모마일 어드민 권한 리소스 API 스웨거.
 * </pre>
 *
 * @author MoonHKLee
 * @since 2023-10-12
 * @version 3.0
 * @Modification
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-10-12     MoonHKLee            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@Tag(name = "캐모마일 어드민 권한 리소스 API")
public interface AuthResourceControllerDoc {
    @Operation(summary = "리소스 권한 목록 요청", description = "리소스 권한의 목록을 조회 합니다.")
    ChamomileResponse<Page<ResourceRoleResponse>> getAuthResourceList(
            @Parameter(example = "{\n" +
                    "    \"searchResourceId\": \"RESOURCE_ADMIN_ID\",\n" +
                    "    \"searchResourceName\": \"어드민 리소스\",\n" +
                    "    \"searchUseYn\": \"1\"\n" +
                    "}")
            ResourceQuery request,
            @Parameter(example = "{\n" +
                    "    \"page\": \"0\",\n" +
                    "    \"size\": \"10\",\n" +
                    "    \"sort\": \"resourceId,desc\"\n" +
                    "}")
            Pageable pageable
    );

    @Operation(summary = "리소스 권한 상세 요청", description = "리소스 권한의 상세를 조회 합니다.")
    ChamomileResponse<AuthResourceResponse> getAuthResource(
            @Parameter(example = "{\n" +
                    "    \"resourceId\": \"RESOURCE_ADMIN_ID\"\n" +
                    "}")
            AuthResourceQuery request
    );

    @Operation(summary = "리소스 권한 수정 요청", description = "리소스 권한을 수정 합니다.")
    ChamomileResponse<Void> updateAuthResource(
            @RequestBody(content = @Content(examples = @ExampleObject(value = "{\n" +
                    "    \"resourceId\": \"RESOURCE_ADMIN_ID\",\n" +
                    "    \"rightValue\": [\n" +
                    "        {\n" +
                    "            \"fixed\": \"false\",\n" +
                    "            \"origin\": \"left\",\n" +
                    "            \"text\": \"기본 리소스\",\n" +
                    "            \"val\": \"ROLE_DEFAULT_ID\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"fixed\": \"false\",\n" +
                    "            \"origin\": \"left\",\n" +
                    "            \"text\": \"어드민 리소스\",\n" +
                    "            \"val\": \"ROLE_ADMIN_ID\"\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}")))
            AuthResourceCommand request
    );

    @Operation(summary = "권한 리소스 엑셀 다운로드 요청", description = "권한 리소스를 엑셀로 다운로드 합니다.")
    ResponseEntity<byte[]> excelDownload(ResourceQuery request, Pageable pageable) throws IOException;
}
