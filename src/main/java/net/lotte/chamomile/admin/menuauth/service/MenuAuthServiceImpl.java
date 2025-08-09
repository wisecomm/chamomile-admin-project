package net.lotte.chamomile.admin.menuauth.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import net.lotte.chamomile.admin.menuauth.domain.MenuComponentRoleVO;
import net.lotte.chamomile.module.security.SecurityService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.lotte.chamomile.admin.auth.api.dto.AuthQuery;
import net.lotte.chamomile.admin.auth.domain.AuthVO;
import net.lotte.chamomile.admin.authhierarchy.domain.AuthHierarchyMapper;
import net.lotte.chamomile.admin.authhierarchy.domain.AuthHierarchyVO;
import net.lotte.chamomile.admin.authtree.AuthTreeVO;
import net.lotte.chamomile.admin.authtree.RoleHierarchyTree;
import net.lotte.chamomile.admin.authtree.RoleHierarchyVO;
import net.lotte.chamomile.admin.menu.api.dto.MenuQuery;
import net.lotte.chamomile.admin.menuauth.api.dto.MenuAuthQuery;
import net.lotte.chamomile.admin.menuauth.domain.MenuAuthMapper;
import net.lotte.chamomile.admin.menuauth.domain.MenuAuthVO;
import net.lotte.chamomile.admin.menutree.MenuHierarchyTree;
import net.lotte.chamomile.admin.menutree.MenuHierarchyVO;
import net.lotte.chamomile.admin.menutree.MenuTreeVO;
import net.lotte.chamomile.module.database.mybatis.batch.BatchRequest;
import net.lotte.chamomile.module.util.string.StringUtil;

/**
 * <pre>
 * 메뉴 권한 관련 서비스 구현체.
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
@Service
@Transactional
@RequiredArgsConstructor
public class MenuAuthServiceImpl implements MenuAuthService {
    private final MenuAuthMapper menuAuthMapper;
    private final AuthHierarchyMapper authHierarchyMapper;
    private final SecurityService securityService;
    private final DateTimeFormatter yyyyMMddFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public List<MenuAuthVO> getMenuAuthLeft(MenuAuthQuery request) {
        return menuAuthMapper.findMenuAuthLeft(request);
    }

    @Override
    public List<MenuAuthVO> getMenuAuthRole(MenuAuthQuery request) {
        return menuAuthMapper.findMenuAuthRole(request);
    }

    @Override
    public void getMenuAuthDetail(List<MenuAuthVO> rightList, List<AuthHierarchyVO> authHierarchyList, List<MenuAuthVO> roleList) {
        Queue<String> queue = new LinkedList<>();
        Set<AuthHierarchyVO> set = new HashSet<>();
        for(MenuAuthVO menuAuthVO : roleList) {
            queue.add(menuAuthVO.getRoleId());
            rightList.add(new MenuAuthVO(menuAuthVO.getMenuId(), menuAuthVO.getRoleId(), null, menuAuthVO.getRoleId(), menuAuthVO.getRoleId()));
        }
        // 가지고 있는 것보다 상위 권한 조회
        while(!queue.isEmpty()) {
            int qSize = queue.size();
            for (int i = 0; i < qSize; i++) {
                String currentRoleId = queue.poll();
                Iterator<AuthHierarchyVO> it = authHierarchyList.iterator();
                while(it.hasNext()) {
                    AuthHierarchyVO nextVo = it.next();
                    String nextParentRoleId = nextVo.getParentRoleId();
                    String nextChildRoleId = nextVo.getChildRoleId();
                    if(!nextChildRoleId.equals(currentRoleId)) continue;
                    if(set.contains(nextVo)) continue;
                    queue.add(nextParentRoleId);
                    set.add(nextVo);
                }
            }
        }
        List<AuthHierarchyVO> setList = new ArrayList<>(set);
        for (AuthHierarchyVO authHierarchyVO : setList) {
            MenuAuthVO menuAuthVO = new MenuAuthVO();
            menuAuthVO.setVal(authHierarchyVO.getParentRoleId() + "*");
            menuAuthVO.setText(authHierarchyVO.getParentRoleId() + "(by " + authHierarchyVO.getChildRoleId() + ")");
            menuAuthVO.setFixed(true);
            rightList.add(menuAuthVO);
        }
    }

    @Override
    public Page<AuthVO> getMenuListByAuth(AuthQuery request, Pageable pageable) {
        return menuAuthMapper.findMenuListByAuth(request, pageable);
    }

    @Override
    public List<MenuAuthVO> getAuthMenuLeft(AuthQuery request) {
        return menuAuthMapper.findAuthMenuLeft(request);
    }

    @Override
    public List<MenuAuthVO> getAuthMenuRight(AuthQuery request) {
        return menuAuthMapper.findAuthMenuRight(request);
    }

    @Override
    public Page<MenuAuthVO> getMenuAuthExcelList(MenuQuery request, PageRequest pageable) {
        return menuAuthMapper.findMenuAuthExcelList(request, pageable);
    }

    @Override
    public Page<MenuAuthVO> getAuthMenuExcelList(AuthQuery request, PageRequest pageable) {
        return menuAuthMapper.findAuthMenuExcelList(request, pageable);
    }

    @Override
    public void updateMenuAuth(String menuId, List<String> roleIdList) {
        menuAuthMapper.deleteMenuAuth(menuId);
        if(!roleIdList.isEmpty()) {
            List<MenuAuthVO> list = new ArrayList<>();
            for (String roleId : roleIdList) {
                MenuAuthVO menuAuthVO = new MenuAuthVO(menuId, roleId, null, null, null);
                menuAuthVO.onCreate();
                list.add(menuAuthVO);
            }
            menuAuthMapper.insertMenuAuth(list, new BatchRequest(1000));
        }
    }

    @Override
    public void updateAuthMenu(String roleId, List<String> menuIdList) {
        menuAuthMapper.deleteAuthMenu(roleId);
        if(!menuIdList.isEmpty()) {
            List<MenuAuthVO> list = new ArrayList<>();
            for(String menuId : menuIdList) {
                MenuAuthVO menuAuthVO = new MenuAuthVO(menuId, roleId, null, null, null);
                menuAuthVO.onCreate();
                list.add(menuAuthVO);
            }
            menuAuthMapper.insertMenuAuth(list, new BatchRequest(1000));
        }
    }

    @Override
    public RoleHierarchyTree getMenuAuthRoleDetail(MenuAuthQuery request) {
        if (StringUtil.isNotBlank(request.getSearchRoleEndDt())) {
            LocalDate searchRoleEndDt = LocalDate.parse(request.getSearchRoleStartDt(), dtFormatter);
            request.setSearchRoleEndDt(searchRoleEndDt.plusDays(1).format(dtFormatter));
        }

        List<RoleHierarchyVO> allHierarchyRole = authHierarchyMapper.findHierarchyRoles(request);
        RoleHierarchyTree tree = new RoleHierarchyTree(allHierarchyRole);
        tree.addSingleRole(authHierarchyMapper.findSingleRoleList());
        List<AuthTreeVO> leftList = menuAuthMapper.findUnusedRoleList(request);
        tree.mapToLeftTreeData(leftList);
        List<AuthTreeVO> rightList = menuAuthMapper.findUsedRoleList(request);
        tree.mapToRightTreeDataReverse(rightList);
        return tree;
    }

    @Override
    public MenuHierarchyTree getAuthMenuDetail(AuthQuery request) {
        List<MenuHierarchyVO> allHierarchyMenu = menuAuthMapper.findAllHierarchyMenu();
        MenuHierarchyTree tree = new MenuHierarchyTree(allHierarchyMenu);
        List<MenuTreeVO> leftList = menuAuthMapper.findUnusedMenuList(request);
        tree.mapToLeftTreeData(leftList);
        List<MenuTreeVO> rightList = menuAuthMapper.findUsedMenuList(request);
        tree.mapToRightTreeData(rightList);

        // root 부분 삭제 하위값을 최상위로 올림
        tree.setTopNodes(tree.getTopNodes().get(0).getChildren());
        return tree;
    }

    @Override
    public List<MenuComponentRoleVO> getMenuComponentsByRole(MenuAuthQuery request) {
        return menuAuthMapper.findMenuComponentsByRole(request);
    }

    @Override
    public void saveMenuComponentRole(String menuId, String roleId, List<String> componentList) {
        // 기존 매핑 정보 삭제
        menuAuthMapper.deleteMenuComponentRole(menuId, roleId);
        List<MenuComponentRoleVO> list = new ArrayList<>();
        for (String componentId : componentList) {
            MenuComponentRoleVO inputVO = new MenuComponentRoleVO(menuId, componentId, roleId);
            inputVO.onCreate();
            list.add(inputVO);
        }
        menuAuthMapper.insertMenuComponentRoleMap(list, new BatchRequest(1000));
        securityService.reloadRoleHierarchy();
        securityService.reloadUrlSecurity();
    }
}
