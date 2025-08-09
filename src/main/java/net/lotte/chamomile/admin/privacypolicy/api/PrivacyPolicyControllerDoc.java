package net.lotte.chamomile.admin.privacypolicy.api;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import net.lotte.chamomile.admin.privacypolicy.api.dto.PrivacyPolicyQuery;
import net.lotte.chamomile.admin.privacypolicy.domain.PrivacyPolicyMainVO;
import net.lotte.chamomile.admin.privacypolicy.domain.PrivacyPolicyPostVO;
import net.lotte.chamomile.admin.privacypolicy.domain.PrivacyPolicySubVO;
import net.lotte.chamomile.admin.privacypolicy.domain.PrivacyPolicyVO;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;

/**
 * <pre>
 * Admin 개인정보 처리방침 관련 REST API Swagger Doc.
 * </pre>
 *
 * @ClassName   : PrivacyPolicyControllerDoc.java
 * @Description : Admin 개인정보 처리방침 관련(CRUD 등) REST API Swagger Doc.
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
@Tag(name = "캐모마일 어드민 개인정보 처리방침 API")
public interface PrivacyPolicyControllerDoc {
    @Operation(summary = "개인정보 처리방침 목록 요청", description = "개인정보 처리방침 목록을 조회합니다.")
    ChamomileResponse<Page<PrivacyPolicyMainVO>> getPrivacyPolicyList(PrivacyPolicyQuery request, Pageable pageable);

    @Operation(summary = "개인정보 처리방침 서브 목록 요청", description = "개인정보 처리방침 서브 목록을 조회합니다.")
    ChamomileResponse<Page<PrivacyPolicySubVO>> getPrivacyPolicySubList(PrivacyPolicyQuery request, Pageable pageable);

    @Operation(summary = "개인정보 처리방침 상세 조회 요청", description = "개인정보 처리방침 상세를 조회합니다.")
    public ChamomileResponse<PrivacyPolicyVO> getPrivacyPolicyDetail(Long policyId, Long policySubVersion);

    @Operation(summary = "개인정보 처리방침 생성 요청", description = "개인정보 처리방침을 생성합니다.")
    ChamomileResponse<PrivacyPolicyVO> createPrivacyPolicy(PrivacyPolicyVO request);

    @Operation(summary = "개인정보 처리방침 하위 버전 생성 요청", description = "개인정보 처리방침을 하위 버전을 생성합니다.")
    ChamomileResponse<PrivacyPolicyVO> createPrivacyPolicySub(PrivacyPolicyVO request);

    @Operation(summary = "개인정보 처리방침 생성 요청", description = "개인정보 처리방침을 생성합니다.")
    ChamomileResponse<PrivacyPolicyVO> increasePrivacyPolicy(PrivacyPolicyVO request);

    @Operation(summary = "개인정보 처리방침 삭제 요청", description = "개인정보 처리방침을 삭제합니다.")
    ChamomileResponse<Void> deletePrivacyPolicy(List<PrivacyPolicyVO> policyVOList);

    @Operation(summary = "개인정보 처리방침 삭제 요청", description = "개인정보 처리방침 Sub 를 삭제합니다.")
    ChamomileResponse<Void> deletePrivacyPolicySub(List<PrivacyPolicyVO> policyVOList);

    @Operation(summary = "개인정보 처리방침 수정 요청", description = "개인정보 처리방침을 수정합니다.")
    ChamomileResponse<Void> updatePrivacyPolicy(PrivacyPolicyVO request);

    @Operation(summary = "개인정보 처리방침 적용 요청", description = "개인정보 처리방침을 적용합니다.")
    ChamomileResponse<Void> applyPrivacyPolicy(@RequestBody PrivacyPolicyVO request);

    @Operation(summary = "적용 전 게시된 개인정보 처리방침 확인", description = "적용 전 게시된 개인정보 처리방침 확인합니다.")
    ChamomileResponse<Long> getPrivacyPolicyPostYn(String policyVersion);

    @Operation(summary = "개인정보 처리방침 현행 버전을 조회 요청", description = "개인정보 처리방침 현행 버전을 조회합니다.")
    ChamomileResponse<PrivacyPolicyPostVO> getPrivacyPolicyLast();

    @Operation(summary = "개인정보 처리방침 게시된 이력 요청", description = "개인정보 처리방침을 게시된 이력을 조회합니다.")
    ChamomileResponse<List<PrivacyPolicyPostVO>> getPrivacyPolicyHistory();

    @Operation(summary = "정책버전에 대한 개인정보 처리방침 조회 요청", description = "정책버전에 대한 개인정보 처리방침을 조회합니다.")
    ChamomileResponse<PrivacyPolicyPostVO> getPrivacyPolicy(@PathVariable String policyVersion);
}
