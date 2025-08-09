package net.lotte.chamomile.admin.authuser.domain;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.lotte.chamomile.admin.authtree.AuthTreeVO;
import net.lotte.chamomile.module.database.mybatis.batch.BatchRequest;

/**
 * <pre>
 * 유저 권한 매퍼.
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
public interface AuthUserMapper {

    List<AuthTreeVO> findAuthUserUnMappedList(String userId);

    List<AuthTreeVO> findAuthUser(String userId);

    List<AuthTreeVO> findAuthUserGroup(String userId);

    void insertAuthUser(List<AuthUserVO> command, BatchRequest batchRequest);
    void deleteAuthUser(AuthUserVO userId);

    void insertAuthUserExcel(List<AuthUserExcelUploadVO> command, BatchRequest batchRequest);
}
