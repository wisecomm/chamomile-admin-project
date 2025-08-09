package net.lotte.chamomile.admin.privacypolicy.domain;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.lotte.chamomile.admin.privacypolicy.api.dto.PrivacyPolicyQuery;
import net.lotte.chamomile.module.database.mybatis.batch.BatchRequest;

/**
 * <pre>
 * 개인정보 처리방침 관련 Mapper.
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
@Mapper
public interface PrivacyPolicyMapper {
    Page<PrivacyPolicyMainVO> findPrivacyPolicyList(PrivacyPolicyQuery request, Pageable pageable);

    Page<PrivacyPolicySubVO> findPrivacyPolicySubList(PrivacyPolicyQuery request, Pageable pageable);

    Optional<PrivacyPolicyVO> findPrivacyPolicyDetail(Long policyId, Long policySubVersion);


    void insertPrivacyPolicy(PrivacyPolicyVO request);
    void insertPrivacyPolicySub(PrivacyPolicyVO request);

    void deletePrivacyPolicy(List<PrivacyPolicyVO> policyVOList, BatchRequest batchRequest);
    void deletePrivacyPolicySub(List<PrivacyPolicyVO> policyVOList, BatchRequest batchRequest);
    void deletePrivacyPolicySubAll(List<PrivacyPolicyVO> policyVOList, BatchRequest batchRequest);

    void updatePrivacyPolicy(PrivacyPolicyVO request);
    void updatePrivacyPolicySub(PrivacyPolicyVO request);

    Long findPrivacyPolicyLastId();

    String findPrivacyPolicyLastVersion(String policyVersion);
    Long findPrivacyPolicyLastSubVersion(Long policyId);

    void applyPrivacyPolicy(PrivacyPolicyVO request);

    void applyPrivacyPolicySub(PrivacyPolicyVO request);
    void applyCancelPrivacyPolicySub(PrivacyPolicyVO request);

    Long findPrivacyPolicyPostYn(String currentDtm, String policyVersion);

    PrivacyPolicyPostVO findPrivacyPolicyLast();

    List<PrivacyPolicyPostVO> findPrivacyPolicyHistory();

    PrivacyPolicyPostVO findPrivacyPolicy(String policyVersion);
}
