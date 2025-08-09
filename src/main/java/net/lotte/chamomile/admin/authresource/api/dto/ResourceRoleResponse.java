package net.lotte.chamomile.admin.authresource.api.dto;

import java.util.List;

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

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class ResourceRoleResponse extends TimeAuthorLog {
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
    private List<String> roleIdList;


    private String flag;

    public ResourceRoleResponse(String resourceId) {
        this.resourceId = resourceId;
    }
}
