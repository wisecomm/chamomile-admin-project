package net.lotte.chamomile.admin.authresource.api.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 리소스 권한 변경 DTO.
 * </pre>
 *
 * @author MoonHKLee
 * @since 2023-11-09
 * @version 3.0
 * @Modification
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-11-09     MoonHKLee            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResourceCommand {
    @NotEmpty(message = "resourceId는 필수 입력값입니다.")
    private String resourceId;
    @Valid
    private List<AuthResourceElement> rightValue;
}
