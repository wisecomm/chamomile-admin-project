package net.lotte.chamomile.admin.auth.domain;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.lotte.chamomile.admin.auth.api.dto.AuthQuery;
import net.lotte.chamomile.module.database.mybatis.batch.BatchRequest;

/**
 * <pre>
 * 권한 관련 마이바티스 매핑 인터페이스.
 * </pre>
 *
 * @author TaehoPark
 * @since 2023-09-26
 * @version 3.0
 * @Modification
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-09-26     TaehoPark            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@Mapper
public interface AuthMapper {
    Page<AuthVO> findAuthListDataPage(AuthQuery query, Pageable pageable);
    List<AuthVO> findAuthListData(AuthQuery query);
    Optional<AuthVO> findAuthDetail(AuthQuery resourceId);
    int insertAuth(AuthVO authVO);
    int updateAuth(AuthVO authVO);
    int authIdCheck(AuthVO authVO);
    int deleteAuth(List<AuthVO> authVOList, BatchRequest batchRequest);
    int deleteUserAuthAll(List<AuthVO> authVOList, BatchRequest batchRequest);
    int deleteGroupAuthAll(List<AuthVO> authVOList, BatchRequest batchRequest);
    int deleteResourceAuthAll(List<AuthVO> authVOList, BatchRequest batchRequest);
    int deleteMenuAuthAll(List<AuthVO> authVOList, BatchRequest batchRequest);
    int deleteMultiAuthAll(List<AuthVO> authVOList, BatchRequest batchRequest);
    void insertAuth(List<AuthVO> authList, BatchRequest batchRequest);
}
