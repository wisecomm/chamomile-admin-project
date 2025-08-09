package net.lotte.chamomile.admin.commoncode.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import net.lotte.chamomile.module.database.audit.TimeAuthorLog;

/**
 * <pre>
 * Admin 공통코드 REST API Mybatis CHMM_CATEGORY_INFO(대분류 테이블) ReturnType.
 * </pre>
 *
 * @ClassName   : Category.java
 * @Description : Admin 공통코드 REST API Mybatis CHMM_CATEGORY_INFO(대분류 테이블) ReturnType.
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
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CategoryVO extends TimeAuthorLog implements Serializable {
    private String categoryId;
    private String categoryDesc;
    private Integer orderNum;
    private String useYn;
    private String realValue;
}
