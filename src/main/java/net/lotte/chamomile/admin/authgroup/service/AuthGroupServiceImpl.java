package net.lotte.chamomile.admin.authgroup.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import net.lotte.chamomile.admin.authgroup.api.dto.AuthGroupQuery;
import net.lotte.chamomile.admin.authgroup.domain.AuthGroupExcelVO;
import net.lotte.chamomile.admin.authgroup.domain.AuthGroupMapper;
import net.lotte.chamomile.admin.authgroup.domain.AuthGroupVO;
import net.lotte.chamomile.admin.authgroup.service.AuthGroupService;
import net.lotte.chamomile.admin.authhierarchy.domain.AuthHierarchyMapper;
import net.lotte.chamomile.admin.authtree.AuthTreeVO;
import net.lotte.chamomile.admin.authtree.RoleHierarchyTree;
import net.lotte.chamomile.admin.authtree.RoleHierarchyVO;
import net.lotte.chamomile.module.database.audit.TimeAuthorLog;
import net.lotte.chamomile.module.database.mybatis.batch.BatchRequest;

/**
 * <pre>
 * 권한 그룹 서비스 구현체 클래스.
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
public class AuthGroupServiceImpl implements AuthGroupService {
    private final AuthGroupMapper authGroupMapper;
    private final AuthHierarchyMapper authHierarchyMapper;

    @Override
    @Transactional(readOnly = true)
    public RoleHierarchyTree getAuthGroup(AuthGroupQuery query) {
        List<RoleHierarchyVO> allHierarchyRole = authHierarchyMapper.findAllHierarchyRole();
        RoleHierarchyTree tree = new RoleHierarchyTree(allHierarchyRole);
        tree.addSingleRole(authHierarchyMapper.findSingleRoleList());
        if (!StringUtils.hasText(query.getShowEmptyTreeYn())) {
            List<AuthTreeVO> rightlist = authGroupMapper.findAuthGroupList(query.getGroupId());
            tree.mapToRightTreeData(rightlist);
            return tree;
        }
        if (!query.getShowEmptyTreeYn().equals("1")) {
            List<AuthTreeVO> rightlist = authGroupMapper.findAuthGroupList(query.getGroupId());
            tree.mapToRightTreeData(rightlist);
        }
        return tree;
    }

    @Override
    public void updateAuthGroup(String groupId, List<String> roleIds) {
        authGroupMapper.deleteAuthGroup(groupId);
        roleIds.forEach(v-> {
            AuthGroupVO authGroupVO = new AuthGroupVO(groupId, v);
            authGroupVO.onCreate();
            authGroupMapper.insertAuthGroup(authGroupVO);
        });
    }

    @Override
    public void createAuthGroup(List<AuthGroupExcelVO> list) {
        list.forEach(TimeAuthorLog::onCreate);
        authGroupMapper.insertAuthGroupExcel(list,new BatchRequest(1000));
    }
}
