package net.lotte.chamomile.admin.authgroup.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import net.lotte.chamomile.admin.authhierarchy.domain.AuthHierarchyVO;

/**
 * <pre>
 * 권한 그룹 상세 반환 DTO.
 * </pre>
 *
 * @author MoonHKLee
 * @since 2023-11-01
 * @version 3.0
 * @see AuthGroupResponse
 * @Modification
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-11-01     MoonHKLee            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthGroupDetailResponse {
    private String text;
    private String val;
    private String useYn;
    private String fixed;

    public static AuthGroupDetailResponse of(AuthHierarchyVO authGroupVO) {
        return AuthGroupDetailResponse.builder()
                .text(authGroupVO.getChildRoleId() + "(from " + authGroupVO.getParentRoleId() + ")")
                .val(authGroupVO.getChildRoleId() + "*")
                .fixed("true")
                .build();
    }
}
