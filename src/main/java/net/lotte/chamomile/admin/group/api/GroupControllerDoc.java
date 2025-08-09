package net.lotte.chamomile.admin.group.api;

import java.io.IOException;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import net.lotte.chamomile.admin.group.api.dto.GroupQuery;
import net.lotte.chamomile.admin.group.domain.GroupVO;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;
/**
 * <pre>
 * 그룹 API 스웨거 문서.
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

@Tag(name = "캐모마일 어드민 그룹 API")
public interface GroupControllerDoc {
    @Operation(summary = "그룹 목록 요청", description = "그룹의 목록을 조회 합니다.")
    ChamomileResponse<Page<GroupVO>> getGroupList(
            @Parameter(example = "{\n" +
                    "    \"searchGroupId\": \"group\",\n" +
                    "    \"searchGroupName\": \"그룹\",\n" +
                    "    \"searchUseYn\": \"1\"\n" +
                    "}")
            GroupQuery request,
            @Parameter(example = "{\n" +
                    "    \"page\": \"0\",\n" +
                    "    \"size\": \"10\",\n" +
                    "    \"sort\": \"groupId,desc\"\n" +
                    "}")
            Pageable pageable
    );
    @Operation(summary = "그룹 상세 요청", description = "그룹의 상세를 조회 합니다.")
    ChamomileResponse<GroupVO> getGroup(
            @Parameter(example = "group01")
            String groupId
    );

    @Operation(summary = "그룹 체크 요청", description = "그룹의 ID를 체크 합니다.")
    ChamomileResponse<Boolean> getGroupCheck(
            @Parameter(example = "group01")
            String groupId
    );

    @Operation(summary = "그룹 생성 요청", description = "그룹을 생성 합니다.")
    ChamomileResponse<Void> createGroup(
            @RequestBody(content = @Content(examples = @ExampleObject(value = "{\n" +
                    "    \"groupId\": \"group100\",\n" +
                    "    \"groupName\": \"그룹 100\",\n" +
                    "    \"groupDesc\": \"그룹 100 입니다.\",\n" +
                    "    \"useYn\": \"1\"\n" +
                    "}")))
            GroupVO request
    );
    @Operation(summary = "그룹 수정 요청", description = "그룹을 수정 합니다.")
    ChamomileResponse<Void> updateGroup(
            @RequestBody(content = @Content(examples = @ExampleObject(value = "{\n" +
                    "    \"groupId\": \"group01\",\n" +
                    "    \"groupName\": \"그룹 01 수정\",\n" +
                    "    \"groupDesc\": \"그룹 01 수정 설명.\",\n" +
                    "    \"useYn\": \"0\"\n" +
                    "}")))
            GroupVO request
    );
    @Operation(summary = "그룹 삭제 요청", description = "그룹을 삭제 합니다.")
    ChamomileResponse<Void> deleteGroup(
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
            List<GroupVO> request
    );
    @Operation(summary = "그룹 엑셀 업로드 요청", description = "그룹을 엑셀로 업로드 합니다.")
    ChamomileResponse<Void> excelUpload(MultipartFile file);

    @Operation(summary = "그룹 엑셀 다운로드 요청", description = "그룹을 엑셀로 다운로드 합니다.")
    ResponseEntity<byte[]> excelDownload(GroupQuery request, Pageable pageable) throws IOException;

    @Operation(summary = "그룹 엑셀 샘플 요청", description = "그룹의 엑셀 샘플을 다운로드 합니다.")
    ResponseEntity<byte[]> excelSample() throws IOException;
}
