package net.lotte.chamomile.admin.authuser.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import net.lotte.chamomile.admin.authhierarchy.domain.AuthHierarchyVO;

/**
 * <pre>
 * 유저 권한 상세 반환 DTO.
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
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthUserDetailResponse {
    private String text;
    private String val;
    private String useYn;
    private String fixed;

    public static AuthUserDetailResponse of(AuthHierarchyVO authGroupVO) {
        return AuthUserDetailResponse.builder()
                .text(authGroupVO.getChildRoleId() + "(from " + authGroupVO.getParentRoleId() + ")")
                .val(authGroupVO.getChildRoleId() + "*")
                .fixed("true")
                .build();
    }
}
