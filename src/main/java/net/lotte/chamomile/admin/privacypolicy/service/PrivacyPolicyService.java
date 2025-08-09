package net.lotte.chamomile.admin.privacypolicy.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.lotte.chamomile.admin.privacypolicy.api.dto.PrivacyPolicyQuery;
import net.lotte.chamomile.admin.privacypolicy.domain.PrivacyPolicyMainVO;
import net.lotte.chamomile.admin.privacypolicy.domain.PrivacyPolicyPostVO;
import net.lotte.chamomile.admin.privacypolicy.domain.PrivacyPolicySubVO;
import net.lotte.chamomile.admin.privacypolicy.domain.PrivacyPolicyVO;

/**
 * <pre>
 * 개인정보 처리방침 관련 서비스 인터페이스.
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
public interface PrivacyPolicyService {
    Page<PrivacyPolicyMainVO> getPrivacyPolicyList(PrivacyPolicyQuery request, Pageable pageable);

    Page<PrivacyPolicySubVO> getPrivacyPolicySubList(PrivacyPolicyQuery request, Pageable pageable);

    PrivacyPolicyVO getPrivacyPolicyDetail(Long policyId, Long policySubVersion);

    PrivacyPolicyVO createPrivacyPolicy(PrivacyPolicyVO request);

    PrivacyPolicyVO createPrivacyPolicySub(PrivacyPolicyVO request);

    PrivacyPolicyVO increasePrivacyPolicy(PrivacyPolicyVO request);

    void deletePrivacyPolicy(List<PrivacyPolicyVO> policyVOList);

    void deletePrivacyPolicySub(List<PrivacyPolicyVO> policyVOList);

    void updatePrivacyPolicy(PrivacyPolicyVO request);

    void applyPrivacyPolicy(PrivacyPolicyVO request);

    Long getPrivacyPolicyPostYn(String policyVersion);

    PrivacyPolicyPostVO getPrivacyPolicyLast();

    List<PrivacyPolicyPostVO> getPrivacyPolicyHistory();

    PrivacyPolicyPostVO getPrivacyPolicy(String policyVersion);
}
