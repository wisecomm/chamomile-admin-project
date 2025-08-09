package net.lotte.chamomile.admin.resource.domain;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import net.lotte.chamomile.module.database.audit.TimeAuthorLog;

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
public class ResourceVO extends TimeAuthorLog {
    private String resourceId;
    private String resourceDesc;
    private String resourceHttpMethod;
    private String resourceName;
    private String resourceUri;
    @NotNull(message = "숫자를 입력하세요.")
    @Min(value = 1, message = "리소스 순서는 1~5 자리의 양수만 입력 가능합니다.")
    @Max(value = 99999, message = "리소스 순서는 1~5 자리의 양수만 입력 가능합니다.")
    private Integer securityOrder;
    @Pattern(regexp = "^(0|1)$", message = "참/거짓은 0/1로 구분 합니다.")
    private String useYn;


    private String flag;

    public ResourceVO(String resourceId) {
        this.resourceId = resourceId;
    }
}
