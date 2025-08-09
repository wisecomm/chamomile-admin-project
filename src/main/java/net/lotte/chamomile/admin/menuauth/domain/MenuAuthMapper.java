package net.lotte.chamomile.admin.menuauth.domain;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import net.lotte.chamomile.admin.auth.api.dto.AuthQuery;
import net.lotte.chamomile.admin.auth.domain.AuthVO;
import net.lotte.chamomile.admin.authtree.AuthTreeVO;
import net.lotte.chamomile.admin.menu.api.dto.MenuQuery;
import net.lotte.chamomile.admin.menuauth.api.dto.MenuAuthQuery;
import net.lotte.chamomile.admin.menuauth.domain.MenuAuthVO;
import net.lotte.chamomile.admin.menuauth.domain.MenuComponentRoleVO;
import net.lotte.chamomile.admin.menutree.MenuHierarchyVO;
import net.lotte.chamomile.admin.menutree.MenuTreeVO;
import net.lotte.chamomile.module.database.mybatis.batch.BatchRequest;
/**
 * <pre>
 * 메뉴 권한 관련 Mapper.
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
@Mapper
public interface MenuAuthMapper {
    List<MenuAuthVO> findMenuAuthLeft(MenuAuthQuery request);

    List<MenuAuthVO> findMenuAuthRole(MenuAuthQuery request);

    Page<AuthVO> findMenuListByAuth(AuthQuery request, Pageable pageable);

    List<MenuAuthVO> findAuthMenuLeft(AuthQuery request);

    List<MenuAuthVO> findAuthMenuRight(AuthQuery request);

    Page<MenuAuthVO> findMenuAuthExcelList(MenuQuery request, PageRequest pageable);

    Page<MenuAuthVO> findAuthMenuExcelList(AuthQuery request, PageRequest pageable);

    void deleteMenuAuth(String menuId);

    void insertMenuAuth(List<MenuAuthVO> list, BatchRequest batchRequest);

    void deleteAuthMenu(String roleId);

    List<AuthTreeVO> findUnusedRoleList(MenuAuthQuery request);

    List<AuthTreeVO> findUsedRoleList(MenuAuthQuery request);

    List<MenuHierarchyVO> findAllHierarchyMenu();

    List<MenuTreeVO> findUnusedMenuList(AuthQuery request);

    List<MenuTreeVO> findUsedMenuList(AuthQuery request);

    List<MenuComponentRoleVO> findMenuComponentsByRole(MenuAuthQuery request);

    void deleteMenuComponentRole(@Param("menuId") String menuId, @Param("roleId") String roleId);

    void insertMenuComponentRoleMap(List<MenuComponentRoleVO> list, BatchRequest batchRequest);
}

