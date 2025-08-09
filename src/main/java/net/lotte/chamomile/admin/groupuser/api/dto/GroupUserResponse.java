package net.lotte.chamomile.admin.groupuser.api.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

import net.lotte.chamomile.admin.groupuser.domain.GroupUserVO;

/**
 * <pre>
 * 그룹 유저 반환 DTO.
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
@Data
@AllArgsConstructor
public class GroupUserResponse {
    private List<GroupUserVO> returnList;

    public GroupUserResponse() {
        returnList = new ArrayList<>();
    }

    public void addRightGroupUserList(List<GroupUserVO> rightGroupUserList) {
        returnList.addAll(rightGroupUserList);
    }
}
