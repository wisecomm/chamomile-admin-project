package net.lotte.chamomile.admin.authhierarchy.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import net.lotte.chamomile.module.database.audit.TimeAuthorLog;


/**
 * <pre>
 * 권한 상하 관계 관련 VO 도메인 객체.
 * </pre>
 *
 * @author TaehoPark
 * @since 2023-10-06
 * @version 3.0
 * @Modification
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-10-06     TaehoPark            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class AuthHierarchyVO extends TimeAuthorLog {
    /** 케모마일 권한 정보 **/
    @NotBlank
    @Size(min=5, max=32)
    @Schema(description = "상위 권한 아이디", example = "ROLE_ADMIN_ID")
    private String parentRoleId;

    @Size(min=5, max=32)
    @Schema(description = "하위 권한 아이디", example = "ROLE_USER_ID")
    private String childRoleId; /* 하위 Role ID */

    @Pattern(regexp = "^(0|1)$", message = "참/거짓은 0/1로 구분 합니다.")
    @Schema(description = "사용여부", example = "1")
    private String useYn; /* 사용 여부 */

    @Schema(example = "U", allowableValues = {"I", "U"})
    private String flag;

    @Schema(hidden = true)
    private int order;

    private String childRoleName; /* non-table-property */
    private String parentRoleName; /* non-table-property */

}
