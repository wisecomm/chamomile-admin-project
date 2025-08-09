package net.lotte.chamomile.admin.menuauth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import net.lotte.chamomile.module.database.audit.TimeAuthorLog;

/**
 * <pre>
 * 메뉴권한 관리 VO 도메인 객체.
 * </pre>
 *
 * @author chaelynJang
 * @version 3.0
 * @Modification <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-10-16     chaelynJang            최초 생성
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 * @since 2023-10-16
 */
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class MenuAuthVO extends TimeAuthorLog {
    private String menuId; // 메뉴 아이디
    private String roleId; // 권한 아이디
    private Boolean fixed; // 고정여부
    private String val; // 변수가 가지는 값
    private String text; // 화면에 보이는 값
}
