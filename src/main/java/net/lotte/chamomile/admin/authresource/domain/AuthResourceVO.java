package net.lotte.chamomile.admin.authresource.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import net.lotte.chamomile.module.database.audit.TimeAuthorLog;

/**
 * <pre>
 * 리소스 권한 VO.
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
public class AuthResourceVO extends TimeAuthorLog {
    private String resourceId;
    private String roleId;
    private String useYn;
}
