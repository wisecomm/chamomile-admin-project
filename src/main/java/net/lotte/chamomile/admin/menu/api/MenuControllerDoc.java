package net.lotte.chamomile.admin.menu.api;

import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import net.lotte.chamomile.admin.menu.domain.MenuComponentVO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import net.lotte.chamomile.admin.menu.api.dto.MenuQuery;
import net.lotte.chamomile.admin.menu.domain.BookmarkVO;
import net.lotte.chamomile.admin.menu.domain.MenuVO;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;

/**
 * <pre>
 * Admin 메뉴 관련 REST API Swagger Doc.
 * </pre>
 *
 * @ClassName   : MenuControllerDoc.java
 * @Description : Admin 메뉴 관련(CRUD 등) REST API Swagger Doc.
 * @author chaelynJang
 * @since 2023.10.05
 * @version 3.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023.10.05     chaelynJang            최초 생성
 *
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@Tag(name = "캐모마일 메뉴 MenuAPI")
public interface MenuControllerDoc {
    @Operation(summary = "메뉴 목록 요청", description = "메뉴 목록을 보여줍니다.")
    ChamomileResponse<Page<MenuVO>> getMenuList(MenuQuery request, Pageable pageable);

    @Operation(summary = "메뉴 전체 목록 요청", description = "메뉴 전체 목록을 보여줍니다.")
    ChamomileResponse<List<MenuVO>> getMenuTreeData();

    @Operation(summary = "메뉴 생성 요청", description = "메뉴를 생성합니다.")
    ChamomileResponse<String> createMenu(MenuVO request, MultipartFile file);

    @Operation(summary = "메뉴 삭제 요청", description = "메뉴를 삭제합니다.")
    ChamomileResponse<Void> deleteMenu(List<MenuVO> menuVOList);

    @Operation(summary = "메뉴 수정 요청", description = "메뉴를 수정합니다.")
    ChamomileResponse<Void> updateMenu(MenuVO request, MultipartFile file);

    @Operation(summary = "왼쪽 메뉴 목록 조회 요청", description = "왼쪽 메뉴 목록을 보여줍니다.")
    ChamomileResponse<List<Map<String, Object>>> getLeftMenuList();

    @Operation(summary = "메뉴 엑셀 다운로드 요청", description = "메뉴 목록 엑셀을 다운로드합니다.")
    public ResponseEntity<byte[]> exportMenuExcel(MenuQuery request) throws Exception;

    @Operation(summary = "메뉴 엑셀 템플릿 다운로드 요청", description = "메뉴 엑셀 템플릿을 다운로드합니다.")
    ResponseEntity<byte[]> exportMenuTemplateExcel() throws Exception;

    @Operation(summary = "메뉴 엑셀 데이터 업로드 요청", description = "메뉴 엑셀 데이터를 업로드 합니다.")
    ChamomileResponse<Void> excelUpload(@RequestParam("file") MultipartFile file);


    @Operation(summary = "즐겨찾기 등록 요청", description = "즐겨찾기 요청을 등록 합니다.")
    ChamomileResponse<Void> createBookmark(@Validated @RequestBody BookmarkVO request);

    @Operation(summary = "즐겨찾기 삭제 요청", description = "즐겨찾기 삭제 요청을 합니다.")
    ChamomileResponse<Void> deleteBookmark(@Validated @RequestBody BookmarkVO request);

    @Operation(summary = "컴포넌트 아이디 중복 확인 요청", description = "컴포넌트 아이디가 중복되는지 확인합니다.")
    ResponseEntity<Boolean> checkComponentId(String menuId, String componentId);

    @Operation(summary = "컴포넌트 리스트 조회 요청", description = "컴포넌트 리스트를 조회합니다.")
    ChamomileResponse<List<MenuComponentVO>> getComponentList(String menuId);

    @Operation(summary = "컴포넌트 생성 요청", description = "화면에서 사용할 컴포넌트를 생성합니다.")
    ChamomileResponse<Void> createComponents(List<MenuComponentVO> request);

    @Operation(summary = "컴포넌트 수정 요청", description = "화면에서 사용할 컴포넌트를 수정합니다.")
    public ChamomileResponse<Void> updateComponent(List<MenuComponentVO> request);

    @Operation(summary = "컴포넌트 삭제 요청", description = "화면에서 사용할 컴포넌트를 삭제합니다.")
    ChamomileResponse<Void> deleteComponents(List<MenuComponentVO> request);
}
