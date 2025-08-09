package net.lotte.chamomile.admin.group.domain;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.lotte.chamomile.admin.group.api.dto.GroupQuery;
import net.lotte.chamomile.module.database.mybatis.batch.BatchRequest;

/**
 * <pre>
 * 그룹 매퍼.
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
public interface GroupMapper {
    Page<GroupVO> findGroupList(GroupQuery query, Pageable pageable);
    List<GroupVO> findGroupListWithId(String groupId);
    Optional<GroupVO> findGroup(String groupId);
    void insertGroup(GroupVO command);
    void updateGroup(GroupVO command);
    void deleteGroup(List<String> groupId, BatchRequest batchRequest);
    Set<String> groupSortedSet();

    Page<GroupExcelVO> findGroupListExcel(GroupQuery query, Pageable pageable);
    void insertGroup(List<GroupExcelVO> command, BatchRequest batchRequest);
}
