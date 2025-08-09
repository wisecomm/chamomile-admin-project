package net.lotte.chamomile.admin.menuauth.service;

import java.util.List;

import net.lotte.chamomile.admin.menuauth.domain.MenuComponentRoleVO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import net.lotte.chamomile.admin.auth.api.dto.AuthQuery;
import net.lotte.chamomile.admin.auth.domain.AuthVO;
import net.lotte.chamomile.admin.authhierarchy.domain.AuthHierarchyVO;
import net.lotte.chamomile.admin.authtree.RoleHierarchyTree;
import net.lotte.chamomile.admin.menu.api.dto.MenuQuery;
import net.lotte.chamomile.admin.menuauth.api.dto.MenuAuthQuery;
import net.lotte.chamomile.admin.menuauth.domain.MenuAuthVO;
import net.lotte.chamomile.admin.menutree.MenuHierarchyTree;

/**
 * <pre>
 * 메뉴 권한 관련 서비스.
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
public interface MenuAuthService {
    List<MenuAuthVO> getMenuAuthLeft(MenuAuthQuery request);

    List<MenuAuthVO> getMenuAuthRole(MenuAuthQuery request);

    void getMenuAuthDetail(List<MenuAuthVO> rightList, List<AuthHierarchyVO> authHierarchyList, List<MenuAuthVO> queueList);

    Page<AuthVO> getMenuListByAuth(AuthQuery request, Pageable pageable);

    List<MenuAuthVO> getAuthMenuLeft(AuthQuery request);

    List<MenuAuthVO> getAuthMenuRight(AuthQuery request);

    Page<MenuAuthVO> getMenuAuthExcelList(MenuQuery request, PageRequest pageable);

    Page<MenuAuthVO> getAuthMenuExcelList(AuthQuery request, PageRequest pageable);

    void updateMenuAuth(String menuId, List<String> roleIdList);

    void updateAuthMenu(String roleId, List<String> menuIdList);

    RoleHierarchyTree getMenuAuthRoleDetail(MenuAuthQuery request);

    MenuHierarchyTree getAuthMenuDetail(AuthQuery request);

    List<MenuComponentRoleVO> getMenuComponentsByRole(MenuAuthQuery request);

    void saveMenuComponentRole(String menuId, String roleId, List<String> componentList);
}
