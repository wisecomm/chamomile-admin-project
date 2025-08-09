package net.lotte.chamomile.admin.commoncode.api;

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
import org.springframework.data.web.SortDefault;
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

import net.lotte.chamomile.admin.commoncode.api.dto.CategoryCommand;
import net.lotte.chamomile.admin.commoncode.api.dto.CategoryQuery;
import net.lotte.chamomile.admin.commoncode.api.dto.CodeCommand;
import net.lotte.chamomile.admin.commoncode.api.dto.CodeItemCommand;
import net.lotte.chamomile.admin.commoncode.api.dto.CodeItemQuery;
import net.lotte.chamomile.admin.commoncode.api.dto.CodeQuery;
import net.lotte.chamomile.admin.commoncode.api.dto.CommonCodeQuery;
import net.lotte.chamomile.admin.commoncode.domain.CategoryExcelUploadVO;
import net.lotte.chamomile.admin.commoncode.domain.CategoryVO;
import net.lotte.chamomile.admin.commoncode.domain.CodeExcelUploadVO;
import net.lotte.chamomile.admin.commoncode.domain.CodeItemExcelUploadVO;
import net.lotte.chamomile.admin.commoncode.domain.CodeItemVO;
import net.lotte.chamomile.admin.commoncode.domain.CodeVO;
import net.lotte.chamomile.admin.commoncode.service.CommonCodeService;
import net.lotte.chamomile.module.excel.ExcelExporter;
import net.lotte.chamomile.module.excel.ExcelImporter;
import net.lotte.chamomile.module.file.util.FileUploader;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;

/**
 * <pre>
 * Admin 공통코드 관련 REST API Controller.
 * </pre>
 *
 * @ClassName   : CommonCodeController.java
 * @Description : Admin 공통코드 관련(CRUD 등) REST API Controller.
 * @author chaelynJang
 * @since 2023.09.13
 * @version 3.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023.09.13     chaelynJang            최초 생성
 *
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@Slf4j
@RestController
@RequestMapping("/chmm/common-code")
@RequiredArgsConstructor
public class CommonCodeController implements CommonCodeControllerDoc {

    private final CommonCodeService commonCodeService;
    private final FileUploader fileUploader;

    @GetMapping("/list/all")
    public ChamomileResponse<Map<String, Object>> getCommonCodeAllList() {
        return new ChamomileResponse<>(commonCodeService.getCommonCodeAllList());
    }

    @GetMapping("/category/list")
    public ChamomileResponse<Page<CategoryVO>> getCategoryList(@Validated CategoryQuery request
            , @SortDefault(sort = "orderNum", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ChamomileResponse<>(commonCodeService.getCategoryList(request, pageable));
    }

    @GetMapping("/code/list")
    public ChamomileResponse<Page<CodeVO>> getCodeList(@Validated CodeQuery request
            , @SortDefault(sort = "orderNum", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ChamomileResponse<>(commonCodeService.getCodeList(request, pageable));
    }

    @GetMapping("/code-item/list")
    public ChamomileResponse<Page<CodeItemVO>> getCodeList(@Validated CodeItemQuery request
            , @SortDefault(sort = "orderNum", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ChamomileResponse<>(commonCodeService.getCodeItemList(request, pageable));
    }

    @PostMapping("/category/create")
    public ChamomileResponse<Void> createCategory(@Validated @RequestBody CategoryCommand categoryCommand) {
        commonCodeService.categorySave(categoryCommand);
        return new ChamomileResponse<>();
    }

    @PostMapping("/code/create")
    public ChamomileResponse<Void> createCode(@Validated @RequestBody CodeCommand codeCommand) {
        commonCodeService.codeSave(codeCommand);
        return new ChamomileResponse<>();
    }

    @PostMapping("/code-item/create")
    public ChamomileResponse<Void> createCodeItem(@Validated @RequestBody CodeItemCommand codeItemCommand) {
        commonCodeService.codeItemSave(codeItemCommand);
        return new ChamomileResponse<>();
    }

    @PostMapping("/category/update")
    public ChamomileResponse<Void> updateCategory(@RequestBody CategoryCommand categoryCommand) {
        commonCodeService.updateCategory(categoryCommand);
        return new ChamomileResponse<>();
    }

    @PostMapping("/category/order/update")
    public ChamomileResponse<Void> updateCategoryOrder(@RequestBody List<CategoryCommand> categoryCommand) {
        commonCodeService.updateCategoryOrder(categoryCommand);
        return new ChamomileResponse<>();
    }

    @PostMapping("/code/update")
    public ChamomileResponse<Void> updateCode(@RequestBody CodeCommand codeCommand) {
        commonCodeService.updateCode(codeCommand);
        return new ChamomileResponse<>();
    }

    @PostMapping("/code/order/update")
    public ChamomileResponse<Void> updateCodeOrder(@RequestBody List<CodeCommand> codeCommands) {
        commonCodeService.updateCodeOrder(codeCommands);
        return new ChamomileResponse<>();
    }

    @PostMapping("/code-item/update")
    public ChamomileResponse<Void> updateCodeItem(@RequestBody CodeItemCommand codeItemCommand) {
        commonCodeService.updateCodeItem(codeItemCommand);
        return new ChamomileResponse<>();
    }

    @PostMapping("/code-item/order/update")
    public ChamomileResponse<Void> updateCodeItemOrder(@RequestBody List<CodeItemCommand> codeItemCommands) {
        commonCodeService.updateCodeItemOrder(codeItemCommands);
        return new ChamomileResponse<>();
    }

    @PostMapping("/category/delete")
    public ChamomileResponse<Void> deleteCategory(@RequestBody List<CategoryCommand> categoryCommands) {
        commonCodeService.deleteCategory(categoryCommands);
        return new ChamomileResponse<>();
    }

    @PostMapping("/code/delete")
    public ChamomileResponse<Void> deleteCode(@RequestBody List<CodeCommand> codeCommands) {
        commonCodeService.deleteCode(codeCommands);
        return new ChamomileResponse<>();
    }

    @PostMapping("/code-item/delete")
    public ChamomileResponse<Void> deleteCodeItem(@RequestBody List<CodeItemCommand> codeItemCommands) {
        commonCodeService.deleteCodeItem(codeItemCommands);
        return new ChamomileResponse<>();
    }

     @GetMapping("/category/excel/download")
     public ResponseEntity<byte[]> exportCategoryExcel(@Validated CategoryQuery request) throws Exception {
         String docName = URLEncoder.encode("categoryList", "UTF-8");
         ExcelExporter excelExporter = new ExcelExporter(docName);
         excelExporter.addDataList(commonCodeService.getCategoryList(request
                 , PageRequest.of(0, Integer.MAX_VALUE)).getContent());
         return excelExporter.toHttpResponse();
     }

    @GetMapping("/code/excel/download")
    public ResponseEntity<byte[]> exportCodeExcel(@Validated CodeQuery request) throws Exception {
        String docName = URLEncoder.encode("codeList", "UTF-8");
        ExcelExporter excelExporter = new ExcelExporter(docName);
        excelExporter.addDataList(commonCodeService.getCodeList(request
                , PageRequest.of(0, Integer.MAX_VALUE)).getContent());
        return excelExporter.toHttpResponse();
    }

    @GetMapping("/code-item/excel/download")
    public ResponseEntity<byte[]> exportCodeItemExcel(@Validated CodeItemQuery request) throws Exception {
        String docName = URLEncoder.encode("codeItemList", "UTF-8");
        ExcelExporter excelExporter = new ExcelExporter(docName);
        excelExporter.addDataList(commonCodeService.getCodeItemList(request
                , PageRequest.of(0, Integer.MAX_VALUE)).getContent());
        return excelExporter.toHttpResponse();
    }

    @GetMapping("/common-code/excel/download")
    public ResponseEntity<byte[]> exportCategoryExcel(@Validated CommonCodeQuery request) throws Exception {
        String docName = URLEncoder.encode("commonCodeList", "UTF-8");
        ExcelExporter excelExporter = new ExcelExporter(docName);

        String categoryId = request.getSearchCategoryId();
        String codeId = request.getSearchCodeId();
        String codeItemId = request.getSearchCodeItemId();
        String type = request.getSearchType();
        String searchDesc = request.getSearchDesc();
        String useYn = request.getSearchUseYn();

        CategoryQuery categoryQuery = new CategoryQuery(request.getSearchCategoryId());
        CodeQuery codeQuery = new CodeQuery(request.getSearchCodeCategoryId(), request.getSearchCodeId());
        CodeItemQuery codeItemQuery = new CodeItemQuery(request.getSearchCodeItemCategoryId(), request.getSearchCodeItemCodeId(), request.getSearchCodeItemId());

        if ("0".equals(type)) {
            /* 전체조회 */
            categoryQuery = new CategoryQuery("");
            codeQuery = new CodeQuery("","");
            codeItemQuery = new CodeItemQuery("", "", "");
        } else if ("1".equals(type)) {
            categoryQuery.setSearchCategoryDesc(searchDesc);
            categoryQuery.setSearchUseYn(useYn);
        } else if ("2".equals(type)) {
            codeQuery.setSearchCodeDesc(searchDesc);
            codeQuery.setSearchUseYn(useYn);
        } else if ("3".equals(type)) {
            codeItemQuery.setSearchCodeItemDesc(searchDesc);
            codeItemQuery.setSearchUseYn(useYn);
        }

        excelExporter.addDataList(commonCodeService.getCategoryList(categoryQuery
                , PageRequest.of(0, Integer.MAX_VALUE)).getContent()
                , "CHMM_CATEGORY_INFO");

        excelExporter.addDataList(commonCodeService.getCodeList(codeQuery
                , PageRequest.of(0, Integer.MAX_VALUE)).getContent()
                , "CHMM_CODE_INFO");

        excelExporter.addDataList(commonCodeService.getCodeItemList(codeItemQuery
                , PageRequest.of(0, Integer.MAX_VALUE)).getContent()
                , "CHMM_CODE_ITEM_INFO");

        return excelExporter.toHttpResponse();
    }

    @GetMapping("/excel/sample")
    public ResponseEntity<byte[]> exportCodeExcel() throws Exception {
        List<CategoryExcelUploadVO> list = new ArrayList<>();
        list.add(new CategoryExcelUploadVO("test", "test", 100, "test"));
        List<CodeExcelUploadVO> codeList = new ArrayList<>();
        codeList.add(new CodeExcelUploadVO("test", "test1", "test", 100, "test"));
        List<CodeItemExcelUploadVO> codeItemList = new ArrayList<>();
        codeItemList.add(new CodeItemExcelUploadVO("test", "test1", "test2", "test", 100, "test"));
        String docName = URLEncoder.encode("sample_code", "UTF-8");
        ExcelExporter excelExporter = new ExcelExporter(docName);
        excelExporter.addDataList(list, "CHMM_CATEGORY_INFO");
        excelExporter.addDataList(codeList, "CHMM_CODE_INFO");
        excelExporter.addDataList(codeItemList, "CHMM_CODE_ITEM_INFO");
        return excelExporter.toHttpResponse();
    }

    @PostMapping(value = "/excel/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ChamomileResponse<Void> uploadCategory(@RequestParam("file") MultipartFile file) throws Exception {
        List<CategoryExcelUploadVO> categoryList = new ExcelImporter().toCustomClass(file, CategoryExcelUploadVO.class);
        List<CodeExcelUploadVO> codeList = new ExcelImporter().toCustomClass(file, 1, CodeExcelUploadVO.class);
        List<CodeItemExcelUploadVO> codeItemList = new ExcelImporter().toCustomClass(file, 2, CodeItemExcelUploadVO.class);
        commonCodeService.commonCodeBulkInsert(categoryList, codeList, codeItemList);
        return new ChamomileResponse<>();
    }

    @GetMapping("/category/checkDuplicate")
    public ChamomileResponse<Boolean> checkCategory(@Validated CategoryQuery request) {
        return new ChamomileResponse<>(commonCodeService.checkDuplicateCategory(request));
    }
    @GetMapping("/code/checkDuplicate")
    public ChamomileResponse<Boolean> checkCode(@Validated CodeQuery request) {
        return new ChamomileResponse<>(commonCodeService.checkDuplicateCode(request));
    }
    @GetMapping("/code-item/checkDuplicate")
    public ChamomileResponse<Boolean> checkCodeItem(@Validated CodeItemQuery request) {
        return new ChamomileResponse<>(commonCodeService.checkDuplicateCodeItem(request));
    }
}
