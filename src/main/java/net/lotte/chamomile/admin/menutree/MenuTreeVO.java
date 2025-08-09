package net.lotte.chamomile.admin.menutree;

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
public class MenuTreeVO extends TimeAuthorLog {
    private String menuId;
    private String useYn;
    private String menuName;
    private String groupId;
    private String groupName;

    public MenuTreeVO(String menuName, String menuId) {
        this.menuName = menuName;
        this.menuId = menuId;
    }

    public MenuTreeVO(String userId, String menuId, String useYn) {
        this.menuId = menuId;
        this.useYn = useYn;
    }
}
