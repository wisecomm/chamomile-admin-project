package net.lotte.chamomile.admin.grouphierarchy.domain;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.lotte.chamomile.module.database.mybatis.batch.BatchRequest;

/**
 * <pre>
 * 그룹 상하관계 매퍼.
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
@Mapper
public interface GroupHierarchyMapper {
    Page<GroupHierarchyVO> findGroupHierarchyListData(Pageable pageable);
    Page<GroupHierarchyExcelVO> findGroupHierarchyListDataExcel(Pageable pageable);
    List<GroupHierarchyVO> findGroupHierarchyList(String groupId);
    List<GroupHierarchyVO> findGroupHierarchyListByParentAndChild(GroupHierarchyVO query);
    List<GroupHierarchyVO> findAllGroupHierarchyList();
    void insertGroupHierarchy(GroupHierarchyVO command);
    void insertGroupHierarchyExcel(List<GroupHierarchyExcelVO> command, BatchRequest batchRequest);
    void deleteGroupHierarchy(List<GroupHierarchyVO> command, BatchRequest batchRequest);
    void deleteAllGroupHierarchy(List<String> deleteIds, BatchRequest batchRequest);
}
