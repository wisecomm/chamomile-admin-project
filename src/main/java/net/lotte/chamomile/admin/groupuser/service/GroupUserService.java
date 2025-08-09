package net.lotte.chamomile.admin.groupuser.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.lotte.chamomile.admin.groupuser.api.dto.GroupUserResponse;
import net.lotte.chamomile.admin.groupuser.domain.GroupUserExcelVO;
import net.lotte.chamomile.admin.groupuser.domain.GroupUserVO;

/**
 * <pre>
 * 그룹 유저 서비스 인터페이스.
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
public interface GroupUserService {
    Page<GroupUserExcelVO> getGroupUserList(Pageable pageable);
    GroupUserResponse getGroupUser(String groupId);
    GroupUserResponse getGroupUserByUserId(String userId);
    Page<GroupUserVO> getUnMappedUser(String groupId, Pageable pageable);
    void updateGroupUser(String groupId, List<String> userIds);
    void updateGroupUserByUserId(String userId, List<String> groupIds);
    void createGroupUser(List<GroupUserExcelVO> list);
}
