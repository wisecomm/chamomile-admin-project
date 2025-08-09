package net.lotte.chamomile.admin.menu.domain;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.lotte.chamomile.admin.menu.api.dto.MenuQuery;
import net.lotte.chamomile.module.database.mybatis.batch.BatchRequest;

/**
 * <pre>
 * 메뉴 관련 Mapper.
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
@Mapper
public interface MenuMapper {
    Page<MenuVO> findMenuList(MenuQuery request, Pageable pageable);
    MenuVO findMenu(String menuId);
    
    Optional<MenuVO> findRoot();

    String selectLastMenuKey();

    void insertMenu(MenuVO request);

    List<MenuVO> findChildMenuList(String upperMenuId);

    void deleteMenuAuth(List<String> menuVOList);

    void deleteMenu(List<String> deleteList);

    void updateMenu(MenuVO request);

    void insertMenu(List<MenuVO> menuVOList, BatchRequest batchRequest);

    void insertBookmark(BookmarkVO request);

    void deleteBookmark(BookmarkVO request);

    BookmarkVO findBookmark(BookmarkVO request);

    Boolean checkComponentId(@Param("menuId") String menuId, @Param("componentId") String componentId);
    
    List<MenuComponentVO> findComponentList(String menuId);

    void insertMenuComponent(List<MenuComponentVO> request, BatchRequest batchRequest);

    void updateComponent(List<MenuComponentVO> request, BatchRequest batchRequest);
    
    void deleteComponent(List<MenuComponentVO> request, BatchRequest batchRequest);
}
