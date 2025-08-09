package net.lotte.chamomile.admin.privacypolicy.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 개인정보 처리방침 관련 HTTP READ 요청 객체.
 * </pre>
 *
 * @author chaehwi.lim
 * @version 3.0
 * @Modification <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2024-08-13     chaehwi.lim            최초 생성
 * </pre>
 * Copyright (C) 2024 by LOTTE INNOVATE COMPANY., All right reserved.
 * @since 2024-08-13
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrivacyPolicyQuery {
    private String policyVersion;
    private String policyStartDt;
    private String postYn;
    private Long policyId;
}
