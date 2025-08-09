package net.lotte.chamomile.admin.grouphierarchy.api;

import java.io.IOException;
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

import net.lotte.chamomile.admin.grouphierarchy.domain.GroupHierarchyExcelVO;
import net.lotte.chamomile.admin.grouphierarchy.domain.GroupHierarchyVO;
import net.lotte.chamomile.admin.grouphierarchy.service.GroupHierarchyService;
import net.lotte.chamomile.module.excel.ExcelExporter;
import net.lotte.chamomile.module.excel.ExcelImporter;
import net.lotte.chamomile.module.file.util.FileUploader;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;

/**
 * <pre>
 * 그룹 상하관계 컨트롤러.
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
@RequestMapping(path = "/chmm/group-hierarchy")
public class GroupHierarchyController implements GroupHierarchyControllerDoc {

    private final GroupHierarchyService groupHierarchyService;
    private final FileUploader fileUploader;

    @GetMapping(value = "/list")
    public ChamomileResponse<Page<GroupHierarchyVO>> getGroupHierarchyList(Pageable pageable) {
        Page<GroupHierarchyVO> result = groupHierarchyService.getGroupHierarchyList(pageable);
        return new ChamomileResponse<>(result);
    }

    @GetMapping(value = "/detail")
    public ChamomileResponse<List<GroupHierarchyVO>> getGroupHierarchy(@RequestParam String groupId) {
        List<GroupHierarchyVO> result = groupHierarchyService.getGroupHierarchy(groupId);
        return new ChamomileResponse<>(result);
    }

    @PostMapping(value = "/create")
    public ChamomileResponse<Void> createGroupHierarchy(@RequestBody @Validated GroupHierarchyVO request) {
        groupHierarchyService.createGroupHierarchy(request);
        return new ChamomileResponse<>();
    }

    @PostMapping(value = "/delete")
    public ChamomileResponse<Void> deleteGroupHierarchy(@RequestBody @Validated List<GroupHierarchyVO> request) {
        groupHierarchyService.deleteGroupHierarchy(request);
        return new ChamomileResponse<>();
    }

    @PostMapping(value = "/excel/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ChamomileResponse<Void> excelUpload(@RequestParam("file") MultipartFile file) {
        List<GroupHierarchyExcelVO> list = new ExcelImporter()
                .toCustomClass(file, GroupHierarchyExcelVO.class);
        groupHierarchyService.createGroupHierarchy(list);
        return new ChamomileResponse<>();
    }

    @GetMapping("/excel/download")
    public ResponseEntity<byte[]> excelDownload(Pageable pageable) throws IOException {
        Page<GroupHierarchyExcelVO> result = groupHierarchyService.getGroupHierarchyListExcel(pageable);
        ExcelExporter excelExporter = new ExcelExporter("group_hierarchy");
        excelExporter.addDataList(result.toList());
        return excelExporter.toHttpResponse();
    }

    @GetMapping("/excel/sample")
    public ResponseEntity<byte[]> excelSample() throws IOException {
        List<GroupHierarchyExcelVO> list = new ArrayList<>();
        list.add(new GroupHierarchyExcelVO());
        ExcelExporter excelExporter = new ExcelExporter("sample_group_hierarchy");
        excelExporter.addDataList(list);
        return excelExporter.toHttpResponse();
    }
}
