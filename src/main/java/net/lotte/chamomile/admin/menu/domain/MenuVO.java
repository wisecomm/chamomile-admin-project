package net.lotte.chamomile.admin.menu.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import net.lotte.chamomile.module.database.audit.TimeAuthorLog;

/**
 * <pre>
 * 메뉴 관련 VO 도메인 객체.
 * </pre>
 *
 * @author chaelynJang
 * @version 3.0
 * @Modification <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-09-26     chaelynJang            최초 생성
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 * @since 2023-09-26
 */
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
public class MenuVO extends TimeAuthorLog {
    private String menuId;
    @Schema(defaultValue = "0")
    @NotNull
    private Integer menuLvl;
    private String menuUri;
    @Schema(defaultValue = "test")
    @NotEmpty
    private String menuName;
    @Schema(defaultValue = "root")
    @NotEmpty
    private String upperMenuId;
    private String menuDesc;
    @Schema(defaultValue = "-1")
    @NotNull
    private Integer menuSeq;
    @Schema(defaultValue = "0")
    @NotEmpty
    private String leftMenuYn;
    @Schema(defaultValue = "0")
    @NotEmpty
    private String useYn;
    @Schema(defaultValue = "0")
    @NotEmpty
    private String adminMenuYn;
    private String menuHelpUri;
    private String menuScript;
    private String personalDataYn;
}
