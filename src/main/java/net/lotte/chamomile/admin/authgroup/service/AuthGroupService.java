package net.lotte.chamomile.admin.authgroup.service;

import java.util.List;

import net.lotte.chamomile.admin.authgroup.api.dto.AuthGroupQuery;
import net.lotte.chamomile.admin.authgroup.domain.AuthGroupExcelVO;
import net.lotte.chamomile.admin.authtree.RoleHierarchyTree;

/**
 * <pre>
 * 권한 그룹 서비스 인터페이스.
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
public interface AuthGroupService {
    RoleHierarchyTree getAuthGroup(AuthGroupQuery query);

    void updateAuthGroup(String groupId, List<String> roleIds);

    void createAuthGroup(List<AuthGroupExcelVO> list);
}
