package net.lotte.chamomile.admin.groupuser.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.lotte.chamomile.admin.groupuser.api.dto.GroupUserResponse;
import net.lotte.chamomile.admin.groupuser.domain.GroupUserExcelVO;
import net.lotte.chamomile.admin.groupuser.service.GroupUserService;
import net.lotte.chamomile.module.excel.ExcelExporter;
import net.lotte.chamomile.module.excel.ExcelImporter;
import net.lotte.chamomile.module.file.util.FileUploader;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;

/**
 * <pre>
 * 그룹 유저 컨트롤러.
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
@RequestMapping(value = "/chmm/group-user")
public class GroupUserController implements GroupUserControllerDoc {

    private final GroupUserService groupUserService;
    private final FileUploader fileUploader;

    @GetMapping(value = "/detail")
    public ChamomileResponse<GroupUserResponse> getGroupUser(@RequestParam String groupId) {
        GroupUserResponse result = groupUserService.getGroupUser(groupId);
        return new ChamomileResponse<>(result);
    }

    @PostMapping(value = "/update")
    public ChamomileResponse<Void> updateGroupUser(@RequestParam String groupId, @RequestBody List<String> userIds) {
        groupUserService.updateGroupUser(groupId, userIds);
        return new ChamomileResponse<>();
    }

    @GetMapping(value = "/user/detail")
    public ChamomileResponse<GroupUserResponse> getGroupUserByUserId(@RequestParam String userId) {
        GroupUserResponse result = groupUserService.getGroupUserByUserId(userId);
        return new ChamomileResponse<>(result);
    }

    @PostMapping(value = "/user/update")
    public ChamomileResponse<Void> updateGroupUserByUserId(@RequestParam String userId, @RequestBody List<String> groupIds) {
        groupUserService.updateGroupUserByUserId(userId, groupIds);
        return new ChamomileResponse<>();
    }

    @PostMapping(value = "/excel/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ChamomileResponse<Void> excelUpload(@RequestParam("file") MultipartFile file) {
        List<GroupUserExcelVO> list = new ExcelImporter()
                .toCustomClass(file, GroupUserExcelVO.class);
        groupUserService.createGroupUser(list);
        return new ChamomileResponse<>();
    }

    @GetMapping("/excel/download")
    public ResponseEntity<byte[]> excelDownload(Pageable pageable) throws IOException {
        Page<GroupUserExcelVO> result = groupUserService.getGroupUserList(pageable);
        ExcelExporter excelExporter = new ExcelExporter("group_user");
        excelExporter.addDataList(result.toList());
        return excelExporter.toHttpResponse();
    }

    @GetMapping("/excel/sample")
    public ResponseEntity<byte[]> excelSample() throws IOException {
        List<GroupUserExcelVO> list = new ArrayList<>();
        list.add(new GroupUserExcelVO());
        ExcelExporter excelExporter = new ExcelExporter("sample_group_user");
        excelExporter.addDataList(list);
        return excelExporter.toHttpResponse();
    }
}
