package net.lotte.chamomile.admin.message.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import net.lotte.chamomile.admin.message.domain.LanguageVO;
import net.lotte.chamomile.module.database.audit.TimeAuthorLog;

/**
 * <pre>
 * 언어 Update DTO 객체.
 * </pre>
 *
 * @author mw-kim
 * @version 3.0
 * @Modification <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2024-08-20     mw-kim            최초 생성
 * </pre>
 * Copyright (C) 2023 by LDCC., All right reserved.
 * @since 2023-10-05
 */
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
public class LanguageUpdate extends TimeAuthorLog {
    @Schema(example = "test")
    private String languageCode;
    @Schema(example = "test")
    private String countryCode;
    @Schema(example = "desc")
    private String description;

    @Schema(example = "test1")
    private String prevLanguageCode;
    @Schema(example = "test1")
    private String prevCountryCode;

    public LanguageVO toLanguageVO() {
        return new LanguageVO(languageCode, countryCode, description);
    }
}
