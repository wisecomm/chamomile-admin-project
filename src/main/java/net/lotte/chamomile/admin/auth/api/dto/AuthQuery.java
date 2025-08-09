package net.lotte.chamomile.admin.auth.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <pre>
 * 권한 관련 HTTP READ 요청 객체.
 * </pre>
 *
 * @author TaehoPark
 * @since 2023-09-19
 * @version 3.0
 * @Modification
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-09-19     TaehoPark            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@Data
public class AuthQuery {

    @Schema(description = "권한아이디", example = "admin")
    private String searchRoleId;   //권한아이디(검색)
    @Schema(description = "권한명", example = " ")
    private String searchRoleName; //권한명(검색)
    @Schema(description = "권한시작일자", example = " ")
    private String searchRoleStartDt;//권한시작일자(검색)
    @Schema(description = "권한종료일자", example = " ")
    private String searchRoleEndDt;  //권한종료일자(검색)
    @Schema(description = "사용여부", example = " ")
    private String searchUseYn;      //사용여부(검색)
    @Schema(description = "권한아이디/권한명", example = " ")
    private String searchRole;      //사용여부(검색)
    @Schema(description = "권한검색 Type", example = " ")
    private String searchRoleType;      //사용여부(검색)
}
