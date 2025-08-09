package net.lotte.chamomile.admin.authresource.domain;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.lotte.chamomile.admin.authresource.api.dto.AuthResourceQuery;
import net.lotte.chamomile.admin.authresource.api.dto.ResourceRoleResponse;
import net.lotte.chamomile.admin.authtree.AuthTreeVO;
import net.lotte.chamomile.admin.resource.api.dto.ResourceQuery;
import net.lotte.chamomile.admin.resource.domain.ResourceVO;
import net.lotte.chamomile.module.database.mybatis.batch.BatchRequest;

/**
 * <pre>
 * 리소스 권한 Mapper.
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
public interface AuthResourceMapper {
    List<AuthTreeVO> findUsedResourceList(AuthResourceQuery query);

    List<AuthTreeVO> findUnUsedResourceList(AuthResourceQuery query);
    Integer countAuthResourceByRoleId(String roleId);

    void deleteAuthResource(List<String> deleteIds);

    void insertAuthResource(AuthResourceVO command);
    void insertAuthResourceExcel(List<AuthResourceExcelVO> command, BatchRequest batchRequest);

    Page<AuthResourceExcelVO> findAuthResourceListExcel(ResourceQuery query, Pageable pageable);
    Page<ResourceRoleResponse> findResourceRoleList(ResourceQuery query, Pageable pageable);
    List<String> findRolesByResourceId(String resourceId);
}
