package net.lotte.chamomile.admin.authuser.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import net.lotte.chamomile.admin.authhierarchy.domain.AuthHierarchyMapper;
import net.lotte.chamomile.admin.authtree.AuthTreeVO;
import net.lotte.chamomile.admin.authtree.RoleHierarchyTree;
import net.lotte.chamomile.admin.authtree.RoleHierarchyVO;
import net.lotte.chamomile.admin.authuser.api.dto.AuthUserQuery;
import net.lotte.chamomile.admin.authuser.domain.AuthUserExcelUploadVO;
import net.lotte.chamomile.admin.authuser.domain.AuthUserMapper;
import net.lotte.chamomile.admin.authuser.domain.AuthUserVO;
import net.lotte.chamomile.module.database.audit.TimeAuthorLog;
import net.lotte.chamomile.module.database.mybatis.batch.BatchRequest;

/**
 * <pre>
 * 유저 권한 서비스 구현 객체.
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
public class AuthUserServiceImpl implements AuthUserService {

    private final AuthUserMapper authUserMapper;

    private final AuthHierarchyMapper authHierarchyMapper;

    @Override
    @Transactional(readOnly = true)
    public RoleHierarchyTree getAuthUser(AuthUserQuery query) {
        List<RoleHierarchyVO> allHierarchyRole = authHierarchyMapper.findAllHierarchyRole();
        RoleHierarchyTree tree = new RoleHierarchyTree(allHierarchyRole);
        tree.addSingleRole(authHierarchyMapper.findSingleRoleList());
        // List<AuthTreeVO> leftList = authUserMapper.findAuthUserUnMappedList(query.getUserId());
        // tree.mapToLeftTreeData(leftList);
        if (!StringUtils.hasText(query.getShowEmptyTreeYn())) {
            List<AuthTreeVO> userRoleList = authUserMapper.findAuthUser(query.getUserId());
            tree.mapToRightTreeData(userRoleList);
            List<AuthTreeVO> groupRoleList = authUserMapper.findAuthUserGroup(query.getUserId());
            tree.mapToRightGroupData(groupRoleList);
            return tree;
        }
        if (!query.getShowEmptyTreeYn().equals("1")) {
            List<AuthTreeVO> userRoleList = authUserMapper.findAuthUser(query.getUserId());
            tree.mapToRightTreeData(userRoleList);
        }
        List<AuthTreeVO> groupRoleList = authUserMapper.findAuthUserGroup(query.getUserId());
        tree.mapToRightGroupData(groupRoleList);

        return tree;
    }

    @Override
    public void updateAuthUser(String userId, List<String> roleIds) {
        authUserMapper.deleteAuthUser(AuthUserVO.builder().userId(userId).build());
        List<AuthUserVO> commands = roleIds.stream()
                .map(v -> {
                    AuthUserVO authUserVO = new AuthUserVO(userId, v, "1");
                    authUserVO.onCreate();
                    return authUserVO;
                })
                .collect(Collectors.toList());
        authUserMapper.insertAuthUser(commands, new BatchRequest(1000));
    }

    @Override
    public void createAuthUser(List<AuthUserExcelUploadVO> list) {
        list.forEach(TimeAuthorLog::onCreate);
        authUserMapper.insertAuthUserExcel(list, new BatchRequest(1000));
    }
}
