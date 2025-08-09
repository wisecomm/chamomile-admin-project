package net.lotte.chamomile.admin.authhierarchy.api.dto;

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
public class AuthHierarchyQuery {

    @Schema(description = "검색 상위 권한 아이디", example = "ROLE_ADMIN_ID")
    private String searchParentRoleId;
    @Schema(description = "검색 하위 권한 아이디", example = "ROLE_USER_ID")
    private String searchChildRoleId;
    @Schema(description = "검색  권한 아이디", example = "ROLE_ADMIN_ID")
    private String searchRoleId;

}
