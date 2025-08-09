package net.lotte.chamomile.admin.commoncode.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <pre>
 * Admin 공통코드 소분류 엑셀 업로드 VO.
 * </pre>
 *
 * @author chaelynJang
 * @since 2023.10.19
 * @version 3.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023.10.19     chaelynJang            최초 생성
 *
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CodeItemExcelUploadVO {
    private String categoryId;
    private String codeId;
    private String codeItemId;
    private String codeItemDesc;
    private Integer orderNum;
    private String realValue;

    public CodeItemVO toEntity() {
        return CodeItemVO.builder()
                .categoryId(categoryId)
                .codeId(codeId)
                .codeItemId(codeItemId)
                .codeItemDesc(codeItemDesc)
                .orderNum(orderNum)
                .useYn("1")
                .realValue(realValue)
                .build();
    }
}
