package net.lotte.chamomile.admin.commoncode.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * <pre>
 * Admin 공통코드 REST API Mybatis CHMM_CODE_ITEM_INFO(소분류 테이블) 변경 Request DTO.
 * </pre>
 *
 * @ClassName   : CodeItemCommand.java
 * @Description : Admin 공통코드 REST API Mybatis CHMM_CODE_ITEM_INFO(소분류 테이블) 변경(CREATE, UPDATE, DELETE) 조건 담을 Request DTO.
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
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CodeItemCommand {
    private String categoryId;
    private String codeId;
    private String codeItemId;
    private String codeItemDesc;
    private Integer orderNum;
    private String useYn;
    private String realValue;

    public CodeItemCommand(String categoryId, String codeId, String codeItemId) {
        this.categoryId = categoryId;
        this.codeId = codeId;
        this.codeItemId = codeItemId;
    }
}
