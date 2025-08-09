package net.lotte.chamomile.admin.privacypolicy.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import net.lotte.chamomile.module.database.audit.TimeAuthorLog;

/**
 * <pre>
 * Admin 개인정보 처리방침 관리 REST API Mybatis CHMM_PRIVACY_POLICY_SUB_INFO(개인정보 처리방침 테이블) ReturnType.
 * </pre>
 *
 * @ClassName   : PrivacyPolicySubVO.java
 * @Description : Admin 개인정보 처리방침 관리 REST API Mybatis CHMM_PRIVACY_POLICY_SUB_INFO(개인정보 처리방침 테이블) ReturnType.
 * @author chaehwi.lim
 * @since 2024.08.13
 * @version 3.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2024.08.13     chaehwi.lim            최초 생성
 *
 *
 * </pre>
 * Copyright (C) 2024 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PrivacyPolicySubVO extends TimeAuthorLog {
    private Long policyId;
    private String policyVersion;
    private Long policySubVersion;
    private String applyYn;
}
