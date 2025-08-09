package net.lotte.chamomile.admin.commoncode.domain;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.lotte.chamomile.admin.commoncode.api.dto.CategoryQuery;
import net.lotte.chamomile.admin.commoncode.api.dto.CodeItemQuery;
import net.lotte.chamomile.admin.commoncode.api.dto.CodeQuery;
import net.lotte.chamomile.module.database.mybatis.batch.BatchRequest;

/**
 * <pre>
 * Admin 공통코드 REST API 관련 Mapper.
 * </pre>
 *
 * @ClassName   : CommonCodeMapper.java
 * @Description : Admin 공통코드 REST API 관련 Mapper.
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
@Mapper
public interface CommonCodeMapper {
    Page<CategoryVO> findCategoryList(CategoryQuery query, Pageable pageable);

    Page<CodeVO> findCodeList(CodeQuery query, Pageable pageable);

    Page<CodeItemVO> findCodeItemList(CodeItemQuery query, Pageable pageable);

    int insertCategory(CategoryVO command);

    int insertCode(CodeVO command);

    int insertCodeItem(CodeItemVO command);

    void updateCategory(CategoryVO command);

    void updateCategoryOrder(List<CategoryVO> categoryList, BatchRequest batchRequest);

    void updateCode(CodeVO entity);

    void updateCodeOrder(List<CodeVO> codes, BatchRequest batchRequest);

    void updateCodeItem(CodeItemVO entity);

    void updateCodeItemOrder(List<CodeItemVO> codeItems, BatchRequest batchRequest);

    void deleteCodeItem(List<CodeItemVO> codeItems, BatchRequest batchRequest);

    void deleteCategoryWithChild(List<CategoryVO> categories, BatchRequest batchRequest);

    void deleteCodeWithChild(List<CodeVO> codes, BatchRequest batchRequest);

    List<String> findCategoryListByIds(List<CategoryVO> categories);

    void insertCategory(List<CategoryVO> categories, BatchRequest batchRequest);

    List<CodeVO> findCodeListByIds(List<CodeVO> codes);

    void insertCode(List<CodeVO> codes, BatchRequest batchRequest);

    List<CodeItemVO> findCodeItemListByIds(List<CodeItemVO> codeItems);

    void insertCodeItem(List<CodeItemVO> insertCodeItemList, BatchRequest batchRequest);

    int checkDuplicateCategory(CategoryQuery query);
    int checkDuplicateCode(CodeQuery query);
    int checkDuplicateCodeItem(CodeItemQuery query);
}
