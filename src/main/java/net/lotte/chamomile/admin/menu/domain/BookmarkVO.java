package net.lotte.chamomile.admin.menu.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
 * 즐겨찾기 VO 도메인 객체.
 * </pre>
 *
 * @author teahoPark
 * @version 3.0
 * @Modification <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2024-01-22     taehoPark            최초 생성
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 * @since 2024-01-22
 */
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
public class BookmarkVO extends TimeAuthorLog {

    @Schema(description = "menu00000011", example = "0")
    @NotNull
    private String menuId;

    @NotBlank
    @Size(min = 5, max = 50)
    @Schema(description = "사용자 ID", example = "admin")
    private String userId; /* 사용자 ID */

}
