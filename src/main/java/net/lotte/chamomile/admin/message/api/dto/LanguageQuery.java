package net.lotte.chamomile.admin.message.api.dto;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 다국어 관련 HTTP READ 요청 객체.
 * </pre>
 *
 * @author chaelynJang
 * @version 3.0
 * @Modification <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2024-08-16     minwooKim            최초 생성
 * </pre>
 * Copyright (C) 2023 by LDCC., All right reserved.
 * @since 2023-10-05
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LanguageQuery implements Serializable {
    @Schema(description = "언어코드", example = "ko")
    private String searchLanguageCode;
    @Schema(description = "국가코드", example = "KR")
    private String searchCountryCode;
}
