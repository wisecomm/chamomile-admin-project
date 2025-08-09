package net.lotte.chamomile.admin.group.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 그룹 검색 DTO.
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
public class GroupQuery {
    private String searchGroupId;
    private String searchGroupName;
    private String searchUseYn;
    private String searchParentGroupId;
    
    // 240801 임지훈 - 그룹유저정보 조회여부 추가
    private Boolean includeUserYn;
}
