package net.lotte.chamomile.admin.authresource.service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import net.lotte.chamomile.admin.authhierarchy.domain.AuthHierarchyMapper;
import net.lotte.chamomile.admin.authresource.api.dto.AuthResourceCommand;
import net.lotte.chamomile.admin.authresource.api.dto.AuthResourceQuery;
import net.lotte.chamomile.admin.authresource.api.dto.AuthResourceResponse;
import net.lotte.chamomile.admin.authresource.api.dto.ResourceRoleResponse;
import net.lotte.chamomile.admin.authresource.domain.AuthResourceExcelVO;
import net.lotte.chamomile.admin.authresource.domain.AuthResourceMapper;
import net.lotte.chamomile.admin.authresource.service.AuthResourceService;
import net.lotte.chamomile.admin.authtree.AuthTreeVO;
import net.lotte.chamomile.admin.authtree.RoleHierarchyTree;
import net.lotte.chamomile.admin.authtree.RoleHierarchyVO;
import net.lotte.chamomile.admin.resource.api.dto.ResourceQuery;
import net.lotte.chamomile.admin.resource.domain.ResourceMapper;
import net.lotte.chamomile.admin.resource.domain.ResourceVO;
import net.lotte.chamomile.module.database.mybatis.batch.BatchRequest;

/**
 * <pre>
 * 리소스 권한 서비스 구현 객체.
 * </pre>
 *
 * @author MoonHKLee
 * @version 3.0
 * @Modification <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-11-09     MoonHKLee            최초 생성
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 * @since 2023-11-09
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthResourceServiceImpl implements AuthResourceService {

    private final AuthResourceMapper authResourceMapper;

    private final AuthHierarchyMapper authHierarchyMapper;

    private final ResourceMapper resourceMapper;

    @Override
    @Transactional(readOnly = true)
    public AuthResourceResponse getAuthResource(AuthResourceQuery query) {
        query.setSearchResourceId(query.getResourceId());
        ResourceVO resourceVO = resourceMapper.findResource(query.getResourceId())
                .orElseThrow(() -> new NoSuchElementException("해당 리소스가 존재하지 않습니다.{" + query.getResourceId() + "}"));
        List<RoleHierarchyVO> allHierarchyRole = authHierarchyMapper.findAllHierarchyRole();
        RoleHierarchyTree tree = new RoleHierarchyTree(allHierarchyRole);
        tree.addSingleRole(authHierarchyMapper.findSingleRoleList());
        List<AuthTreeVO> left = authResourceMapper.findUnUsedResourceList(query);
        List<AuthTreeVO> right = authResourceMapper.findUsedResourceList(query);
        if (!StringUtils.hasText(query.getShowEmptyTreeYn())) {
            fillTree(query, tree);
            return new AuthResourceResponse(resourceVO, left, right, tree);
        }
        if (!query.getShowEmptyTreeYn().equals("1")) {
            fillTree(query, tree);
        }
        return new AuthResourceResponse(resourceVO, left, right, tree);
    }

    private void fillTree(AuthResourceQuery query, RoleHierarchyTree tree) {
        List<AuthTreeVO> rightlist = authResourceMapper.findUsedResourceList(query);
        tree.mapToRightTreeDataReverse(rightlist);
    }

    @Override
    public void updateAuthResource(AuthResourceCommand command) {
        resourceMapper.findResource(command.getResourceId())
                .orElseThrow(() -> new NoSuchElementException("해당 리소스가 존재하지 않습니다.{" + command.getResourceId() + "}"));

        authResourceMapper.deleteAuthResource(Collections.singletonList(command.getResourceId()));
        command.getRightValue().stream()
                .map(v -> v.toEntity(command.getResourceId()))
                .forEach(v -> {
                    v.onCreate();
                    authResourceMapper.insertAuthResource(v);
                });
    }

    @Override
    public void createAuthGroup(List<AuthResourceExcelVO> list) {
        list.forEach(AuthResourceExcelVO::onCreate);
        authResourceMapper.insertAuthResourceExcel(list, new BatchRequest(1000));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuthResourceExcelVO> getAuthResourceListExcel(ResourceQuery request, Pageable pageable) {
        return authResourceMapper.findAuthResourceListExcel(request, pageable);
    }

    @Override
    public Page<ResourceRoleResponse> getAuthResourceList(ResourceQuery request, Pageable pageable) {
        Page<ResourceRoleResponse> resourceListData = authResourceMapper.findResourceRoleList(request, pageable);
        resourceListData.forEach(v -> v.setRoleIdList(authResourceMapper.findRolesByResourceId(v.getResourceId())));
        return resourceListData;
    }
}
