package net.lotte.chamomile.admin.resource.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import net.lotte.chamomile.module.database.audit.TimeAuthorLog;
import net.lotte.chamomile.module.excel.annotation.ExcelSheet;

/**
 * <pre>
 * 리소스 관련 VO 도메인 객체.
 * </pre>
 *
 * @author MoonHKLee
 * @since 2023-09-15
 * @version 3.0
 * @Modification
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-09-15     MoonHKLee            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@ExcelSheet(maxRows = Integer.MAX_VALUE)
public class ResourceExcelVO extends TimeAuthorLog {
    private String resourceId;
    private String resourceDesc;
    private String resourceHttpMethod;
    private String resourceName;
    private String resourceUri;
    private Integer securityOrder;
    private String useYn;
}
