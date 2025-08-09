package net.lotte.chamomile.admin.commoncode.api.dto;

import lombok.Data;

/**
 * <pre>
 * Admin 공통코드 REST API Mybatis 엑셀다운 Request DTO.
 * 엑셀 전체 다운을 위해 화면 비지니스에 적합한 DTO 신규 생성함.
 * </pre>
 *
 * @ClassName   : CommonCodeQuery.java
 * @Description : Admin 공통코드 REST API Mybatis 엑셀다운 Request DTO.
 * @author wy.kim
 * @since 2024.08.29
 * @version 3.3
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2024.08.29     wy.kim            최초 생성
 *
 *
 * </pre>
 * Copyright (C) 2023 by LDCC., All right reserved.
 */
@Data
public class CommonCodeQuery {

    private String searchCategoryId;

    private String searchCodeCategoryId;
    private String searchCodeId;

    private String searchCodeItemCategoryId;
    private String searchCodeItemCodeId;
    private String searchCodeItemId;

    private String searchType; /* 1:대분류, 2:중분류, 3:소분류 */
    private String searchDesc;
    private String searchUseYn;

    public CommonCodeQuery(String searchCategoryId, String searchCodeId, String searchCodeItemId) {
        this.searchCategoryId = searchCategoryId;
        this.searchCodeId = searchCodeId;
        this.searchCodeItemId = searchCodeItemId;
    }
}
