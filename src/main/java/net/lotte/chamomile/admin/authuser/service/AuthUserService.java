package net.lotte.chamomile.admin.authuser.service;

import java.util.List;

import net.lotte.chamomile.admin.authtree.RoleHierarchyTree;
import net.lotte.chamomile.admin.authuser.api.dto.AuthUserQuery;
import net.lotte.chamomile.admin.authuser.domain.AuthUserExcelUploadVO;

/**
 * <pre>
 * 유저 권한 서비스 인터페이스.
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
public interface AuthUserService {
    RoleHierarchyTree getAuthUser(AuthUserQuery query);
    void updateAuthUser(String userId, List<String> roleIds);

    void createAuthUser(List<AuthUserExcelUploadVO> list);
}
