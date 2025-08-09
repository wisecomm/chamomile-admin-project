package net.lotte.chamomile.admin.authhierarchy.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.lotte.chamomile.admin.auth.api.dto.AuthQuery;
import net.lotte.chamomile.admin.auth.domain.AuthVO;
import net.lotte.chamomile.admin.auth.service.AuthService;
import net.lotte.chamomile.admin.authhierarchy.api.dto.AuthHierarchyQuery;
import net.lotte.chamomile.admin.authhierarchy.domain.AuthHierarchyExcelUploadVO;
import net.lotte.chamomile.admin.authhierarchy.domain.AuthHierarchyVO;
import net.lotte.chamomile.admin.authhierarchy.service.AuthHierarchyService;
import net.lotte.chamomile.admin.authtree.RoleHierarchyTree;
import net.lotte.chamomile.module.excel.ExcelExporter;
import net.lotte.chamomile.module.excel.ExcelImporter;
import net.lotte.chamomile.module.file.util.FileUploader;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;


/**
 * <pre>
 * 권한 상하관계 관련 컨트롤러.
 * </pre>
 *
 * @author teahoPark
 * @since 2023-10-05
 * @version 3.0
 * @Modification
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-10-05     TaehoPark            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@Slf4j
@RestController
@RequestMapping(path = "/chmm/auth-hierarchy")
@RequiredArgsConstructor
public class AuthHierarchyController implements AuthHierarchyDoc {

    private final AuthHierarchyService authHierarchyService;
    private final AuthService authService;
    private final FileUploader fileUpload;

    @GetMapping(path = "/tree")
    public ChamomileResponse<Map<String, Object>> getAuthHierarchyTree(AuthHierarchyQuery authHierarchyQuery) {
        RoleHierarchyTree tree = authHierarchyService.getAuthHierarchyTree(authHierarchyQuery);
        Map<String, Object> map = new HashMap<>();
        map.put("tree", tree);
        return new ChamomileResponse<>(map);
    }

    @GetMapping(path = "/list")
    public ChamomileResponse<Page<AuthHierarchyVO>> getAuthHierarchyList(AuthHierarchyQuery authHierarchyQuery, Pageable pageable) {
        Page<AuthHierarchyVO> results = authHierarchyService.getAuthHierarchyList(authHierarchyQuery, pageable);
        return new ChamomileResponse<>(results);
    }

    @GetMapping(path = "/detail")
    public ChamomileResponse<List<AuthHierarchyVO>> getAuthHierarchyDetail(AuthHierarchyQuery authHierarchyQuery) {
        List<AuthHierarchyVO> results = authHierarchyService.getAuthHierarchyDetailList(authHierarchyQuery);
        return new ChamomileResponse<>(results);
    }

    @PostMapping(path = "/create")
    public ChamomileResponse<Void> createAuthHierarchy(@Validated @RequestBody AuthHierarchyVO authHierarchyVO) throws Exception {
        List<AuthHierarchyVO> list = new ArrayList<>();
        list.add(authHierarchyVO);
        authHierarchyService.createAuthHierarchy(list);
        return new ChamomileResponse<>();
    }

    @PostMapping(path = "/delete")
    public ChamomileResponse<Void> deleteAuthHierarchy(@RequestBody List<AuthHierarchyVO> list) throws Exception {
        authHierarchyService.deleteAuthHierarchy(list);
        return new ChamomileResponse<>();
    }

    @GetMapping(path = "/auth-list")
    public ChamomileResponse<List<AuthVO>> getAuthList(AuthQuery authQuery) {
        List<AuthVO> results = authService.getAuthList(authQuery);
        return new ChamomileResponse<>(results);
    }

    @GetMapping("/excel/download")
    public ResponseEntity<byte[]> excelDownload(AuthHierarchyQuery authHierarchyQuery) throws Exception {
        ExcelExporter excelExporter = new ExcelExporter("auth_hierarchymgmt.xlsx");

        List<AuthHierarchyVO> results = authHierarchyService.getAuthHierarchyList(authHierarchyQuery);

        // 엑셀 데이터 추가
        excelExporter.addDataList(results);
        return excelExporter.toHttpResponse();
    }

    @GetMapping("/excel/sample")
    public ResponseEntity<byte[]> excelSample() throws Exception {
        ExcelExporter excelExporter = new ExcelExporter("sample_auth_hierarchymgmt.xlsx");

        List<AuthHierarchyExcelUploadVO> list = new ArrayList<>();
        AuthHierarchyExcelUploadVO authHierarchyExcelUploadVO = AuthHierarchyExcelUploadVO.builder()
                .parentRoleId("ROLE_ADMIN_ID")
                .childRoleId("test5")
                .build();

        list.add(authHierarchyExcelUploadVO);

        excelExporter.addDataList(list);
        return excelExporter.toHttpResponse();
    }

    @PostMapping(value = "/excel/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ChamomileResponse<Void> excelUpload(@RequestParam("file") MultipartFile file) {
        List<AuthHierarchyExcelUploadVO> list = new ExcelImporter().toCustomClass(file, AuthHierarchyExcelUploadVO.class);
        authHierarchyService.createExcelAuthHierarchy(list);

        return new ChamomileResponse<>();
    }
}
