package net.lotte.chamomile.admin.message.api.dto;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 메시지 관련 HTTP READ 요청 객체.
 * </pre>
 *
 * @author chaelynJang
 * @version 3.0
 * @Modification <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-10-05     chaelynJang            최초 생성
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 * @since 2023-10-05
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageQuery implements Serializable {
    @Schema(description = "검색유형", example = "searchCode")
    private String searchType;
    @Schema(description = "검색어", example = "test")
    private String searchText;
    @Schema(description = "코드유형", example = "alert")
    private String searchCodeCategory;
    @Schema(description = "등록여부", example = "1")
    private String searchRegYn;
    @Schema(description = "메시지코드", example = "alert.msg.alreadydata")
    private String searchCode;
    @Schema(description = "언어코드", example = "ko")
    private String searchLanguageCode;
    @Schema(description = "국가코드", example = "KR")
    private String searchCountryCode;
}
