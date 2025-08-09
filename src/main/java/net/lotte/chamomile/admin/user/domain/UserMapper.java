package net.lotte.chamomile.admin.user.domain;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.lotte.chamomile.admin.logging.domain.UserAccessLoggingVO;
import net.lotte.chamomile.admin.user.api.dto.UserQuery;
import net.lotte.chamomile.module.database.mybatis.batch.BatchRequest;

/**
 * <pre>
 * 사용자 관련 마이바티스 매핑 인터페이스.
 * </pre>
 *
 * @author TaehoPark
 * @since 2023-09-20
 * @version 3.0
 * @Modification
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-09-20     TaehoPark            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@Mapper
public interface UserMapper {
    Page<UserVO> findUserListDataPage(UserQuery query, Pageable pageable);
    Page<UserVO> findUserListWithGroup(UserQuery query, Pageable pageable);
    List<UserVO> findUserListData(UserQuery query);
    Optional<UserVO> findUserDetail(UserQuery resourceId);
    String findUserPwd(UserVO userVO);
    int insertUser(UserVO userVO);
    int insertUser(List<UserVO> userVO, BatchRequest batchRequest);
    int updateUser(UserVO userVO);
    int updatePasswordUser(UserPasswordVO userPasswordVO);
    void deleteUserGroupMap(List<UserVO> userVOList, BatchRequest batchRequest);
    void deleteUserauth(List<UserVO> userVOList, BatchRequest batchRequest);
    void deleteUser(List<UserVO> userVOList, BatchRequest batchRequest);

    // 모바일 기능
    Optional<UserVO> findUserMobileDetail(UserQuery query);
    void updateMdmYnOfUser(UserVO userVO);

    int userIdCheck(UserVO userVO);
    int userMobileDupCheck(UserVO userVO);
    int userEmailDupCheck(UserVO userVO);
    Optional<UserAccessLoggingVO> findUserRecentlyLoggingInfo(UserQuery query);
    Optional<UserAccessLoggingVO> findUserRecentlyLoggingInfo2(UserQuery query);
    void accountOnLock(String userId);


}
