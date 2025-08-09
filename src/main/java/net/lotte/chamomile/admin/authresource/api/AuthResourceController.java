package net.lotte.chamomile.admin.authresource.api;

import java.io.IOException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.lotte.chamomile.admin.authresource.api.dto.AuthResourceCommand;
import net.lotte.chamomile.admin.authresource.api.dto.AuthResourceQuery;
import net.lotte.chamomile.admin.authresource.api.dto.AuthResourceResponse;
import net.lotte.chamomile.admin.authresource.api.dto.ResourceRoleResponse;
import net.lotte.chamomile.admin.authresource.domain.AuthResourceExcelVO;
import net.lotte.chamomile.admin.authresource.service.AuthResourceService;
import net.lotte.chamomile.admin.resource.api.dto.ResourceQuery;
import net.lotte.chamomile.admin.resource.domain.ResourceVO;
import net.lotte.chamomile.admin.resource.service.ResourceService;
import net.lotte.chamomile.module.excel.ExcelExporter;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;

/**
 * <pre>
 * 리소스 권한 컨트롤러.
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
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/chmm/auth-resource")
public class AuthResourceController implements AuthResourceControllerDoc {

    private final AuthResourceService authResourceService;

    @GetMapping(value = "/list")
    public ChamomileResponse<Page<ResourceRoleResponse>> getAuthResourceList(@Validated ResourceQuery request, Pageable pageable) {
        Page<ResourceRoleResponse> results = authResourceService.getAuthResourceList(request, pageable);
        return new ChamomileResponse<>(results);
    }

    @GetMapping(value = "/detail")
    public ChamomileResponse<AuthResourceResponse> getAuthResource(@Validated AuthResourceQuery request) {
        AuthResourceResponse result = authResourceService.getAuthResource(request);
        return new ChamomileResponse<>(result);
    }

    @PostMapping(value = "/update")
    public ChamomileResponse<Void> updateAuthResource(@RequestBody @Validated AuthResourceCommand request) {
        authResourceService.updateAuthResource(request);
        return new ChamomileResponse<>();
    }

    @GetMapping("/excel/download")
    public ResponseEntity<byte[]> excelDownload(ResourceQuery request, Pageable pageable) throws IOException {
        Page<AuthResourceExcelVO> result = authResourceService.getAuthResourceListExcel(request, pageable);
        ExcelExporter excelExporter = new ExcelExporter("authResource");
        excelExporter.addDataList(result.toList());
        return excelExporter.toHttpResponse();
    }
}
