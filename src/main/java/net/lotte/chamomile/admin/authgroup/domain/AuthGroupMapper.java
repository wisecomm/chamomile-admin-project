package net.lotte.chamomile.admin.authgroup.domain;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.lotte.chamomile.admin.authgroup.domain.AuthGroupExcelVO;
import net.lotte.chamomile.admin.authgroup.domain.AuthGroupVO;
import net.lotte.chamomile.admin.authtree.AuthTreeVO;
import net.lotte.chamomile.admin.authuser.domain.AuthUserVO;
import net.lotte.chamomile.module.database.mybatis.batch.BatchRequest;

/**
 * <pre>
 * 권한 그룹 매퍼 클래스.
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
public interface AuthGroupMapper {
    List<AuthTreeVO> findAuthGroupUnMappedList(String groupId);
    List<AuthTreeVO> findAuthGroupList(String groupId);
    List<AuthUserVO> findAuthUserList();
    void insertAuthGroup(AuthGroupVO authGroupVO);
    void deleteAuthGroup(String groupId);
    void insertAuthGroupExcel(List<AuthGroupExcelVO> command, BatchRequest batchRequest);
}
