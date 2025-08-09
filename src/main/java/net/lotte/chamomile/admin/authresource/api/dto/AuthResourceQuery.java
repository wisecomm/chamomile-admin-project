package net.lotte.chamomile.admin.authresource.api.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 리소스 권한 검색
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
public class AuthResourceQuery {
    private String searchResourceId;
    private String searchResourceName;
    private String searchUseYn;
    private String showEmptyTreeYn;
    @NotEmpty(message = "권한 리소스 ID는 필수 값 입니다.")
    private String resourceId;

    public AuthResourceQuery(String searchResourceId) {
        this.searchResourceId = searchResourceId;
    }
}
