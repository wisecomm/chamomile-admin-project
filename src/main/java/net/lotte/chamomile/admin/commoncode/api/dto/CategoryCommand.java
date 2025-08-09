package net.lotte.chamomile.admin.commoncode.api.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * <pre>
 * Admin 공통코드 REST API Mybatis CHMM_CATEGORY_INFO(대분류 테이블) 변경 Request DTO.
 * </pre>
 *
 * @ClassName   : CategoryCommand.java
 * @Description : Admin 공통코드 REST API Mybatis CHMM_CATEGORY_INFO(대분류 테이블) 변경(CREATE, UPDATE, DELETE) 조건 담을 Request DTO.
 * @author chaelynJang
 * @since 2023.09.13
 * @version 1.0
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
public class CategoryCommand {
    @NotEmpty
    private String categoryId;
    private String categoryDesc;
    private Integer orderNum;
    @NotEmpty
    private String useYn;
    private String realValue;
}
