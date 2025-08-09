package net.lotte.chamomile.admin.auth.domain;

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
 * 권한 관련 VO 도메인 객체.
 * </pre>
 *
 * @author TaehoPark
 * @since 2023-09-20
 * @version 3.0
 * @Modification
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-09-20     TaehoPark            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class AuthVO extends TimeAuthorLog {
    /** 케모마일 권한 정보 **/
    @NotBlank
    @Size(max=50)
    @Schema(description = "권한 아이디", example = "test")
    private String roleId;    //권한 아이디
    @NotBlank
    @Size(max=50)
    @Schema(description = "권한명", example = "테스트 권한")
    private String roleName;  //권한 명
    @Size(max=200)
    @Schema(description = "권한 설명", example = "테스트 설명")
    private String roleDesc;  //권한 설명
    @Size(min=8,max=8)
    @Schema(description = "권한 시작일자", example = "20231004")
    private String roleStartDt;//권한 시작일자
    @Size(min=8,max=8)
    @Schema(description = "권한 종료일자", example = "20241114")
    private String roleEndDt;  //권한 종료일자


    /** 케모마일 사용자 롤 관계 **/
    @Schema(hidden = true)
    private String userId; /* 사용자아이디 */
    private String userName; /* 사용자명 */

    @Pattern(regexp = "^(0|1)$", message = "참/거짓은 0/1로 구분 합니다.")
    @Schema(description = "사용여부", example = "1")
    private String useYn; /* 사용 여부 */
    @Schema(example = "U", allowableValues = {"I", "U"})
    private String flag;
    @Schema(hidden = true)
    private Boolean valid;

}
