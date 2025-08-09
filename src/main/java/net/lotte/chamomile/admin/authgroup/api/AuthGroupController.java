package net.lotte.chamomile.admin.authgroup.api;

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

import net.lotte.chamomile.admin.authgroup.api.dto.AuthGroupQuery;
import net.lotte.chamomile.admin.authgroup.domain.AuthGroupExcelVO;
import net.lotte.chamomile.admin.authgroup.service.AuthGroupService;
import net.lotte.chamomile.admin.authtree.RoleHierarchyTree;
import net.lotte.chamomile.module.excel.ExcelImporter;
import net.lotte.chamomile.module.file.util.FileUploader;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;
/**
 * <pre>
 * 권한 그룹 Controller.
 * </pre>
 *
 * @author MoonHKLee
 * @since 2023-11-01
 * @version 3.0
 * @see AuthGroupControllerDoc
 * @Modification
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-11-01     MoonHKLee            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/chmm/auth-group")
public class AuthGroupController implements AuthGroupControllerDoc {

    private final AuthGroupService authGroupService;
    private final FileUploader fileUploader;
    @GetMapping(value = "/detail")
    public ChamomileResponse<RoleHierarchyTree> getAuthGroupDetail(AuthGroupQuery request){
        RoleHierarchyTree result = authGroupService.getAuthGroup(request);
        return new ChamomileResponse<>(result);
    }

    @PostMapping(value = "/update")
    public ChamomileResponse<Void> updateAuthGroup(@RequestParam String groupId, @RequestBody List<String> roleIds) {
        authGroupService.updateAuthGroup(groupId, roleIds);
        return new ChamomileResponse<>();
    }

    @PostMapping(value = "/excel/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ChamomileResponse<Void> excelUpload(@RequestParam("file") MultipartFile file) {
        List<AuthGroupExcelVO> list = new ExcelImporter()
                .toCustomClass(file, AuthGroupExcelVO.class);
        authGroupService.createAuthGroup(list);
        return new ChamomileResponse<>();
    }
}
