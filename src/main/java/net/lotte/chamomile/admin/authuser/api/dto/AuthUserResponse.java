package net.lotte.chamomile.admin.authuser.api.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 유저 권한 반화 DTO.
 * </pre>
 *
 * @author MoonHKLee
 * @since 2023-11-09
 * @version 3.0
 * @see
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
public class AuthUserResponse {
    private List<AuthUserDetailResponse> leftList;
    private List<AuthUserDetailResponse> rightList;
}
