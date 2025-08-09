package net.lotte.chamomile.admin.grouphierarchy.service;

import java.util.ArrayList;
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
import net.lotte.chamomile.admin.grouphierarchy.domain.GroupHierarchyExcelVO;
import net.lotte.chamomile.admin.grouphierarchy.domain.GroupHierarchyMapper;
import net.lotte.chamomile.admin.grouphierarchy.domain.GroupHierarchyVO;
import net.lotte.chamomile.module.database.mybatis.batch.BatchRequest;

/**
 * <pre>
 * 그룹 상하관계 구현 객체.
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
public class GroupHierarchyServiceImpl implements GroupHierarchyService {
    private final GroupMapper groupMapper;
    private final GroupHierarchyMapper groupHierarchyMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<GroupHierarchyVO> getGroupHierarchyList(Pageable pageable) {
        return groupHierarchyMapper.findGroupHierarchyListData(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GroupHierarchyExcelVO> getGroupHierarchyListExcel(Pageable pageable) {
        return groupHierarchyMapper.findGroupHierarchyListDataExcel(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupHierarchyVO> getGroupHierarchy(String groupId) {
        Set<GroupHierarchyVO> setList = getNonDuplicatedList(groupId);
        List<GroupHierarchyVO> returnList = new ArrayList<>(setList);
        sort(returnList);
        return returnList;
    }

    @Override
    public void createGroupHierarchy(GroupHierarchyVO command) {
        command.cycleCheck(groupHierarchyMapper.findGroupHierarchyList(""));
        groupMapper.findGroup(command.getParentGroupId())
                .orElseThrow(() -> new NoSuchElementException("부모 그룹이 존재하지 않습니다."));
        groupMapper.findGroup(command.getChildGroupId())
                .orElseThrow(() -> new NoSuchElementException("자식 그룹이 존재하지 않습니다."));
        groupHierarchyMapper.insertGroupHierarchy(command);
    }

    @Override
    public void createGroupHierarchy(List<GroupHierarchyExcelVO> command) {
        groupHierarchyMapper.insertGroupHierarchyExcel(command, new BatchRequest(1000));
    }

    @Override
    public void deleteGroupHierarchy(List<GroupHierarchyVO> command) {
        command.forEach(v-> {
            if(groupHierarchyMapper.findGroupHierarchyListByParentAndChild(v).isEmpty()) {
                throw new RuntimeException("그룹 상하관계가 존재하지 않습니다.");
            };
        });
        groupHierarchyMapper.deleteGroupHierarchy(command, new BatchRequest(1000));
    }

    private Set<GroupHierarchyVO> getNonDuplicatedList(String groupId) {
        groupMapper.findGroup(groupId)
                .orElseThrow(() -> new NoSuchElementException("그룹이 존재하지 않습니다."));
        List<GroupHierarchyVO> list = groupHierarchyMapper.findAllGroupHierarchyList();
        Set<GroupHierarchyVO> setList = new HashSet<>();

        Queue<String> queue = new LinkedList<>();
        queue.add(groupId);

        int orderNo = 1; // 정렬순서 기록
        while (!queue.isEmpty()) {
            for (int i = 0; i < queue.size(); i++) {
                String currentGroupId = queue.poll();
                for (GroupHierarchyVO nextVo : list) {  // vo를 하나씩 꺼낸다
                    String nextParentGroupId = nextVo.getParentGroupId();
                    String nextChildGroupId = nextVo.getChildGroupId();
                    if (!nextParentGroupId.equals(currentGroupId)) continue; // next vo의 parent가 현재vo일 경우 넘긴다
                    if (setList.contains(nextVo)) continue; // 이미 방문한 vo인 경우 넘긴다
                    queue.add(nextChildGroupId);
                    nextVo.setOrder(orderNo);
                    setList.add(nextVo);
                }
            }
            orderNo++; // 정렬순서 1 증가
        }
        return setList;
    }

    // order, parent id, child id 순으로 정렬
    private static void sort(List<GroupHierarchyVO> returnList) {
        returnList.sort((GroupHierarchyVO o1, GroupHierarchyVO o2) -> {
            int orderCompare = Integer.compare(o1.getOrder(), o2.getOrder());
            if (orderCompare != 0) return orderCompare;
            int parentCompare = o1.getParentGroupId().compareTo(o2.getParentGroupId());
            if (parentCompare != 0) return parentCompare;
            return o1.getChildGroupId().compareTo(o2.getChildGroupId());
        });
    }
}
