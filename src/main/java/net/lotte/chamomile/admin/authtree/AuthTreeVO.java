package net.lotte.chamomile.admin.authtree;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import net.lotte.chamomile.module.database.audit.TimeAuthorLog;

/**
 * <pre>
 * 유저 권한 VO.
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
public class AuthTreeVO extends TimeAuthorLog {
    private String userId;
    private String roleId;
    private String useYn;
    private String roleName;
    private String groupId;
    private String groupName;

    public AuthTreeVO(String roleName, String roleId) {
        this.roleName = roleName;
        this.roleId = roleId;
    }

    public AuthTreeVO(String userId, String roleId, String useYn) {
        this.userId = userId;
        this.roleId = roleId;
        this.useYn = useYn;
    }
}
