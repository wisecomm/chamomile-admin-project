package net.lotte.chamomile.admin.group.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.lotte.chamomile.admin.group.api.dto.GroupQuery;
import net.lotte.chamomile.admin.group.domain.GroupExcelVO;
import net.lotte.chamomile.admin.group.domain.GroupVO;

/**
 * <pre>
 * 그룹 서비스 인터페이스.
 * </pre>
 *
 * @author MoonHKLee
 * @since 2023-11-09
 * @version 3.0
 * @see
 * @Modification
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-11-09     MoonHKLee            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
public interface GroupService {
    Page<GroupVO> getGroupList(GroupQuery query, Pageable pageable);
    List<GroupVO> getGroupListWithId(String groupId);

    Page<GroupExcelVO> getGroupListExcel(GroupQuery query, Pageable pageable);

    GroupVO getGroup(String groupId);
    Boolean getGroupCheck(String groupId);

    void createGroup(GroupVO command);

    void updateGroup(GroupVO command);

    void deleteGroup(List<GroupVO> commands);
    void createGroup(List<GroupExcelVO> list);

}
