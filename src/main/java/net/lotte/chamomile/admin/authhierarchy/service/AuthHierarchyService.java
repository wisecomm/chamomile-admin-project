package net.lotte.chamomile.admin.authhierarchy.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.lotte.chamomile.admin.authhierarchy.api.dto.AuthHierarchyQuery;
import net.lotte.chamomile.admin.authhierarchy.domain.AuthHierarchyExcelUploadVO;
import net.lotte.chamomile.admin.authhierarchy.domain.AuthHierarchyVO;
import net.lotte.chamomile.admin.authtree.RoleHierarchyTree;

/**
 * <pre>
 * 권한 상하관계 관련 서비스 인터페이스.
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
public interface AuthHierarchyService {

    RoleHierarchyTree getAuthHierarchyTree(AuthHierarchyQuery authHierarchyQuery);

    Page<AuthHierarchyVO> getAuthHierarchyList(AuthHierarchyQuery authHierarchyQuery, Pageable pageable);

    List<AuthHierarchyVO> getAuthHierarchyList(AuthHierarchyQuery authHierarchyQuery);

    void createAuthHierarchy(List<AuthHierarchyVO> list);

    void deleteAuthHierarchy(List<AuthHierarchyVO> list);

    List<AuthHierarchyVO> getAuthHierarchyDetailList(AuthHierarchyQuery authHierarchyQuery);

    void createExcelAuthHierarchy(List<AuthHierarchyExcelUploadVO> list);
}
