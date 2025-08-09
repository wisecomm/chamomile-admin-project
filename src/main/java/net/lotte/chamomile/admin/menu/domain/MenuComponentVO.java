package net.lotte.chamomile.admin.menu.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import net.lotte.chamomile.module.database.audit.TimeAuthorLog;

/**
 * <pre>
 * 메뉴 컴포넌트 관련 VO 도메인 객체.
 * </pre>
 *
 * @author chaelynJang
 * @version 3.0
 * @Modification <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2024-03-04     chaelynJang            최초 생성
 *
 * </pre>
 * Copyright (C) 2024 by LOTTE INNOVATE COMPANY., All right reserved.
 * @since 2024-03-04
 */
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class MenuComponentVO extends TimeAuthorLog {
    @Schema(defaultValue = "test")
    @NotEmpty
    private String menuId;
    @Schema(defaultValue = "test")
    @NotEmpty
    private String componentId;
    private String componentUrl;
    private String componentName;
    private String componentDesc;
    @Schema(defaultValue = "0")
    private String useYn;
    @NotNull
    @Schema(defaultValue = "0")
    private Integer componentType;

    public MenuComponentVO(String menuId, String componentId) {
        this.menuId = menuId;
        this.componentId = componentId;
    }
}
