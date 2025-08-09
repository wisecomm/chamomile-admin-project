package net.lotte.chamomile.admin.commoncode.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.lotte.chamomile.admin.commoncode.api.dto.CategoryCommand;
import net.lotte.chamomile.admin.commoncode.api.dto.CategoryQuery;
import net.lotte.chamomile.admin.commoncode.api.dto.CodeCommand;
import net.lotte.chamomile.admin.commoncode.api.dto.CodeItemCommand;
import net.lotte.chamomile.admin.commoncode.api.dto.CodeItemQuery;
import net.lotte.chamomile.admin.commoncode.api.dto.CodeQuery;
import net.lotte.chamomile.admin.commoncode.domain.CategoryExcelUploadVO;
import net.lotte.chamomile.admin.commoncode.domain.CategoryVO;
import net.lotte.chamomile.admin.commoncode.domain.CodeExcelUploadVO;
import net.lotte.chamomile.admin.commoncode.domain.CodeItemExcelUploadVO;
import net.lotte.chamomile.admin.commoncode.domain.CodeItemVO;
import net.lotte.chamomile.admin.commoncode.domain.CodeVO;

/**
 * <pre>
 * Admin 공통코드 REST API 관련 Service Interface.
 * </pre>
 *
 * @ClassName   : CommonCodeService.java
 * @Description : Admin 공통코드 REST API 관련(CRUD 등) Service Interface.
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
public interface CommonCodeService {
    Map<String, Object> getCommonCodeAllList();

    Page<CategoryVO> getCategoryList(CategoryQuery query, Pageable pageable);

    Page<CodeVO> getCodeList(CodeQuery query, Pageable pageable);

    Page<CodeItemVO> getCodeItemList(CodeItemQuery query, Pageable pageable);

    int categorySave(CategoryCommand command);

    int codeSave(CodeCommand command);

    int codeItemSave(CodeItemCommand command);

    void updateCategory(CategoryCommand categoryCommand);

    void updateCategoryOrder(List<CategoryCommand> command);

    void updateCode(CodeCommand codeCommand);

    void updateCodeOrder(List<CodeCommand> commands);

    void updateCodeItem(CodeItemCommand codeItemCommand);

    void updateCodeItemOrder(List<CodeItemCommand> commands);

    void deleteCategory(List<CategoryCommand> commands);

    void deleteCode(List<CodeCommand> codeCommands);

    void deleteCodeItem(List<CodeItemCommand> commands);

    void commonCodeBulkInsert(List<CategoryExcelUploadVO> categoryList, List<CodeExcelUploadVO> codeList, List<CodeItemExcelUploadVO> codeItemList);

    boolean checkDuplicateCategory(CategoryQuery query);
    boolean checkDuplicateCode(CodeQuery query);
    boolean checkDuplicateCodeItem(CodeItemQuery query);
}
