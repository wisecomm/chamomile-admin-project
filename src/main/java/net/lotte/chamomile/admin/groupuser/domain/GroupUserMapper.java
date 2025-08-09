package net.lotte.chamomile.admin.groupuser.domain;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.lotte.chamomile.module.database.mybatis.batch.BatchRequest;

/**
 * <pre>
 * 그룹 유저 매퍼.
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
public interface GroupUserMapper {
    Page<GroupUserVO> findUnMappedUserList(String groupId, Pageable pageable);

    List<GroupUserVO> findGroupUserList(String groupId);
    Page<GroupUserExcelVO> findGroupUserListExcel(Pageable pageable);
    List<GroupUserVO> findGroupUserListByUserId(String userId);

    void deleteGroupUserByGroupId(List<String> groupId, BatchRequest batchRequest);
    void deleteGroupUserByUserId(List<String> strings, BatchRequest batchRequest);

    void insertGroupUser(GroupUserVO command);
    void insertGroupUserExcel(List<GroupUserExcelVO> command, BatchRequest batchRequest);
}
