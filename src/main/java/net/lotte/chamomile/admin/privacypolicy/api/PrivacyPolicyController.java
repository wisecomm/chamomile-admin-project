package net.lotte.chamomile.admin.privacypolicy.api;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.lotte.chamomile.admin.auth.domain.AuthVO;
import net.lotte.chamomile.admin.privacypolicy.api.dto.PrivacyPolicyQuery;
import net.lotte.chamomile.admin.privacypolicy.domain.PrivacyPolicyMainVO;
import net.lotte.chamomile.admin.privacypolicy.domain.PrivacyPolicyPostVO;
import net.lotte.chamomile.admin.privacypolicy.domain.PrivacyPolicySubVO;
import net.lotte.chamomile.admin.privacypolicy.domain.PrivacyPolicyVO;
import net.lotte.chamomile.admin.privacypolicy.service.PrivacyPolicyService;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;

/**
 * <pre>
 * Admin 개인정보 처리방침 관련 REST API Controller.
 * </pre>
 *
 * @ClassName   : PrivacyPolicyController.java
 * @Description : Admin 개인정보 처리방침 관련(CRUD 등) REST API Controller.
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
@Slf4j
@RestController
@RequestMapping("/chmm/privacy-policy")
@RequiredArgsConstructor
public class PrivacyPolicyController implements PrivacyPolicyControllerDoc {

    private final PrivacyPolicyService privacyPolicyService;

    @GetMapping("/list")
    public ChamomileResponse<Page<PrivacyPolicyMainVO>> getPrivacyPolicyList(PrivacyPolicyQuery request, Pageable pageable) {
        return new ChamomileResponse<>(privacyPolicyService.getPrivacyPolicyList(request, pageable));
    }

    @GetMapping("/sub-list")
    public ChamomileResponse<Page<PrivacyPolicySubVO>> getPrivacyPolicySubList(PrivacyPolicyQuery request, Pageable pageable) {
        return new ChamomileResponse<>(privacyPolicyService.getPrivacyPolicySubList(request, pageable));
    }

    @GetMapping("/detail/{policyId}/sub/{policySubVersion}")
    public ChamomileResponse<PrivacyPolicyVO> getPrivacyPolicyDetail(@PathVariable Long policyId, @PathVariable Long policySubVersion) {
        return new ChamomileResponse<>(privacyPolicyService.getPrivacyPolicyDetail(policyId, policySubVersion));
    }

    @PostMapping("/create")
    public ChamomileResponse<PrivacyPolicyVO> createPrivacyPolicy(@Validated @RequestBody PrivacyPolicyVO request) {
        PrivacyPolicyVO result = privacyPolicyService.createPrivacyPolicy(request);
        return new ChamomileResponse<>(result);
    }

    @PostMapping("/create-sub")
    public ChamomileResponse<PrivacyPolicyVO> createPrivacyPolicySub(@Validated @RequestBody PrivacyPolicyVO request) {
        PrivacyPolicyVO result = privacyPolicyService.createPrivacyPolicySub(request);
        return new ChamomileResponse<>(result);
    }

    @PostMapping("/increase")
    public ChamomileResponse<PrivacyPolicyVO> increasePrivacyPolicy(@Validated @RequestBody PrivacyPolicyVO request) {
        PrivacyPolicyVO result = privacyPolicyService.increasePrivacyPolicy(request);
        return new ChamomileResponse<>(result);
    }

    @PostMapping("/delete")
    public ChamomileResponse<Void> deletePrivacyPolicy(@RequestBody List<PrivacyPolicyVO> policyVOList) {
        privacyPolicyService.deletePrivacyPolicy(policyVOList);
        return new ChamomileResponse<>();
    }

    @PostMapping("/delete-sub")
    public ChamomileResponse<Void> deletePrivacyPolicySub(@RequestBody List<PrivacyPolicyVO> policyVOList) {
        privacyPolicyService.deletePrivacyPolicySub(policyVOList);
        return new ChamomileResponse<>();
    }

    @PostMapping("/update")
    public ChamomileResponse<Void> updatePrivacyPolicy(@RequestBody PrivacyPolicyVO request) {
        privacyPolicyService.updatePrivacyPolicy(request);
        return new ChamomileResponse<>();
    }

    @PostMapping("/apply")
    public ChamomileResponse<Void> applyPrivacyPolicy(@RequestBody PrivacyPolicyVO request) {
        privacyPolicyService.applyPrivacyPolicy(request);
        return new ChamomileResponse<>();
    }

    /* 적용 전 게시된 개인정보 처리방침 확인 */
    @GetMapping("/posting/{policyVersion}")
    public ChamomileResponse<Long> getPrivacyPolicyPostYn(@PathVariable String policyVersion) {
        return new ChamomileResponse<>(privacyPolicyService.getPrivacyPolicyPostYn(policyVersion));
    }

    /* 게시된 개인정보 처리방침 게시 */
    @GetMapping("/last")
    public ChamomileResponse<PrivacyPolicyPostVO> getPrivacyPolicyLast() {
        return new ChamomileResponse<>(privacyPolicyService.getPrivacyPolicyLast());
    }

    @GetMapping("/history")
    public ChamomileResponse<List<PrivacyPolicyPostVO>> getPrivacyPolicyHistory() {
        return new ChamomileResponse<>(privacyPolicyService.getPrivacyPolicyHistory());
    }

    /* 게시된 개인정보 처리방침 */
    @GetMapping("/{policyVersion}")
    public ChamomileResponse<PrivacyPolicyPostVO> getPrivacyPolicy(@PathVariable String policyVersion) {
        return new ChamomileResponse<>(privacyPolicyService.getPrivacyPolicy(policyVersion));
    }

}
