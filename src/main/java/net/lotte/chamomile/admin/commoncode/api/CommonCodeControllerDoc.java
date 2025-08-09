package net.lotte.chamomile.admin.commoncode.api;

import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import net.lotte.chamomile.admin.commoncode.api.dto.CategoryCommand;
import net.lotte.chamomile.admin.commoncode.api.dto.CategoryQuery;
import net.lotte.chamomile.admin.commoncode.api.dto.CodeCommand;
import net.lotte.chamomile.admin.commoncode.api.dto.CodeItemCommand;
import net.lotte.chamomile.admin.commoncode.api.dto.CodeItemQuery;
import net.lotte.chamomile.admin.commoncode.api.dto.CodeQuery;
import net.lotte.chamomile.admin.commoncode.domain.CategoryVO;
import net.lotte.chamomile.admin.commoncode.domain.CodeItemVO;
import net.lotte.chamomile.admin.commoncode.domain.CodeVO;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;

/**
 * <pre>
 * Admin 공통코드 관련 REST API Swagger Doc.
 * </pre>
 *
 * @ClassName   : CommonCodeControllerDoc.java
 * @Description : Admin 공통코드 관련(CRUD 등) REST API Swagger Doc.
 * @author chaelynJang
 * @since 2023.09.13
 * @version 3.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023.09.13     chaelynJang            최초 생성
 *
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@Tag(name = "캐모마일 어드민 CommonCodeAPI")
public interface CommonCodeControllerDoc {
    @Operation(summary = "공통코드 전체 목록 요청", description = "공통코드 전체 목록을 보여줍니다.")
    ChamomileResponse<Map<String, Object>> getCommonCodeAllList();

    @Operation(summary = "대분류 목록 요청", description = "대분류 목록을 보여줍니다.")
    ChamomileResponse<Page<CategoryVO>> getCategoryList(CategoryQuery request, Pageable pageable);
    @Operation(summary = "중분류 목록 요청", description = "중분류 목록을 보여줍니다.")
    ChamomileResponse<Page<CodeVO>> getCodeList(CodeQuery request, Pageable pageable);
    @Operation(summary = "소분류 목록 요청", description = "소분류 목록을 보여줍니다.")
    ChamomileResponse<Page<CodeItemVO>> getCodeList(CodeItemQuery request, Pageable pageable);

    @Operation(summary = "대분류 생성 요청", description = "대분류를 생성합니다.")
    ChamomileResponse<Void> createCategory(CategoryCommand categoryCommand);
    @Operation(summary = "중분류 생성 요청", description = "중분류를 생성합니다.")
    ChamomileResponse<Void> createCode(CodeCommand codeCommand);

    @Operation(summary = "소분류 생성 요청", description = "소분류를 생성합니다.")
    ChamomileResponse<Void> createCodeItem(CodeItemCommand codeItemCommand);

    @Operation(summary = "대분류 순서 변경 요청", description = "대분류 순서를 변경합니다.")
    ChamomileResponse<Void> updateCategoryOrder(List<CategoryCommand> categoryCommands);

    @Operation(summary = "중분류 순서 변경 요청", description = "중분류 순서를 변경합니다.")
    ChamomileResponse<Void> updateCodeOrder(List<CodeCommand> codeCommands);

    @Operation(summary = "소분류 순서 변경 요청", description = "소분류 순서를 변경합니다.")
    ChamomileResponse<Void> updateCodeItemOrder(List<CodeItemCommand> codeItemCommands);

    @Operation(summary = "대분류 수정 요청", description = "대분류를 수정합니다.")
    ChamomileResponse<Void> updateCategory(CategoryCommand categoryCommand);

    @Operation(summary = "증분류 수정 요청", description = "중분류를 수정합니다.")
    ChamomileResponse<Void> updateCode(CodeCommand codeCommand);

    @Operation(summary = "소분류 수정 요청", description = "소분류를 수정합니다.")
    ChamomileResponse<Void> updateCodeItem(CodeItemCommand codeItemCommand);

    @Operation(summary = "대분류 삭제 요청", description = "대분류를 삭제합니다.")
    ChamomileResponse<Void> deleteCategory(List<CategoryCommand> categoryCommands);

    @Operation(summary = "중분류 삭제 요청", description = "중분류를 삭제합니다.")
    ChamomileResponse<Void> deleteCode(List<CodeCommand> codeCommands);

    @Operation(summary = "소분류 삭제 요청", description = "소분류를 삭제합니다.")
    ChamomileResponse<Void> deleteCodeItem(List<CodeItemCommand> codeItemCommands);

    @Operation(summary = "대분류 엑셀 다운로드 요청", description = "대분류 목록을 다운로드합니다.")
    ResponseEntity<byte[]> exportCategoryExcel(CategoryQuery request) throws Exception;

    @Operation(summary = "중분류 엑셀 다운로드 요청", description = "중분류 목록을 다운로드합니다.")
    public ResponseEntity<byte[]> exportCodeExcel(CodeQuery request) throws Exception;

    @Operation(summary = "소분류 엑셀 다운로드 요청", description = "소분류 목록을 다운로드합니다.")
    ResponseEntity<byte[]> exportCodeItemExcel(CodeItemQuery request) throws Exception;

    @Operation(summary = "대분류 엑셀 템플릿 다운로드 요청", description = "대분류 엑셀 템플릿을 다운로드합니다.")
    ResponseEntity<byte[]> exportCodeExcel() throws Exception;
}
