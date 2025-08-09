package net.lotte.chamomile.admin.authgroup.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import net.lotte.chamomile.module.database.audit.TimeAuthorLog;


/**
 * <pre>
 * 권한 그룹 VO.
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
public class AuthGroupVO extends TimeAuthorLog {
    private String groupId;
    private String roleId;
    private String useYn;

    public AuthGroupVO(String groupId, String roleId) {
        this.groupId = groupId;
        this.roleId = roleId;
    }
}
