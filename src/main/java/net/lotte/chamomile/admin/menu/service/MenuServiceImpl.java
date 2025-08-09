package net.lotte.chamomile.admin.menu.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Stack;

import org.springframework.beans.BeanUtils;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lotte.chamomile.admin.common.exception.code.AdminExceptionCode;
import net.lotte.chamomile.admin.menu.api.dto.MenuQuery;
import net.lotte.chamomile.admin.menu.domain.BookmarkVO;
import net.lotte.chamomile.admin.menu.domain.MenuComponentVO;
import net.lotte.chamomile.admin.menu.domain.MenuExcelUploadVO;
import net.lotte.chamomile.admin.menu.domain.MenuMapper;
import net.lotte.chamomile.admin.menu.domain.MenuVO;
import net.lotte.chamomile.admin.resource.domain.ResourceMapper;
import net.lotte.chamomile.core.exception.BusinessException;
import net.lotte.chamomile.module.database.mybatis.batch.BatchRequest;
import net.lotte.chamomile.module.util.string.StringUtil;

/**
 * <pre>
 * 메뉴 관련 서비스 인터페이스 구현체.
 * </pre>
 *
 * @author chaelynJang
 * @version 3.0
 * @Modification <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-09-26     chaelynJang            최초 생성
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 * @since 2023-09-26
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuMapper menuMapper;
    private final net.lotte.chamomile.module.menu.MenuService menuModuleService;
    private final ResourceMapper resourceMapper;
    private final CacheManager cacheManager;

    @Override
    @Transactional(readOnly = true)
    public Page<MenuVO> findMenuList(MenuQuery request, Pageable pageable) {
        return menuMapper.findMenuList(request, pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
	public MenuVO findMenu(String menuId) {
		return menuMapper.findMenu(menuId);
	}

    @Override
    @Transactional(readOnly = true)
    public MenuVO readRootLevelMenu() {
        return menuMapper.findRoot().orElseThrow(() -> new NoSuchElementException("최상위 메뉴가 존재하지 않습니다."));
    }

    @CacheEvict(value = "commonLeftMenu", allEntries = true)
    @Override
    @Transactional
    public String createMenu(MenuVO request) {
        if(ObjectUtils.isEmpty(request)) {
            throw new BusinessException(AdminExceptionCode.ServerError);
        }
        String menuKey = getMenuKey();
        request.setMenuId(menuKey);
        request.onCreate();
        menuMapper.insertMenu(request);
        return menuKey;
        // if(StringUtils.hasText(request.getMenuUri())) {
        //     ResourceVO resourceVO = new ResourceVO(menuKey);
        //     resourceVO.setResourceDesc(request.getMenuDesc());
        //     resourceVO.setResourceHttpMethod("");
        //     resourceVO.setResourceName(request.getMenuName());
        //     resourceVO.setResourceUri(request.getMenuUri());
        //     resourceVO.setSecurityOrder(99998);
        //     resourceVO.setUseYn(request.getUseYn());
        //     resourceMapper.insertResource(resourceVO);
        // }
    }

    @CacheEvict(value = "commonLeftMenu", allEntries = true)
    @Override
    public void  deleteMenu(List<MenuVO> menuVOList) {
        // 삭제할 메뉴 + 하위 메뉴 전체 조회
        List<String> deleteList = getDeleteMenuIdWithChild(menuVOList);
        // 메뉴 권한 삭제
        menuMapper.deleteMenuAuth(deleteList);
        // 메뉴 삭제
        menuMapper.deleteMenu(deleteList);
    }

    private List<String> getDeleteMenuIdWithChild(List<MenuVO> menuVOList) {
        Set<String> delMenuIdSet = new HashSet<>(); // 삭제할 메뉴ID 저장
        Stack<String> menuIdStk = new Stack<>(); // 재귀 조회할 하위 메뉴ID 저장

        for (MenuVO menuVO : menuVOList) {
            if (delMenuIdSet.contains(menuVO.getMenuId())) {
                continue; // 이미 조회한 MenuId는 Skip
            }
            delMenuIdSet.add(menuVO.getMenuId());

            menuIdStk.push(menuVO.getMenuId());
            while (!menuIdStk.isEmpty()) {
                String menuId = menuIdStk.pop();

                List<MenuVO> childMenuList = menuMapper.findChildMenuList(menuId);
                for (MenuVO childMenu : childMenuList) {
                    String childMenuId = childMenu.getMenuId();
                    if (!delMenuIdSet.contains(childMenuId)) {
                        delMenuIdSet.add(childMenuId);
                        menuIdStk.push(childMenuId);
                    }
                }
            }
        }
        return new ArrayList<>(delMenuIdSet);
    }

    @CacheEvict(value = "commonLeftMenu", allEntries = true)
    @Override
    public void updateMenu(MenuVO request) {
        if(ObjectUtils.isEmpty(request)) {
            throw new BusinessException(AdminExceptionCode.ServerError);
        }
        request.onUpdate();
        menuMapper.updateMenu(request);
    }

    //@Cacheable(value = "commonLeftMenu", key = "#loginId")
    @Override
    public List<Map<String, Object>> getLeftMenuList(String loginId) {
        return menuModuleService.findMyMenu(net.lotte.chamomile.module.menu.MenuService.ADMIN_MENU, null);
    }

    @Override
    @Transactional
    public void createBulkMenu(List<MenuExcelUploadVO> list) {
        List<MenuVO> insertList = new ArrayList<>();
        String lastMenuKey = menuMapper.selectLastMenuKey(); // max+1 조회
        int key = 0;
        if (StringUtil.isNotBlank(lastMenuKey)) {
            lastMenuKey = lastMenuKey.replaceAll("[^0-9]+", "");
            key = Integer.parseInt(lastMenuKey) + 1;
        }
        for (MenuExcelUploadVO vo : list) {
            MenuVO insertVo = new MenuVO();
            BeanUtils.copyProperties(vo, insertVo);
            insertVo.setMenuId("menu" + String.format("%08d", key));
            insertVo.onCreate();
            insertList.add(insertVo);
            key += 1;
        }
        menuMapper.insertMenu(insertList, new BatchRequest(1000));
    }

    private String getMenuKey() {
        String lastMenuKey = menuMapper.selectLastMenuKey(); // max 조회
        int key = 0;
        if (StringUtil.isNotBlank(lastMenuKey)) {
            lastMenuKey = lastMenuKey.replaceAll("[^0-9]+", "");
            key = Integer.parseInt(lastMenuKey) + 1;
        }
        String temp = String.format("%08d", key);
        return "menu" + temp;
    }

    @CacheEvict(value = "commonLeftMenu", allEntries = true)
    public void createBookmark(BookmarkVO request) {
        if (ObjectUtils.isEmpty(request)) {
            throw new BusinessException(AdminExceptionCode.ServerError);
        }
        request.onCreate();
        menuMapper.insertBookmark(request);

    }

    @CacheEvict(value = "commonLeftMenu", allEntries = true)
    public void deleteBookmark(BookmarkVO request) {
        if (ObjectUtils.isEmpty(request)) {
            throw new BusinessException(AdminExceptionCode.ServerError);
        }
        menuMapper.deleteBookmark(request);
    }

    @Override
    public Boolean checkComponentId(String menuId, String componentId) {
        return menuMapper.checkComponentId(menuId, componentId);
    }
    
    @Override
    public List<MenuComponentVO> findComponentList(String menuId) {
    	return menuMapper.findComponentList(menuId);
    }

    @Override
    public void createComponents(List<MenuComponentVO> request) {
        if(ObjectUtils.isEmpty(request)) {
            throw new BusinessException(AdminExceptionCode.ServerError);
        }
        for (MenuComponentVO componentVO : request) {
            componentVO.onCreate();
        }
        menuMapper.insertMenuComponent(request, new BatchRequest(1000));
    }

    @Override
    public void updateComponents(List<MenuComponentVO> request) {
        if(ObjectUtils.isEmpty(request)) {
            throw new BusinessException(AdminExceptionCode.ServerError);
        }
        menuMapper.updateComponent(request, new BatchRequest(1000));
    }
    
    @Override
    public void deleteComponents(List<MenuComponentVO> request) {
        if(ObjectUtils.isEmpty(request)) {
            throw new BusinessException(AdminExceptionCode.ServerError);
        }
        menuMapper.deleteComponent(request, new BatchRequest(1000));
    }
}
