package net.lotte.chamomile.admin.authhierarchy.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.lotte.chamomile.admin.auth.domain.AuthVO;
import net.lotte.chamomile.admin.auth.service.AuthService;
import net.lotte.chamomile.admin.authhierarchy.api.dto.AuthHierarchyQuery;
import net.lotte.chamomile.admin.authhierarchy.domain.AuthHierarchyExcelUploadVO;
import net.lotte.chamomile.admin.authhierarchy.domain.AuthHierarchyMapper;
import net.lotte.chamomile.admin.authhierarchy.domain.AuthHierarchyVO;
import net.lotte.chamomile.admin.authtree.RoleHierarchyTree;
import net.lotte.chamomile.admin.authtree.RoleHierarchyVO;
import net.lotte.chamomile.admin.common.exception.code.AdminExceptionCode;
import net.lotte.chamomile.core.exception.BusinessException;
import net.lotte.chamomile.module.database.mybatis.batch.BatchRequest;
import net.lotte.chamomile.module.security.SecurityService;

/**
 * <pre>
 * 권한 상하관계 관련 서비스 인터페이스 구현체.
 * </pre>
 *
 * @author TaehoPark
 * @since 2023-10-06
 * @version 3.0
 * @Modification
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-10-06     TaehoPark            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthHierarchyServiceImpl implements AuthHierarchyService {

    private final AuthHierarchyMapper authHierarchyMapper;
    private final AuthService authService;
    private final SecurityService securityService;


    @Override
    @Transactional(readOnly = true)
    public RoleHierarchyTree getAuthHierarchyTree(AuthHierarchyQuery authHierarchyQuery) {
        List<RoleHierarchyVO> allHierarchyRole = authHierarchyMapper.findHierarchyRoleList(authHierarchyQuery);
        RoleHierarchyTree tree = new RoleHierarchyTree(allHierarchyRole);
        return tree;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuthHierarchyVO> getAuthHierarchyList(AuthHierarchyQuery authHierarchyQuery, Pageable pageable) {
        return authHierarchyMapper.findAuthHierarchyListData(authHierarchyQuery, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthHierarchyVO> getAuthHierarchyList(AuthHierarchyQuery authHierarchyQuery) {
        return authHierarchyMapper.findAuthHierarchyListData(authHierarchyQuery);
    }
    @Override
    @Transactional(readOnly = true)
    public List<AuthHierarchyVO> getAuthHierarchyDetailList(AuthHierarchyQuery authHierarchyQuery) {
        List<AuthHierarchyVO> list = authHierarchyMapper.findAuthHierarchyListData(authHierarchyQuery);
        Set<AuthHierarchyVO> setList = new HashSet<>();

        Queue<String> queue = new LinkedList<>();
        queue.add(authHierarchyQuery.getSearchRoleId());
        int orderNo = 1; // 정렬순서 기록

        while (!queue.isEmpty()) {
            int qSize = queue.size();
            for (int i = 0; i < qSize; i++) {
                String currentRoleId = queue.poll();
                for (AuthHierarchyVO nextVo : list) { // vo를 하나씩 꺼낸다
                    String nextParentRoleId = nextVo.getParentRoleId();
                    String nextChildRoleId = nextVo.getChildRoleId();
                    if (!nextParentRoleId.equals(currentRoleId)) continue; // next vo의 parent가 현재vo일 경우 넘긴다
                    if (setList.contains(nextVo)) continue; // 이미 방문한 vo인 경우 넘긴다
                    queue.add(nextChildRoleId);
                    nextVo.setOrder(orderNo);
                    setList.add(nextVo);
                }
            }
            orderNo++; // 정렬순서 1 증가
        }

        List<AuthHierarchyVO> returnList = new ArrayList<>(setList);

        // order, parent id, child id 순으로 정렬
        returnList.sort((AuthHierarchyVO o1, AuthHierarchyVO o2) -> {
            int orderCompare = Integer.compare(o1.getOrder(), o2.getOrder());
            if (orderCompare != 0) return orderCompare;
            int parentCompare = o1.getParentRoleId().compareTo(o2.getParentRoleId());
            if (parentCompare != 0) return parentCompare;
            return o1.getChildRoleId().compareTo(o2.getChildRoleId());
        });

        return returnList;
    }

    @Override
    public void createAuthHierarchy(List<AuthHierarchyVO> list) {
        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException(AdminExceptionCode.DataNotFoundError);
        }

        AuthHierarchyQuery authHierarchyQuery = new AuthHierarchyQuery();
        AuthVO authVo = new AuthVO();
        BatchRequest batchRequest = new BatchRequest(1000);

        for (AuthHierarchyVO vo : list) {
            authHierarchyQuery.setSearchParentRoleId(vo.getParentRoleId());
            authHierarchyQuery.setSearchChildRoleId(vo.getChildRoleId());

            boolean isCycle = cycleCheck(vo, authHierarchyMapper.findAuthHierarchyListData(authHierarchyQuery));
            if (isCycle) {
                throw new BusinessException(AdminExceptionCode.DataNotFoundError);
            }


            authVo.setRoleId(vo.getParentRoleId());
            int parentCheck = authService.authIdCheck(authVo);
            if (parentCheck != 1) {
                throw new BusinessException(AdminExceptionCode.DataNotFoundError);
            }

            authVo.setRoleId(vo.getChildRoleId());
            int childCheck = authService.authIdCheck(authVo);
            if (childCheck != 1) {
                throw new BusinessException(AdminExceptionCode.DataNotFoundError);
            }

        }

        authHierarchyMapper.insertAuthHierarchy(list, batchRequest);
        securityService.reloadRoleHierarchy();
    }

    @Override
    public void deleteAuthHierarchy(List<AuthHierarchyVO> list) {

        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException(AdminExceptionCode.DataNotFoundError);
        }
        BatchRequest batchRequest = new BatchRequest(1000);

        securityService.reloadRoleHierarchy();
        if (authHierarchyMapper.deleteAuthHierarchy(list, batchRequest) == 0){
            throw new BusinessException(AdminExceptionCode.DataNotFoundError);
        }
    }

    public boolean cycleCheck(AuthHierarchyVO authHierarchyVO, List<AuthHierarchyVO> allList) {

        String parentRoleId = authHierarchyVO.getParentRoleId();
        String childRoleId = authHierarchyVO.getChildRoleId();

        Set<String> roleIdSet = new HashSet<String>();
        roleIdSet.add(childRoleId);

        boolean isSuccess = true;

        Queue<String> queue = new LinkedList<String>();
        queue.add(childRoleId);

        while (!queue.isEmpty()) {
            String currentGroupId = queue.poll();
            if (currentGroupId.equals(parentRoleId)) { /* cycle */
                return true;
            }

            Iterator<AuthHierarchyVO> it = allList.iterator(); // vo를 하나씩 꺼낸다
            while (it.hasNext() && isSuccess) {
                AuthHierarchyVO nextVo = it.next();
                String nextParentRoleId = nextVo.getParentRoleId();
                String nextChildRoleId = nextVo.getChildRoleId();

                if (parentRoleId.equals(nextParentRoleId) && childRoleId.equals(nextChildRoleId)) { /* cycle */
                    return true;
                }

                if (!nextParentRoleId.equals(currentGroupId)) continue; // next vo의 parent가 현재 vo인 경우 넘긴다
                if (roleIdSet.contains(nextChildRoleId)) continue; // 방문했다면 넘긴다

                queue.add(nextChildRoleId);
                roleIdSet.add(nextChildRoleId);
            }
        }
        return false;
    }

    @Override
    public void createExcelAuthHierarchy(List<AuthHierarchyExcelUploadVO> list) {

        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException(AdminExceptionCode.DataNotFoundError);
        }

        List<AuthHierarchyVO> authList = list.stream()
                .map(vo -> {
                    AuthHierarchyVO authVO = new AuthHierarchyVO();
                    BeanUtils.copyProperties(vo, authVO);
                    authVO.onCreate();
                    authVO.onUpdate();
                    return authVO;
                })
                .collect(Collectors.toList());
        createAuthHierarchy(authList);
    }
}
