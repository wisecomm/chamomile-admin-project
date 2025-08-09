package net.lotte.chamomile.admin.grouphierarchy.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.lotte.chamomile.admin.grouphierarchy.domain.GroupHierarchyExcelVO;
import net.lotte.chamomile.admin.grouphierarchy.domain.GroupHierarchyVO;

/**
 * <pre>
 * 그룹 상하관계 서비스 인터페이스.
 * </pre>
 *
 * @author MoonHKLee
 * @since 2023-11-09
 * @version 3.0
 * @Modification
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-11-09     MoonHKLee            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
public interface GroupHierarchyService {
    Page<GroupHierarchyVO> getGroupHierarchyList(Pageable pageable);
    Page<GroupHierarchyExcelVO> getGroupHierarchyListExcel(Pageable pageable);

    List<GroupHierarchyVO> getGroupHierarchy(String groupId);

    void createGroupHierarchy(GroupHierarchyVO command);
    void createGroupHierarchy(List<GroupHierarchyExcelVO> command);

    void deleteGroupHierarchy(List<GroupHierarchyVO> command);
}
