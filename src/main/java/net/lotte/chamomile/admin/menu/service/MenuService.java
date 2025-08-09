package net.lotte.chamomile.admin.menu.service;

import java.util.List;
import java.util.Map;

import net.lotte.chamomile.admin.menu.domain.MenuComponentVO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.lotte.chamomile.admin.menu.api.dto.MenuQuery;
import net.lotte.chamomile.admin.menu.domain.BookmarkVO;
import net.lotte.chamomile.admin.menu.domain.MenuExcelUploadVO;
import net.lotte.chamomile.admin.menu.domain.MenuVO;

/**
 * <pre>
 * 메뉴 관련 서비스 인터페이스.
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
public interface MenuService {
    Page<MenuVO> findMenuList(MenuQuery request, Pageable pageable);
    MenuVO findMenu(String menuId);
    MenuVO readRootLevelMenu();

    String createMenu(MenuVO request);

    void deleteMenu(List<MenuVO> menuVOList);

    void updateMenu(MenuVO request);

    List<Map<String, Object>> getLeftMenuList(String loginId);

    void createBulkMenu(List<MenuExcelUploadVO> list);

    void createBookmark(BookmarkVO request);

    void deleteBookmark(BookmarkVO request);

    Boolean checkComponentId(String menuId, String componentId);
    
    List<MenuComponentVO> findComponentList(String menuId);

    void createComponents(List<MenuComponentVO> request);

    void updateComponents(List<MenuComponentVO> request);
    
    void deleteComponents(List<MenuComponentVO> request);
}
