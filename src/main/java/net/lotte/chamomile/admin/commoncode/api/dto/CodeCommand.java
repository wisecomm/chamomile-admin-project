package net.lotte.chamomile.admin.commoncode.api.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
/**
 * <pre>
 * Admin 공통코드 REST API Mybatis CHMM_CODE_INFO(증분류 테이블) 변경 Request DTO.
 * </pre>
 *
 * @ClassName   : CodeCommand.java
 * @Description : Admin 공통코드 REST API Mybatis CHMM_CODE_INFO(중분류 테이블) 변경(CREATE, UPDATE, DELETE) 조건 담을 Request DTO.
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
public class CodeCommand {
    @NotEmpty
    private String categoryId;
    @NotEmpty
    private String codeId;
    private String codeDesc;
    private Integer orderNum;
    @NotEmpty
    private String useYn;
    private String realValue;

}
