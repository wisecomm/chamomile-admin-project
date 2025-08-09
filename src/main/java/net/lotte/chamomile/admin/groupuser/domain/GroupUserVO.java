package net.lotte.chamomile.admin.groupuser.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import net.lotte.chamomile.module.database.audit.TimeAuthorLog;

/**
 * <pre>
 * 그룹 유저 VO.
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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class GroupUserVO extends TimeAuthorLog implements Comparable<GroupUserVO> {
    private String groupId;
    private String groupName;
    private String userId;
    private String useYn;

    @JsonIgnore
    private LocalDateTime sysInsertDtm;
    @JsonIgnore
    private LocalDateTime sysUpdateDtm;
    @JsonIgnore
    private String sysInsertUserId;
    @JsonIgnore
    private String sysUpdateUserId;

    private String userName;

    public GroupUserVO(String groupId, String userId) {
        this.groupId = groupId;
        this.userId = userId;
        this.useYn = "1";
        onCreate();
    }

    @Override
    public int compareTo(GroupUserVO o) {
        String leftUserId = this.getUserId();
        String rightUserId = o.getUserId();

        int compareUserId = leftUserId.compareTo(rightUserId);
        if (compareUserId != 0) return compareUserId;

        String leftGroupId = this.getGroupId();
        String rightGroupId = o.getGroupId();

        if (leftGroupId.equals(groupId)) return -1;
        if (rightGroupId.equals(groupId)) return 1;

        return leftGroupId.compareTo(rightGroupId);
    }
}
