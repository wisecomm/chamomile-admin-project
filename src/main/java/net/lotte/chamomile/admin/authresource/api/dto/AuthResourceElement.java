package net.lotte.chamomile.admin.authresource.api.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import net.lotte.chamomile.admin.authresource.domain.AuthResourceVO;

/**
 * <pre>
 * 리소스 권한 요소 DTO.
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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResourceElement {
    private String fixed;
    private String origin;
    private String text;
    @NotEmpty(message = "val은 필수 입력값입니다.")
    private String val;

    public AuthResourceVO toEntity(String resourceId) {
        return AuthResourceVO.builder()
                .resourceId(resourceId)
                .roleId(fixed.equals("false")?val:null)
                .useYn("1")
                .build();
    }
}
