package net.lotte.chamomile.admin.privacypolicy.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <pre>
 * Admin 개인정보 처리방침 관리 REST API Mybatis CHMM_PRIVACY_POLICY_INFO(개인정보 처리방침 테이블) ReturnType.
 * </pre>
 *
 * @ClassName   : PrivacyPolicyPostVO.java
 * @Description : Admin 개인정보 처리방침 관리 REST API Mybatis CHMM_PRIVACY_POLICY_INFO(개인정보 처리방침 테이블) ReturnType.
 * @author chaehwi.lim
 * @since 2024.08.21
 * @version 3.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2024.08.21     chaehwi.lim            최초 생성
 *
 *
 * </pre>
 * Copyright (C) 2024 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PrivacyPolicyPostVO {

    private String policyVersion;
    private String policyNoticeDt;
    private String policyStartDt;
    private String policyEndDt;
    private String title;
    private String content;
}
