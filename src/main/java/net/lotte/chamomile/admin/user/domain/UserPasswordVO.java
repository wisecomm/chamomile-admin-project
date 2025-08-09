package net.lotte.chamomile.admin.user.domain;

import javax.validation.constraints.NotBlank;
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
 * 사용자 비밀번호 변경 VO 도메인 객체.
 * </pre>
 *
 * @author TaehoPark
 * @version 3.0
 * @Modification <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-12-10     TaehoPark            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 * @since 2023-12-10
 */
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class UserPasswordVO extends TimeAuthorLog {
    @NotBlank
    @Size(min = 5, max = 50)
    @Schema(description = "사용자 ID", example = "admin")
    private String userId; /* 사용자 ID */

    @NotBlank
    @Size(max = 255)
    @ToString.Exclude
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String userPwd; /* 사용자 비밀번호 */ // NOTE: 패스워드에 대한 자릿수 체크는 여기에서 안한다.
    @NotBlank
    @Size(max = 255)
    private String userPrevPwd; /* 기존 비밀번호 */
    @NotBlank
    @Size(max = 255)
    private String userPwdCheck; /* 신규 비밀번호 */

    @Schema(description = "비밀번호 만료일", example = "20300101")
    private String passwordExpireDt; /* 비밀번호 만료일 */

    @Schema(description = "계정 종료일", example = "20300101")
    private String accountEndDt; /* 계정 종료일 */

    @NotBlank
    @Schema(description = "패스워드 변경타입", example = "account")
    private String type; /*패스워드 변경 타입 */

    @NotBlank
    @Schema(description = "패스워드 변경여부", example = "true")
    private String pwChange;

    @Size(max = 1)
    @Schema(hidden = true)
    private String accountNonLock; /* 사용자계정잠김여부 */

    private int plusDays;

}
