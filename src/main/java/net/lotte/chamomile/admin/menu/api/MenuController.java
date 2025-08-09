package net.lotte.chamomile.admin.menu.api;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.lotte.chamomile.admin.menu.api.dto.MenuQuery;
import net.lotte.chamomile.admin.menu.domain.BookmarkVO;
import net.lotte.chamomile.admin.menu.domain.MenuComponentVO;
import net.lotte.chamomile.admin.menu.domain.MenuExcelUploadVO;
import net.lotte.chamomile.admin.menu.domain.MenuVO;
import net.lotte.chamomile.admin.menu.service.MenuService;
import net.lotte.chamomile.module.excel.ExcelExporter;
import net.lotte.chamomile.module.excel.ExcelImporter;
import net.lotte.chamomile.module.file.entity.FileMetadataInfoEntity;
import net.lotte.chamomile.module.file.util.FileDownloader;
import net.lotte.chamomile.module.file.util.FileUploader;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;

/**
 * <pre>
 * 메뉴 관련 컨트롤러.
 * </pre>
 *
 * @author chaelynJang
 * @version 3.0
 * @Modification <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-09-26     chaelynJang            최초 생성
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 * @since 2023-09-26
 */
@Slf4j
@RestController
@RequestMapping(path = "/chmm/menu")
@RequiredArgsConstructor
public class MenuController implements MenuControllerDoc {
    private final MenuService menuService;

    @GetMapping("/list")
    public ChamomileResponse<Page<MenuVO>> getMenuList(MenuQuery request, Pageable pageable) {
        return new ChamomileResponse<>(menuService.findMenuList(request, pageable));
    }

    @GetMapping("/tree")
    public ChamomileResponse<List<MenuVO>> getMenuTreeData() {
        MenuVO rootMenu = menuService.readRootLevelMenu();
        List<MenuVO> allList = menuService.findMenuList(MenuQuery.builder().searchUseYn("1").build()
                , PageRequest.of(0, Integer.MAX_VALUE, Sort.by("menuLvl", "menuSeq"))).getContent();
        List<MenuVO> list = new ArrayList<>();
        list.add(rootMenu);
        list.addAll(allList);

        return new ChamomileResponse<>(list);
    }

    @PostMapping(value = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ChamomileResponse<String> createMenu(@Validated @RequestPart MenuVO request, @RequestParam(required = false) MultipartFile file){
        if(file != null) {
            // TODO: 추후에 변경 필요
            FileUploader fileUploader2 =  new FileUploader("/opt/files/common","","20MB","");
            FileMetadataInfoEntity fileVo = fileUploader2.fileUpload(file);
            request.setMenuHelpUri(fileVo.getFileMetaDataCode() + "/" + fileVo.getOriginalFileName());
        }
        String menuId = menuService.createMenu(request);
        return new ChamomileResponse<>(menuId);
    }

    @PostMapping("/delete")
    public ChamomileResponse<Void> deleteMenu(@RequestBody List<MenuVO> menuVOList) {
        menuService.deleteMenu(menuVOList);
        return new ChamomileResponse<>();
    }

    @PostMapping(value = "/update", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ChamomileResponse<Void> updateMenu(@Validated @RequestPart MenuVO request, @RequestParam(required = false) MultipartFile file) {
        if(file != null) {
            FileUploader fileUploader2 =  new FileUploader("/opt/files/common","pdf","20MB","");
            FileMetadataInfoEntity fileVo = fileUploader2.fileUpload(file);
            request.setMenuHelpUri(fileVo.getFileMetaDataCode() + "/" + fileVo.getOriginalFileName());
        }
        menuService.updateMenu(request);
        return new ChamomileResponse<>();
    }

    @GetMapping("/left")
    public ChamomileResponse<List<Map<String, Object>>> getLeftMenuList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ChamomileResponse<>(menuService.getLeftMenuList(authentication.getName()));
    }

    @PostMapping(path = "/bookmark/create")
    public ChamomileResponse<Void> createBookmark(@Validated @RequestBody BookmarkVO request) {
        menuService.createBookmark(request);
        return new ChamomileResponse<>();
    }

    @PostMapping(path = "/bookmark/delete")
    public ChamomileResponse<Void> deleteBookmark(@Validated @RequestBody BookmarkVO request) {
        menuService.deleteBookmark(request);
        return new ChamomileResponse<>();
    }

    @GetMapping("/excel/download")
    public ResponseEntity<byte[]> exportMenuExcel(MenuQuery request) throws Exception {
        String docName = URLEncoder.encode("menu", "UTF-8");
        ExcelExporter excelExporter = new ExcelExporter(docName);
        excelExporter.addDataList(menuService.findMenuList(request
                , PageRequest.of(0, Integer.MAX_VALUE)).getContent());
        return excelExporter.toHttpResponse();
    }

    @GetMapping("/excel/sample")
    public ResponseEntity<byte[]> exportMenuTemplateExcel() throws Exception {
        List<MenuExcelUploadVO> list = new ArrayList<>();
        list.add(new MenuExcelUploadVO(1, "test", "test", "test", 100
                , "test", "1", "1", "1", "test", "test", "0"));
        String docName = URLEncoder.encode("sample_menu", "UTF-8");
        ExcelExporter excelExporter = new ExcelExporter(docName);
        excelExporter.addDataList(list);
        return excelExporter.toHttpResponse();
    }

    @PostMapping(value = "/excel/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ChamomileResponse<Void> excelUpload(@RequestParam("file") MultipartFile file) {
        List<MenuExcelUploadVO> list = new ExcelImporter().toCustomClass(file, MenuExcelUploadVO.class);
        menuService.createBulkMenu(list);
        return new ChamomileResponse<>();
    }

    @GetMapping("/help-uri/file/{code}/{fileName}")
    public ResponseEntity<byte[]> downloadMenuHelpUriFile(@PathVariable String code, @PathVariable String fileName) {
        FileDownloader fileDownloader =  new FileDownloader("/opt/files/common");
        return fileDownloader.fileDownload(code, fileName);
    }

    @GetMapping("/component-check-id")
    public ResponseEntity<Boolean> checkComponentId(@RequestParam String menuId, @RequestParam String componentId) {
        return ResponseEntity.ok(menuService.checkComponentId(menuId, componentId));
    }
    
    @GetMapping("/component/list")
    public ChamomileResponse<List<MenuComponentVO>> getComponentList(@RequestParam String menuId) {
    	return new ChamomileResponse<>(menuService.findComponentList(menuId));
    }

    @PostMapping("/component/create")
    public ChamomileResponse<Void> createComponents(@Validated @RequestBody List<MenuComponentVO> request) {
        menuService.createComponents(request);
        return new ChamomileResponse<>();
    }

    @PostMapping("/component/update")
    public ChamomileResponse<Void> updateComponent(@Validated @RequestBody List<MenuComponentVO> request) {
        menuService.updateComponents(request);
        return new ChamomileResponse<>();
    }
    
    @PostMapping("/component/delete")
    public ChamomileResponse<Void> deleteComponents(@Validated @RequestBody List<MenuComponentVO> request) {
        menuService.deleteComponents(request);
        return new ChamomileResponse<>();
    }
}
