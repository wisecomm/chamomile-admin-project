package net.lotte.chamomile.admin.authresource.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.lotte.chamomile.admin.authresource.api.dto.AuthResourceCommand;
import net.lotte.chamomile.admin.authresource.api.dto.AuthResourceQuery;
import net.lotte.chamomile.admin.authresource.api.dto.AuthResourceResponse;
import net.lotte.chamomile.admin.authresource.api.dto.ResourceRoleResponse;
import net.lotte.chamomile.admin.authresource.domain.AuthResourceExcelVO;
import net.lotte.chamomile.admin.resource.api.dto.ResourceQuery;

/**
 * <pre>
 * 리소스 권한 서비스 인터페이스.
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
public interface AuthResourceService {
    AuthResourceResponse getAuthResource(AuthResourceQuery query);

    void updateAuthResource(AuthResourceCommand command);

    void createAuthGroup(List<AuthResourceExcelVO> list);

    Page<AuthResourceExcelVO> getAuthResourceListExcel(ResourceQuery request, Pageable pageable);

    Page<ResourceRoleResponse> getAuthResourceList(ResourceQuery request, Pageable pageable);
}
