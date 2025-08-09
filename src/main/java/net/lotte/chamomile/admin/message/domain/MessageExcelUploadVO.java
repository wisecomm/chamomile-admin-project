package net.lotte.chamomile.admin.message.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * <pre>
 * 다국어 관리 엑셀 업로드 관련 VO 도메인 객체.
 * </pre>
 *
 * @author chaelynJang
 * @version 3.0
 * @Modification <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-10-18     chaelynJang            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 * @since 2023-10-18
 */
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class MessageExcelUploadVO {
    private String code;
    private String languageCode;
    private String countryCode;
    private String message;
}
