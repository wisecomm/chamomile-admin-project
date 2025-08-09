package net.lotte.chamomile.admin.commoncode.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import net.lotte.chamomile.module.database.audit.TimeAuthorLog;

/**
 * <pre>
 * Admin 공통코드 REST API Mybatis CHMM_CODE_ITEM_INFO(소분류 테이블) ReturnType.
 * </pre>
 *
 * @ClassName   : CodeItem.java
 * @Description : Admin 공통코드 REST API Mybatis CHMM_CODE_ITEM_INFO(소분류 테이블) ReturnType.
 * @author chaelynJang
 * @since 2023.09.13
 * @version 3.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023.09.13     chaelynJang            최초 생성
 *
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CodeItemVO extends TimeAuthorLog implements Serializable {
    private String categoryId;
    private String codeId;
    private String codeItemId;
    private String codeItemDesc;
    private Integer orderNum;
    private String useYn;
    private String realValue;

    public CodeItemVO(String categoryId, String codeId, String codeItemId) {
        this.categoryId = categoryId;
        this.codeId = codeId;
        this.codeItemId = codeItemId;
    }
}
