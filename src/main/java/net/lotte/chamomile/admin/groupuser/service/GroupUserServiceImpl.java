package net.lotte.chamomile.admin.groupuser.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.lotte.chamomile.admin.group.domain.GroupMapper;
import net.lotte.chamomile.admin.group.domain.GroupVO;
import net.lotte.chamomile.admin.grouphierarchy.domain.GroupHierarchyMapper;
import net.lotte.chamomile.admin.grouphierarchy.domain.GroupHierarchyVO;
import net.lotte.chamomile.admin.groupuser.api.dto.GroupUserResponse;
import net.lotte.chamomile.admin.groupuser.domain.GroupUserExcelVO;
import net.lotte.chamomile.admin.groupuser.domain.GroupUserMapper;
import net.lotte.chamomile.admin.groupuser.domain.GroupUserVO;
import net.lotte.chamomile.admin.groupuser.service.GroupUserService;
import net.lotte.chamomile.admin.user.api.dto.UserQuery;
import net.lotte.chamomile.admin.user.domain.UserMapper;
import net.lotte.chamomile.module.database.audit.TimeAuthorLog;
import net.lotte.chamomile.module.database.mybatis.batch.BatchRequest;

/**
 * <pre>
 * 그룹 유저 서비스 구현 객체.
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
public class GroupUserServiceImpl implements GroupUserService {

    private final GroupUserMapper groupUserMapper;

    private final GroupHierarchyMapper groupHierarchyMapper;

    private final UserMapper userMapper;

    private final GroupMapper groupMapper;

    @Override
    @Transactional(readOnly = true)
    public GroupUserResponse getGroupUser(String groupId) {
        GroupUserResponse groupUserResponse = new GroupUserResponse();

        List<GroupUserVO> mappedGroupUser = getMappedGroupUser(GroupVO.id(groupId));

        groupUserResponse.addRightGroupUserList(mappedGroupUser);
        return groupUserResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public GroupUserResponse getGroupUserByUserId(String userId) {
        GroupUserResponse groupUserResponse = new GroupUserResponse();

        List<GroupUserVO> mappedGroupUser = groupUserMapper.findGroupUserListByUserId(userId);

        groupUserResponse.setReturnList(mappedGroupUser);
        return groupUserResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GroupUserExcelVO> getGroupUserList(Pageable pageable) {
        return groupUserMapper.findGroupUserListExcel(pageable);
    }

    @Override
    public void updateGroupUser(String groupId, List<String> userIds) {
        groupUserMapper.deleteGroupUserByGroupId(Collections.singletonList(groupId), new BatchRequest(1000));

        userIds.forEach(v -> {
            UserQuery query = new UserQuery();
            query.setUserId(v);
            userMapper.findUserDetail(query)
                    .orElseThrow(() -> new NoSuchElementException("해당 유저가 존재하지 않습니다."));
            groupMapper.findGroup(groupId)
                    .orElseThrow(() -> new NoSuchElementException("해당 그룹이 존재하지 않습니다."));
            GroupUserVO groupUserVO = new GroupUserVO(groupId, v);
            groupUserVO.onCreate();
            groupUserMapper.insertGroupUser(groupUserVO);
        });
    }

    @Override
    public void updateGroupUserByUserId(String userId, List<String> groupIds) {
        groupUserMapper.deleteGroupUserByUserId(Collections.singletonList(userId),
                new BatchRequest(1000));

        groupIds.forEach(v -> {
            UserQuery query = new UserQuery();
            query.setUserId(userId);
            userMapper.findUserDetail(query)
                    .orElseThrow(() -> new NoSuchElementException("해당 유저가 존재하지 않습니다."));
            groupMapper.findGroup(v)
                    .orElseThrow(() -> new NoSuchElementException("해당 그룹이 존재하지 않습니다."));
            GroupUserVO groupUserVO = new GroupUserVO(v, userId);
            groupUserVO.onCreate();
            groupUserMapper.insertGroupUser(groupUserVO);
        });
    }

    @Override
    public void createGroupUser(List<GroupUserExcelVO> list) {
        list.forEach(TimeAuthorLog::onCreate);
        groupUserMapper.insertGroupUserExcel(list,new BatchRequest(1000));
    }

    /** 모든 하위그룹 가져오기 **/
    private Set<GroupVO> getAllChildGroups(GroupVO groupVo) {

        log.debug("Start GroupID : {}", groupVo.getGroupId());

        List<GroupHierarchyVO> list = groupHierarchyMapper.findGroupHierarchyList("");

        Set<GroupHierarchyVO> setList = new HashSet<>();
        Set<GroupVO> groupVoSet = new HashSet<>();

        groupVoSet.add(groupVo);
        Queue<GroupVO> groupVoQueue = new LinkedList<>();
        groupVoQueue.add(groupVo);

        while (!groupVoQueue.isEmpty()) {
            for (int i = 0; i < groupVoQueue.size(); i++) {
                GroupVO curGroupVo = groupVoQueue.poll();
                String curGroupId = curGroupVo.getGroupId();

                for (GroupHierarchyVO nextVo : list) {
                    String nextParentGroupId = nextVo.getParentGroupId();
                    String nextChildGroupId = nextVo.getChildGroupId();

                    if (!nextParentGroupId.equals(curGroupId)) continue; // next vo의 parent가 현재 vo인 경우 넘긴다
                    if (setList.contains(nextVo)) continue; // 방문했다면 넘긴다

                    GroupVO parentGroupVo = new GroupVO();
                    parentGroupVo.setGroupId(nextParentGroupId);

                    GroupVO childGroupVo = new GroupVO();
                    childGroupVo.setGroupId(nextChildGroupId);

                    groupVoQueue.add(childGroupVo);
                    setList.add(nextVo);
                    groupVoSet.add(parentGroupVo);
                    groupVoSet.add(childGroupVo);
                }
            }
        }

        log.debug("getAllChildGroups", groupVoSet);

        return groupVoSet;
    }

    private List<GroupUserVO> getMappedGroupUser(GroupVO groupVO) {
        Set<GroupVO> childGroups = getAllChildGroups(groupVO);
        Set<GroupUserVO> userGroupMapVoSet = new HashSet<>();
        for (GroupVO tempGroupVo : childGroups) {
            userGroupMapVoSet.addAll(groupUserMapper.findGroupUserList(tempGroupVo.getGroupId()));
        }

        List<GroupUserVO> rightUserGroup = new ArrayList<>(userGroupMapVoSet);
        Collections.sort(rightUserGroup);
        return rightUserGroup;
    }

	@Override
	@Transactional(readOnly = true)
	public Page<GroupUserVO> getUnMappedUser(String groupId, Pageable pageable) {
        Page<GroupUserVO> unMappedGroupUser = groupUserMapper.findUnMappedUserList(groupId, pageable);
        return unMappedGroupUser;
	}
}
