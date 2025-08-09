package net.lotte.chamomile.admin.authuser.api;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.lotte.chamomile.admin.authtree.RoleHierarchyTree;
import net.lotte.chamomile.admin.authuser.api.dto.AuthUserQuery;
import net.lotte.chamomile.admin.authuser.domain.AuthUserExcelUploadVO;
import net.lotte.chamomile.admin.authuser.service.AuthUserService;
import net.lotte.chamomile.module.excel.ExcelImporter;
import net.lotte.chamomile.module.file.util.FileUploader;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;

/**
 * <pre>
 * 유저 권한 컨트롤러.
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
@RequestMapping(path = "/chmm/auth-user")
public class AuthUserController implements AuthUserControllerDoc {

    private final AuthUserService authUserService;
    private final FileUploader fileUploader;
    @GetMapping(value = "/detail")
    public ChamomileResponse<RoleHierarchyTree> getAuthUser(AuthUserQuery request) {

        RoleHierarchyTree result = authUserService.getAuthUser(request);
        return new ChamomileResponse<>(result);
    }

    @PostMapping(value = "/update")
    public ChamomileResponse<Void> updateAuthUser(@RequestParam String userId, @RequestBody List<String> roleIds) {
        authUserService.updateAuthUser(userId, roleIds);
        return new ChamomileResponse<>();
    }

    @PostMapping(value = "/excel/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ChamomileResponse<Void> excelUpload(@RequestParam("file") MultipartFile file) {
        List<AuthUserExcelUploadVO> list = new ExcelImporter()
                .toCustomClass(file, AuthUserExcelUploadVO.class);
        authUserService.createAuthUser(list);
        return new ChamomileResponse<>();
    }
}
