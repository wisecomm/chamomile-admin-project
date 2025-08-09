package net.lotte.chamomile.admin.group.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.lotte.chamomile.admin.group.api.dto.GroupQuery;
import net.lotte.chamomile.admin.group.domain.GroupExcelVO;
import net.lotte.chamomile.admin.group.domain.GroupMapper;
import net.lotte.chamomile.admin.group.domain.GroupVO;
import net.lotte.chamomile.admin.group.service.GroupService;
import net.lotte.chamomile.admin.grouphierarchy.domain.GroupHierarchyMapper;
import net.lotte.chamomile.admin.grouphierarchy.domain.GroupHierarchyVO;
import net.lotte.chamomile.admin.groupuser.domain.GroupUserMapper;
import net.lotte.chamomile.module.database.audit.TimeAuthorLog;
import net.lotte.chamomile.module.database.mybatis.batch.BatchRequest;

/**
 * <pre>
 * 그룹 서비스 구현 객체.
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
@Slf4j
@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupMapper groupMapper;
    private final GroupUserMapper groupUserMapper;
    private final GroupHierarchyMapper groupHierarchyMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<GroupVO> getGroupList(GroupQuery query, Pageable pageable) {
        Page<GroupVO> groupList = groupMapper.findGroupList(query, pageable);
        groupList.forEach(groupVO -> {
            List<String> parentIds = groupHierarchyMapper
                    .findGroupHierarchyListByParentAndChild(new GroupHierarchyVO(null, groupVO.getGroupId()))
                    .stream()
                    .map(GroupHierarchyVO::getParentGroupId)
                    .collect(Collectors.toList());
            groupVO.setParentGroupIds(parentIds);
        });
        return groupList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<GroupVO> getGroupListWithId(String groupId) {
        List<GroupVO> groupList = groupMapper.findGroupListWithId(groupId);
        groupList.forEach(groupVO -> {
            List<String> parentIds = groupHierarchyMapper
                    .findGroupHierarchyListByParentAndChild(new GroupHierarchyVO(null, groupVO.getGroupId()))
                    .stream()
                    .map(GroupHierarchyVO::getParentGroupId)
                    .collect(Collectors.toList());
            groupVO.setParentGroupIds(parentIds);
        });
        return groupList;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GroupExcelVO> getGroupListExcel(GroupQuery query, Pageable pageable) {
        return groupMapper.findGroupListExcel(query, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public GroupVO getGroup(String groupId) {
        GroupVO groupVO = groupMapper.findGroup(groupId)
                .orElseThrow(() -> new NoSuchElementException("그룹이 존재하지 않습니다."));
        List<String> parentIds = groupHierarchyMapper
                .findGroupHierarchyListByParentAndChild(new GroupHierarchyVO(null, groupId))
                .stream()
                .map(GroupHierarchyVO::getParentGroupId)
                .collect(Collectors.toList());
        groupVO.setParentGroupIds(parentIds);
        return groupVO;
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean getGroupCheck(String groupId) {
        return !groupMapper.findGroup(groupId).isPresent();
    }

    @Override
    public void createGroup(GroupVO command) {
        command.onCreate();
        groupMapper.insertGroup(command);
    }

    @Override
    public void createGroup(List<GroupExcelVO> list) {
        list.forEach(TimeAuthorLog::onCreate);
        groupMapper.insertGroup(list, new BatchRequest(1000));
    }

    @Override
    public void updateGroup(GroupVO command) {
        command.onUpdate();
        groupMapper.updateGroup(command);
    }

    @Override
    public void deleteGroup(List<GroupVO> commands) {
        List<String> deleteIds = commands.stream()
                .map(GroupVO::getGroupId)
                .collect(Collectors.toList());
        groupUserMapper.deleteGroupUserByGroupId(deleteIds, new BatchRequest(1000));
        groupHierarchyMapper.deleteAllGroupHierarchy(deleteIds, new BatchRequest(1000));
        groupMapper.deleteGroup(deleteIds, new BatchRequest(1000));
    }
}
