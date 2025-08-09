package net.lotte.chamomile.admin.menuauth.api;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import net.lotte.chamomile.admin.menuauth.domain.MenuComponentRoleVO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.lotte.chamomile.admin.auth.api.dto.AuthQuery;
import net.lotte.chamomile.admin.auth.domain.AuthVO;
import net.lotte.chamomile.admin.authhierarchy.api.dto.AuthHierarchyQuery;
import net.lotte.chamomile.admin.authhierarchy.domain.AuthHierarchyVO;
import net.lotte.chamomile.admin.authhierarchy.service.AuthHierarchyService;
import net.lotte.chamomile.admin.authtree.RoleHierarchyTree;
import net.lotte.chamomile.admin.menu.api.dto.MenuQuery;
import net.lotte.chamomile.admin.menu.domain.MenuVO;
import net.lotte.chamomile.admin.menu.service.MenuService;
import net.lotte.chamomile.admin.menuauth.api.dto.MenuAuthQuery;
import net.lotte.chamomile.admin.menuauth.domain.MenuAuthVO;
import net.lotte.chamomile.admin.menuauth.service.MenuAuthService;
import net.lotte.chamomile.admin.menutree.MenuHierarchyTree;
import net.lotte.chamomile.module.excel.ExcelExporter;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;

/**
 * <pre>
 * 메뉴 권한 REST API 관련 컨트롤러.
 * </pre>
 *
 * @author chaelynJang
 * @version 3.0
 * @Modification <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-10-16     chaelynJang            최초 생성
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 * @since 2023-10-16
 */
@Slf4j
@RestController
@RequestMapping(path = "/chmm")
@RequiredArgsConstructor
public class MenuAuthController implements MenuAuthControllerDoc {
    private final MenuAuthService menuAuthService;
    private final MenuService menuService;
    private final AuthHierarchyService authHierarchyService;

    @GetMapping("/menu-auth/list")
    public ChamomileResponse<Page<MenuVO>> getMenuAuthList(MenuQuery request, Pageable pageable) {
    	Page<MenuVO> result = null;
    	
    	// 240905 임지훈 - 검색조건 입력 시 부모노드를 조회하지 못해 트리를 생성하지 못하는 문제 해결
    	if (!StringUtils.isEmpty(request.getSearchMenu()) || 
    		!StringUtils.isEmpty(request.getSearchUseYn())
    	) {
    		List<MenuVO> menus = new ArrayList<>();
    		Map<String, MenuVO> menusMap = new HashMap<>();
    		
    		getMenuListParent(request, pageable, menus, menusMap, true);
    		result = new PageImpl<>(menus, pageable, menus.size());
    	} else {
    		result = menuService.findMenuList(request, pageable);
    	}
    	
        return new ChamomileResponse<>(result);
    }

    private void getMenuListParent(MenuQuery request, Pageable pageable, List<MenuVO> menus, Map<String, MenuVO> menusMap, boolean isHeader) {
    	List<MenuVO> menuList = new ArrayList<>();

    	if (isHeader) {
    		menuList = menuService.findMenuList(request, pageable).getContent();
    	} else {
    		MenuVO menu = menuService.findMenu(request.getSearchMenu());
    		
    		if (menu != null) {
    			menuList.add(menu);
    		}
    	}
    	
    	for (MenuVO menu : menuList) {
    		menus.add(menu);
    		menusMap.put(menu.getMenuId(), menu);
    		
    		if (!"root".equals(menu.getUpperMenuId()) && !menusMap.containsKey(menu.getUpperMenuId())) {
    			getMenuListParent(MenuQuery.builder().searchMenu(menu.getUpperMenuId()).build(), pageable, menus, menusMap, false);
			}
    	}
    }

    @GetMapping("/menu-auth/detail")
    public ChamomileResponse<Map<String, Object>> getMenuAuthDetail(MenuAuthQuery request) {
        // 트리 구조 조회
        RoleHierarchyTree tree = menuAuthService.getMenuAuthRoleDetail(request);

        // 목록 조회
        List<MenuAuthVO> leftList = menuAuthService.getMenuAuthLeft(request);
        List<MenuAuthVO> rightList = new ArrayList<>();

        // 권한 계층 조회
        List<AuthHierarchyVO> authHierarchyList = authHierarchyService.getAuthHierarchyList(new AuthHierarchyQuery());
        List<MenuAuthVO> roleList = menuAuthService.getMenuAuthRole(request);

        menuAuthService.getMenuAuthDetail(rightList, authHierarchyList, roleList);
        Map<String, Object> map = new HashMap<>();
        map.put("_leftList", leftList);
        map.put("_rightList", rightList);
        map.put("tree", tree);
        return new ChamomileResponse<>(map);
    }

    @GetMapping("/auth-menu/list")
    public ChamomileResponse<Page<AuthVO>> getMenuAuthListByAuth(AuthQuery request, Pageable pageable) {
        return new ChamomileResponse<>(menuAuthService.getMenuListByAuth(request, pageable));
    }

    @GetMapping("/auth-menu/detail")
    public ChamomileResponse<Map<String, Object>> getMenuAuthDetailByAuth(AuthQuery request) {
        MenuHierarchyTree tree = menuAuthService.getAuthMenuDetail(request);
        List<MenuAuthVO> leftList = menuAuthService.getAuthMenuLeft(request);
        List<MenuAuthVO> rightList = menuAuthService.getAuthMenuRight(request);
        Map<String, Object> map = new HashMap<>();
        map.put("_leftList", leftList);
        map.put("_rightList", rightList);
        map.put("tree", tree);
        return new ChamomileResponse<>(map);
    }

    @GetMapping("/menu-auth/excel/download")
    public ResponseEntity<byte[]> exportMenuAuthExcel(MenuQuery request) throws Exception {
        String docName = URLEncoder.encode("menu_auth", "UTF-8");
        ExcelExporter excelExporter = new ExcelExporter(docName);
        excelExporter.addDataList(menuAuthService.getMenuAuthExcelList(request
                , PageRequest.of(0, Integer.MAX_VALUE)).getContent());
        return excelExporter.toHttpResponse();
    }

    @GetMapping("/auth-menu/excel/download")
    public ResponseEntity<byte[]> exportAuthMenuExcel(AuthQuery request) throws Exception {
        String docName = URLEncoder.encode("auth_menu", "UTF-8");
        ExcelExporter excelExporter = new ExcelExporter(docName);
        excelExporter.addDataList(menuAuthService.getAuthMenuExcelList(request
                , PageRequest.of(0, Integer.MAX_VALUE)).getContent());
        return excelExporter.toHttpResponse();
    }

    @PostMapping("/menu-auth/update/{menuId}")
    public ChamomileResponse<Void> updateMenuAuth(@PathVariable String menuId, @RequestBody List<String> roleIdList) {
        menuAuthService.updateMenuAuth(menuId, roleIdList);
        return new ChamomileResponse<>();
    }

    @PostMapping("/auth-menu/update/{roleId}")
    public ChamomileResponse<Void> updateAuthMenu(@PathVariable String roleId, @RequestBody List<String> menuIdList) {
        menuAuthService.updateAuthMenu(roleId, menuIdList);
        return new ChamomileResponse<>();
    }

    @GetMapping("/menu/component/auth/list")
    public ChamomileResponse<List<MenuComponentRoleVO>> getMenuComponentsByRole(MenuAuthQuery request) {
        return new ChamomileResponse<>(menuAuthService.getMenuComponentsByRole(request));
    }

    @PostMapping("/menu/component/auth/update/{menuId}/{roleId}")
    public ChamomileResponse<Void> updateMenuComponentRole(@PathVariable String menuId, @PathVariable String roleId
            , @RequestBody List<String> componentList) {
        menuAuthService.saveMenuComponentRole(menuId, roleId, componentList);
        return new ChamomileResponse<>();
    }
}
