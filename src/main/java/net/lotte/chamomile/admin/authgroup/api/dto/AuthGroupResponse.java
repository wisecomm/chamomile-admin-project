package net.lotte.chamomile.admin.authgroup.api.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import net.lotte.chamomile.admin.authgroup.api.AuthGroupController;

/**
 * <pre>
 * 권한 그룹 전체 반환 DTO.
 * </pre>
 *
 * @author MoonHKLee
 * @since 2023-11-01
 * @version 3.0
 * @see AuthGroupController#getAuthGroupDetail(String)
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
public class AuthGroupResponse {
    private List<AuthGroupDetailResponse> leftList;
    private List<AuthGroupDetailResponse> rightList;
}
