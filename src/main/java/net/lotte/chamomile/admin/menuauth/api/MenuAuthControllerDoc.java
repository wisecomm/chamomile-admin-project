package net.lotte.chamomile.admin.menuauth.api;

import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import net.lotte.chamomile.admin.menuauth.domain.MenuComponentRoleVO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import net.lotte.chamomile.admin.auth.api.dto.AuthQuery;
import net.lotte.chamomile.admin.auth.domain.AuthVO;
import net.lotte.chamomile.admin.menu.api.dto.MenuQuery;
import net.lotte.chamomile.admin.menu.domain.MenuVO;
import net.lotte.chamomile.admin.menuauth.api.dto.MenuAuthQuery;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;

/**
 * <pre>
 * Admin 메뉴 권한 관련 REST API Swagger Doc.
 * </pre>
 *
 * @Description : Admin 메뉴 권한 관리(CRUD 등) REST API Swagger Doc.
 * @author chaelynJang
 * @since 2023.10.16
 * @version 3.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023.10.16     chaelynJang            최초 생성
 *
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@Tag(name = "캐모마일 메뉴권한 관리 API")
public interface MenuAuthControllerDoc {

    @Operation(summary = "메뉴 권한 목록 요청", description = "메뉴 권한 목록을 보여줍니다.")
    ChamomileResponse<Page<MenuVO>> getMenuAuthList(MenuQuery request, Pageable pageable);

    @Operation(summary = "메뉴 권한 상세 요청", description = "메뉴 권한 상세를 보여줍니다.")
    ChamomileResponse<Map<String, Object>> getMenuAuthDetail(MenuAuthQuery request);

    @Operation(summary = "메뉴 권한 목록(권한 기준) 요청", description = "메뉴 권한 목록(권한 기준)을 보여줍니다.")
    public ChamomileResponse<Page<AuthVO>> getMenuAuthListByAuth(AuthQuery request, Pageable pageable);

    @Operation(summary = "메뉴 권한 상세(권한 기준) 요청", description = "메뉴 권한 상세(권한 기준)을 보여줍니다.")
    ChamomileResponse<Map<String, Object>> getMenuAuthDetailByAuth(AuthQuery request);

    @Operation(summary = "메뉴 권한 목록(메뉴 기준) 엑셀 다운로드 요청", description = "메뉴 권한 목록(메뉴 기준)을 다운로드 합니다.")
    ResponseEntity<byte[]> exportMenuAuthExcel(MenuQuery request) throws Exception;

    @Operation(summary = "메뉴 권한 목록(권한 기준) 엑셀 다운로드 요청", description = "메뉴 권한 목록(권한 기준)을 다운로드 합니다.")
    ResponseEntity<byte[]> exportAuthMenuExcel(AuthQuery request) throws Exception;

    @Operation(summary = "메뉴권한관리(메뉴 기준) 상세 미사용/사용 권한 저장 요청", description = "메뉴권한관리(메뉴 기준) 상세 미사용/사용 권한을 저장합니다.")
    ChamomileResponse<Void> updateMenuAuth(String menuId, List<String> roleIdList);

    @Operation(summary = "메뉴권한관리(권한 기준) 상세 미사용/사용 권한 저장 요청", description = "메뉴권한관리(권한 기준) 상세 미사용/사용 권한을 저장합니다.")
    ChamomileResponse<Void> updateAuthMenu(String roleId, List<String> menuIdList);

    @Operation(summary = "메뉴 아이디, 권한 아이디에 따른 컴포넌트 리스트 조회 요청", description = "메뉴 아이디, 권한 아이디를 기준으로 컴포넌트 리스트를 조회합니다.")
    ChamomileResponse<List<MenuComponentRoleVO>> getMenuComponentsByRole(MenuAuthQuery request);

    @Operation(summary = "메뉴 컴포넌트 권한 매핑 정보 저장 요청", description = "메뉴 컴포넌트 권한 매핑 정보를 저장합니다.")
    ChamomileResponse<Void> updateMenuComponentRole(String menuId, String roleId, List<String> componentList);
}
