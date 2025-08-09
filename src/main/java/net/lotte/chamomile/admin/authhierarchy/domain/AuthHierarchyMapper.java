package net.lotte.chamomile.admin.authhierarchy.domain;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.lotte.chamomile.admin.authhierarchy.api.dto.AuthHierarchyQuery;
import net.lotte.chamomile.admin.authtree.RoleHierarchyVO;
import net.lotte.chamomile.admin.menuauth.api.dto.MenuAuthQuery;
import net.lotte.chamomile.module.database.mybatis.batch.BatchRequest;

/**
 * <pre>
 * 권한상하 관계 관련 마이바티스 매핑 인터페이스.
 * </pre>
 *
 * @author TaehoPark
 * @since 2023-10-05
 * @version 3.0
 * @Modification
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-10-05     TaehoPark            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@Mapper
public interface AuthHierarchyMapper {
    Page<AuthHierarchyVO> findAuthHierarchyListData(AuthHierarchyQuery query, Pageable pageable);
    List<AuthHierarchyVO> findAuthHierarchyListData(AuthHierarchyQuery query);
    Optional<AuthHierarchyVO> findAuthHierarchyDetail(AuthHierarchyQuery authHierarchyQuery);
    void insertAuthHierarchy(List<AuthHierarchyVO> authHierarchyVO, BatchRequest batchRequest);
    int deleteAuthHierarchy(List<AuthHierarchyVO> authHierarchyVO, BatchRequest batchRequest);

    List<RoleHierarchyVO> findHierarchyRoles(MenuAuthQuery request);
    List<RoleHierarchyVO> findAllHierarchyRole();
    List<RoleHierarchyVO> findSingleRoleList();

    List<RoleHierarchyVO> findHierarchyRoleList(AuthHierarchyQuery query);
}
