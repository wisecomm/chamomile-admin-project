package net.lotte.chamomile.admin.resource.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 리소스 관련 HTTP READ 요청 객체.
 * </pre>
 *
 * @author MoonHKLee
 * @version 3.0
 * @Modification
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-09-15     MoonHKLee            최초 생성
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 * @since 2023-09-15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceQuery {
    @Schema(description = "검색유형", example = "searchResourceId")
    private String searchType;
    @Schema(description = "검색어", example = "test")
    private String searchValue;
    @Schema(description = "사용여부", example = "1")
    private String searchUseYn;

    /* 리소스권한 조회용 */
    private String searchResourceId;
    private String searchResourceName;
}
