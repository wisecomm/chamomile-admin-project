package net.lotte.chamomile.admin.authresource.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * <pre>
 * 리소스 권한 사용 가능한 목록 DTO.
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
@Builder
public class UsableAuthResourceResponse {
    private String roleId;
    private String roleName;
}
