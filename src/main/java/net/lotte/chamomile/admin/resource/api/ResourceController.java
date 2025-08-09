package net.lotte.chamomile.admin.resource.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

import net.lotte.chamomile.admin.resource.api.dto.ResourceQuery;
import net.lotte.chamomile.admin.resource.domain.BatchExcelMapper;
import net.lotte.chamomile.admin.resource.domain.ResourceExcelVO;
import net.lotte.chamomile.admin.resource.domain.ResourceVO;
import net.lotte.chamomile.admin.resource.service.ResourceService;
import net.lotte.chamomile.module.excel.ExcelExporter;
import net.lotte.chamomile.module.excel.ExcelImporter;
import net.lotte.chamomile.module.excel.handler.ExcelStreamResultHandler;
import net.lotte.chamomile.module.file.util.FileUploader;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;


/**
 * <pre>
 * 리소스 관련 컨트롤러.
 * </pre>
 *
 * @author MoonHKLee
 * @version 3.0
 * @Modification
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-09-15     MoonHKLee            최초 생성
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 * @since 2023-09-15
 */
@Slf4j
@RestController
@RequestMapping(path = "/chmm/resource")
@RequiredArgsConstructor
public class ResourceController implements ResourceControllerDoc {

    private final ResourceService resourceService;
    private final FileUploader fileUploader;

    @GetMapping(path = "/testBatchExcel")
    public Void testBatchExcelResource(HttpServletResponse response) {
        ExcelStreamResultHandler handler = new ExcelStreamResultHandler(response,"test.xlsx");
        BatchExcelMapper batchExcelMapper = new BatchExcelMapper(handler, "net.lotte.chamomile.admin.resource.domain.ResourceMapper.findResourceListDataExcel2");
        batchExcelMapper.writeExcelFileOnHttpResponse();
        return null;
    }

    @GetMapping(path = "/list")
    public ChamomileResponse<Page<ResourceVO>> getResourceList(ResourceQuery request, Pageable pageable) {
        Page<ResourceVO> results = resourceService.getResourceList(request, pageable);
        return new ChamomileResponse<>(results);
    }

    @GetMapping(path = "/detail")
    public ChamomileResponse<ResourceVO> getResource(@RequestParam String resourceId) {
        ResourceVO result = resourceService.getResource(resourceId);
        return new ChamomileResponse<>(result);
    }

    @GetMapping(path = "/check")
    public ChamomileResponse<Boolean> getResourceCheck(@RequestParam String resourceId) {
        Boolean result = resourceService.getResourceCheck(resourceId);
        return new ChamomileResponse<>(result);
    }

    @PostMapping(path = "/create")
    public ChamomileResponse<Void> createResource(@Validated @RequestBody ResourceVO request) {
        resourceService.createResource(request);
        return new ChamomileResponse<>();
    }

    @PostMapping(path = "/update")
    public ChamomileResponse<Void> updateResource(@Validated @RequestBody ResourceVO request) {
        resourceService.updateResource(request);
        return new ChamomileResponse<>();
    }

    @PostMapping(path = "/delete")
    public ChamomileResponse<Void> deleteResource(@RequestBody List<ResourceVO> request) {
        resourceService.deleteResource(request);
        return new ChamomileResponse<>();
    }

    @PostMapping(value = "/excel/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ChamomileResponse<Void> excelUpload(@RequestParam("file") MultipartFile file) {
        List<ResourceExcelVO> list = new ExcelImporter().toCustomClass(file, ResourceExcelVO.class);
        resourceService.createResource(list);
        return new ChamomileResponse<>();
    }

    @GetMapping("/excel/download")
    public ResponseEntity<byte[]> excelDownload(ResourceQuery request, Pageable pageable) throws IOException {
        pageable = PageRequest.of(0,Integer.MAX_VALUE);
        Page<ResourceExcelVO> result = resourceService.getResourceListExcel(request, pageable);
        ExcelExporter excelExporter = new ExcelExporter("resource");
        excelExporter.addDataList(result.toList());
        return excelExporter.toHttpResponse();
    }

    @GetMapping("/excel/sample")
    public ResponseEntity<byte[]> excelSample() throws IOException {
        List<ResourceExcelVO> list = new ArrayList<>();
        list.add(new ResourceExcelVO("TEST_RESOURCE_ID","TEST_RESOURCE_DESC","GET","TEST_RESOURCE_NAME","/TEST",1234,"1"));
        ExcelExporter excelExporter = new ExcelExporter("sample_resource");
        excelExporter.addDataList(list);
        return excelExporter.toHttpResponse();
    }
}
