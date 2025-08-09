package net.lotte.chamomile.admin.auth.api;

import java.util.ArrayList;
import java.util.List;

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
import net.lotte.chamomile.admin.auth.domain.AuthExcelUploadVO;
import net.lotte.chamomile.admin.auth.domain.AuthVO;
import net.lotte.chamomile.admin.auth.service.AuthService;
import net.lotte.chamomile.module.excel.ExcelExporter;
import net.lotte.chamomile.module.excel.ExcelImporter;
import net.lotte.chamomile.module.file.util.FileUploader;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;


/**
 * <pre>
 * 권한 관련 컨트롤러.
 * </pre>
 *
 * @author teahoPark
 * @since 2023-09-26
 * @version 3.0
 * @Modification
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-09-26     TaehoPark            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@Slf4j
@RestController
@RequestMapping(path = "/chmm/auth")
@RequiredArgsConstructor
public class AuthController implements AuthControllerDoc {

    private final AuthService authService;
    private final FileUploader fileUpload;

    @GetMapping(path = "/list")
    public ChamomileResponse<Page<AuthVO>> getAuthList(AuthQuery authQuery, Pageable pageable) {
        Page<AuthVO> results = authService.getAuthList(authQuery, pageable);
        return new ChamomileResponse<>(results);
    }

    @PostMapping(path = "/create")
    public ChamomileResponse<Void> createAuth(@Validated @RequestBody AuthVO request) throws Exception {
        authService.createAuth(request);
        return new ChamomileResponse<>();
    }

    @PostMapping(path = "/update")
    public ChamomileResponse<Void> updateAuth(@Validated @RequestBody AuthVO request) throws Exception {
        authService.updateAuth(request);
        return new ChamomileResponse<>();
    }

    @PostMapping(path = "/delete")
    public ChamomileResponse<Void> deleteAuth(@RequestBody List<AuthVO> authVOList) throws Exception {
        authService.deleteAuth(authVOList);
        return new ChamomileResponse<>();
    }

    @PostMapping(path = "/auth-check-id")
    public ChamomileResponse<AuthVO> authCheckId(@RequestBody AuthQuery authQuery) throws Exception {
        AuthVO authVO = new AuthVO();
        authVO.setRoleId(authQuery.getSearchRoleId());
        authVO.setValid(authService.authIdCheck(authVO) == 0);
        return new ChamomileResponse<>(authVO);
    }

    @GetMapping("/excel/download")
    public ResponseEntity<byte[]> excelDownload(AuthQuery authQuery) throws Exception {
        ExcelExporter excelExporter = new ExcelExporter("authmgmt.xlsx");

        List<AuthVO> results = authService.getAuthList(authQuery);

        // 엑셀 데이터 추가
        excelExporter.addDataList(results);
        return excelExporter.toHttpResponse();
    }

    @GetMapping("/excel/sample")
    public ResponseEntity<byte[]> excelSample() throws Exception {
        ExcelExporter excelExporter = new ExcelExporter("sample_authmgmt.xlsx");

        List<AuthExcelUploadVO> list = new ArrayList<>();
        AuthExcelUploadVO authExcelUploadVO = AuthExcelUploadVO.builder()
                .roleId("test2")
                .roleName("테스트2엑셀")
                .roleDesc("테스트2엑셀")
                .roleStartDt("20180102")
                .roleEndDt("20240103")
                .useYn("1")
                .build();

        list.add(authExcelUploadVO);

        excelExporter.addDataList(list);
        return excelExporter.toHttpResponse();
    }

    @PostMapping(value = "/excel/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ChamomileResponse<Void> excelUpload(@RequestParam("file") MultipartFile file) {
        List<AuthExcelUploadVO> list = new ExcelImporter().toCustomClass(file, AuthExcelUploadVO.class);
        authService.createAuth(list);

        return new ChamomileResponse<>();
    }
}
