package net.lotte.chamomile.admin.privacypolicy.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import net.lotte.chamomile.admin.privacypolicy.api.dto.PrivacyPolicyQuery;
import net.lotte.chamomile.admin.privacypolicy.domain.PrivacyPolicyMainVO;
import net.lotte.chamomile.admin.privacypolicy.domain.PrivacyPolicyMapper;
import net.lotte.chamomile.admin.privacypolicy.domain.PrivacyPolicyPostVO;
import net.lotte.chamomile.admin.privacypolicy.domain.PrivacyPolicySubVO;
import net.lotte.chamomile.admin.privacypolicy.domain.PrivacyPolicyVO;
import net.lotte.chamomile.admin.common.exception.code.AdminExceptionCode;
import net.lotte.chamomile.core.exception.BusinessException;
import net.lotte.chamomile.module.database.mybatis.batch.BatchRequest;

/**
 * <pre>
 * 개인정보 처리방침 관련 서비스 인터페이스 구현체.
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
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PrivacyPolicyServiceImpl implements PrivacyPolicyService {
    private final PrivacyPolicyMapper privacyPolicyMapper;
    private final String defaultVersion = "1.0";
    private final Long defaultSubVersion = 1L;

    @Override
    @Transactional(readOnly = true)
    public Page<PrivacyPolicyMainVO> getPrivacyPolicyList(PrivacyPolicyQuery request, Pageable pageable) {
        return privacyPolicyMapper.findPrivacyPolicyList(request, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrivacyPolicySubVO> getPrivacyPolicySubList(PrivacyPolicyQuery request, Pageable pageable) {
        return privacyPolicyMapper.findPrivacyPolicySubList(request, pageable);
    }

    @Override
    public PrivacyPolicyVO getPrivacyPolicyDetail(Long policyId, Long policySubVersion) {
        return privacyPolicyMapper.findPrivacyPolicyDetail(policyId, policySubVersion)
                .orElseThrow(() -> new NoSuchElementException("해당 정책이 존재하지 않습니다."));
    }

    /**
     * 정책 신규 등록
     * @param request
     */
    @Override
    public PrivacyPolicyVO createPrivacyPolicy(PrivacyPolicyVO request) {
        if(ObjectUtils.isEmpty(request)) {
            throw new BusinessException(AdminExceptionCode.ServerError);
        }
        request.onCreate();

        Long lastPolicyId = privacyPolicyMapper.findPrivacyPolicyLastId();
        if (lastPolicyId == null) {
            lastPolicyId = 1L;
        } else {
            lastPolicyId++;
        }
        request.setPolicyId(lastPolicyId);

        // 버전 등록
        String lastPolicyVersion = privacyPolicyMapper.findPrivacyPolicyLastVersion(null);
        String policyVersion = StringUtils.defaultString(lastPolicyVersion, defaultVersion);
        if (lastPolicyVersion != null) {
            int index = policyVersion.indexOf(".");
            if (index != -1) {
                String strFirst = policyVersion.substring(0, index);
                int numFirst = Integer.parseInt(strFirst);
                numFirst++;
                policyVersion = numFirst + ".0";
            } else {
                throw new IllegalArgumentException("Invalid version format: " + policyVersion);
            }
        }
        request.setPolicyVersion(policyVersion);
        // 서브 버전 등록
        request.setPolicySubVersion(defaultSubVersion);
        request.setPostYn("0");
        request.setApplyYn("0");

        privacyPolicyMapper.insertPrivacyPolicy(request);
        privacyPolicyMapper.insertPrivacyPolicySub(request);

        return request;
    }

    /**
     * 정책 서브 버전 추가
     * @param request
     */
    @Override
    public PrivacyPolicyVO createPrivacyPolicySub(PrivacyPolicyVO request) {
        if(ObjectUtils.isEmpty(request)) {
            throw new BusinessException(AdminExceptionCode.ServerError);
        }
        request.onCreate();
        // 서브 버전 등록
        long policyId = request.getPolicyId();
        long policySubVersion = privacyPolicyMapper.findPrivacyPolicyLastSubVersion(policyId) == 0 ? defaultSubVersion : privacyPolicyMapper.findPrivacyPolicyLastSubVersion(policyId);

        policySubVersion++;

        // 서브 버전 등록
        request.setPolicySubVersion(policySubVersion);

        request.setApplyYn("0");
        privacyPolicyMapper.insertPrivacyPolicySub(request);

        return request;
    }

    /**
     * 정책 하위 버전업
     * @param request
     */
    @Override
    public PrivacyPolicyVO increasePrivacyPolicy(PrivacyPolicyVO request) {
        if(ObjectUtils.isEmpty(request)) {
            throw new BusinessException(AdminExceptionCode.ServerError);
        }
        request.onCreate();
        Long lastPolicyId = privacyPolicyMapper.findPrivacyPolicyLastId();
        if (lastPolicyId == null) {
            lastPolicyId = 1L;
        } else {
            lastPolicyId++;
        }
        request.setPolicyId(lastPolicyId);

        String reqVersion = request.getPolicyVersion();

        // 버전 등록
        String lastPolicyVersion = privacyPolicyMapper.findPrivacyPolicyLastVersion(reqVersion);
        String policyVersion = StringUtils.defaultString(lastPolicyVersion, defaultVersion);
        if (lastPolicyVersion != null) {
            int index = policyVersion.indexOf(".");
            String[] arrVersion = policyVersion.split("\\.");
            if (index != -1 && arrVersion.length == 2) {
                String strFirst = arrVersion[0];
                String strSecond = arrVersion[1];
                int numSecond = Integer.parseInt(strSecond);
                numSecond++;
                policyVersion = strFirst.concat(".").concat(String.valueOf(numSecond));
            } else {
                throw new IllegalArgumentException("Invalid version format: " + policyVersion);
            }
        }
        request.setPolicyVersion(policyVersion);
        // 서브 버전 등록
        request.setPolicySubVersion(defaultSubVersion);

        request.setPostYn("0");
        request.setApplyYn("0");

        privacyPolicyMapper.insertPrivacyPolicy(request);
        privacyPolicyMapper.insertPrivacyPolicySub(request);

        return request;
    }

    @Override
    public void deletePrivacyPolicy(List<PrivacyPolicyVO> policyVOList) {
        // 개인정보 처리방침 삭제
        if (CollectionUtils.isEmpty(policyVOList)) {
            throw new BusinessException(AdminExceptionCode.ServerError);
        }
        BatchRequest batchRequest = new BatchRequest(1000);
        privacyPolicyMapper.deletePrivacyPolicySubAll(policyVOList, batchRequest);
        privacyPolicyMapper.deletePrivacyPolicy(policyVOList, batchRequest);
    }

    @Override
    public void deletePrivacyPolicySub(List<PrivacyPolicyVO> policyVOList) {
        // 개인정보 처리방침 Sub 삭제
        BatchRequest batchRequest = new BatchRequest(1000);
        privacyPolicyMapper.deletePrivacyPolicySub(policyVOList, batchRequest);
    }

    @Override
    public void updatePrivacyPolicy(PrivacyPolicyVO request) {
        request.onUpdate();
        privacyPolicyMapper.updatePrivacyPolicy(request);
        privacyPolicyMapper.updatePrivacyPolicySub(request);
    }

    /**
     * 적용
     * @param request
     */
    @Override
    public void applyPrivacyPolicy(PrivacyPolicyVO request) {
        request.onUpdate();

        privacyPolicyMapper.applyPrivacyPolicy(request);
        privacyPolicyMapper.applyCancelPrivacyPolicySub(request);
        privacyPolicyMapper.applyPrivacyPolicySub(request);
    }

    @Override
    public Long getPrivacyPolicyPostYn(String policyVersion) {
        String currentDtm = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return privacyPolicyMapper.findPrivacyPolicyPostYn(currentDtm, policyVersion);
    }

    /**
     * 현행중인 정책 조회 (최종 버전)
     * @return PrivacyPolicyPostVO
     */
    @Override
    public PrivacyPolicyPostVO getPrivacyPolicyLast() {
        return privacyPolicyMapper.findPrivacyPolicyLast();
    }

    /**
     * 게시된 정책 이력 목록 조회
     * @return List<PrivacyPolicyPostVO>
     */
    @Override
    public List<PrivacyPolicyPostVO> getPrivacyPolicyHistory() {
        List<PrivacyPolicyPostVO> result = privacyPolicyMapper.findPrivacyPolicyHistory();
        for (int i = 0; i < result.size(); i++) {
            if (i == 0) {
                result.get(i).setPolicyEndDt("");
                continue;
            }
            String dateString = result.get(i-1).getPolicyStartDt();
            LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate previousDay = date.minusDays(1);
            String policyEndDt = previousDay.format(DateTimeFormatter.ISO_LOCAL_DATE);

            result.get(i).setPolicyEndDt(policyEndDt);
        }
        return result;
    }

    /**
     * 이전 게시된 정책 조회
     * @return PrivacyPolicyPostVO
     */
    @Override
    public PrivacyPolicyPostVO getPrivacyPolicy(String policyVersion) {
        policyVersion = policyVersion.replace("_",".");
        return privacyPolicyMapper.findPrivacyPolicy(policyVersion);
    }

}
