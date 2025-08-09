package net.lotte.chamomile.admin.commoncode.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import net.lotte.chamomile.admin.commoncode.domain.CommonCodeMapper;
import net.lotte.chamomile.module.database.audit.TimeAuthorLog;
import net.lotte.chamomile.module.database.mybatis.batch.BatchRequest;


/**
 * <pre>
 * Admin 공통코드 REST API 관련 Service Interface 구현체.
 * </pre>
 *
 * @ClassName   : CommonCodeServiceImpl.java
 * @Description : Admin 공통코드 REST API 관련(crud Service Interface 구현체.
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
@Service
@Transactional
@RequiredArgsConstructor
public class CommonCodeServiceImpl implements CommonCodeService {

    private final CommonCodeMapper commonCodeMapper;

    @Override
    @Cacheable(value = "commonCode")
    public Map<String, Object> getCommonCodeAllList() {
        List<CategoryVO> categoryList = commonCodeMapper.findCategoryList(null, PageRequest.of(0, Integer.MAX_VALUE)).getContent();
        List<CodeVO> codeList = commonCodeMapper.findCodeList(null, PageRequest.of(0, Integer.MAX_VALUE)).getContent();
        List<CodeItemVO> codeItemList = commonCodeMapper.findCodeItemList(null, PageRequest.of(0, Integer.MAX_VALUE)).getContent();
        Map<String, Object> map = new HashMap<>();
        map.put("categoryList", categoryList);
        map.put("codeList", codeList);
        map.put("codeItemList", codeItemList);
        return map;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryVO> getCategoryList(CategoryQuery query, Pageable pageable) {
        return commonCodeMapper.findCategoryList(query, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CodeVO> getCodeList(CodeQuery query, Pageable pageable) {
        return commonCodeMapper.findCodeList(query, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CodeItemVO> getCodeItemList(CodeItemQuery query, Pageable pageable) {
        return commonCodeMapper.findCodeItemList(query, pageable);
    }

    @Override
    @CacheEvict(value = "commonCode", allEntries = true)
    public int categorySave(CategoryCommand command) {
        CategoryVO entity = new CategoryVO();
        BeanUtils.copyProperties(command, entity);
        entity.onCreate();
        return commonCodeMapper.insertCategory(entity);
    }

    @Override
    @CacheEvict(value = "commonCode", allEntries = true)
    public int codeSave(CodeCommand command) {
        CodeVO entity = new CodeVO();
        BeanUtils.copyProperties(command, entity);
        entity.onCreate();
        return commonCodeMapper.insertCode(entity);
    }

    @Override
    @CacheEvict(value = "commonCode", allEntries = true)
    public int codeItemSave(CodeItemCommand command) {
        CodeItemVO entity = new CodeItemVO();
        BeanUtils.copyProperties(command, entity);
        entity.onCreate();
        return commonCodeMapper.insertCodeItem(entity);
    }

    @Override
    @CacheEvict(value = "commonCode", allEntries = true)
    public void updateCategory(CategoryCommand command) {
        CategoryVO entity = new CategoryVO();
        BeanUtils.copyProperties(command, entity);
        entity.onUpdate();
        commonCodeMapper.updateCategory(entity);
    }

    @Override
    @CacheEvict(value = "commonCode", allEntries = true)
    public void updateCategoryOrder(List<CategoryCommand> commandList) {
        BatchRequest batchRequest = new BatchRequest(1000);
//        List<CategoryVO> categories = command.stream()
//                .map(CategoryCommand::toEntity).collect(Collectors.toList());
        List<CategoryVO> categories = new ArrayList<>();
        for(CategoryCommand command : commandList) {
            CategoryVO categoryVO = new CategoryVO();
            BeanUtils.copyProperties(command, categoryVO);
            categories.add(categoryVO);
        }
        categories.forEach(TimeAuthorLog::onUpdate);
        commonCodeMapper.updateCategoryOrder(categories, batchRequest);
    }

    @Override
    @CacheEvict(value = "commonCode", allEntries = true)
    public void updateCode(CodeCommand command) {
        CodeVO entity = new CodeVO();
        BeanUtils.copyProperties(command, entity);
        entity.onUpdate();
        commonCodeMapper.updateCode(entity);
    }

    @Override
    @CacheEvict(value = "commonCode", allEntries = true)
    public void updateCodeOrder(List<CodeCommand> commands) {
        BatchRequest batchRequest = new BatchRequest(1000);
//        List<CodeVO> codes = commands.stream().map(CodeCommand::toEntity)
//                .collect(Collectors.toList());
        List<CodeVO> codes = new ArrayList<>();
        for(CodeCommand command : commands) {
            CodeVO codeVO = new CodeVO();
            BeanUtils.copyProperties(command, codeVO);
            codes.add(codeVO);
        }
        codes.forEach(TimeAuthorLog::onUpdate);
        commonCodeMapper.updateCodeOrder(codes, batchRequest);
    }

    @Override
    @CacheEvict(value = "commonCode", allEntries = true)
    public void updateCodeItem(CodeItemCommand command) {
        CodeItemVO entity = new CodeItemVO();
        BeanUtils.copyProperties(command, entity);
        entity.onUpdate();
        commonCodeMapper.updateCodeItem(entity);
    }

    @Override
    @CacheEvict(value = "commonCode", allEntries = true)
    public void updateCodeItemOrder(List<CodeItemCommand> commands) {
//        List<CodeItemVO> codeItems = commands.stream().map(CodeItemCommand::toEntity)
//                .collect(Collectors.toList());
        List<CodeItemVO> codeItems = new ArrayList<>();
        for(CodeItemCommand command : commands) {
            CodeItemVO codeItemVO = new CodeItemVO();
            BeanUtils.copyProperties(command, codeItemVO);
            codeItems.add(codeItemVO);
        }
        codeItems.forEach(TimeAuthorLog::onUpdate);
        commonCodeMapper.updateCodeItemOrder(codeItems, new BatchRequest(1000));
    }

    @Override
    @CacheEvict(value = "commonCode", allEntries = true)
    public void deleteCategory(List<CategoryCommand> commands) {
//        List<CategoryVO> categories = commands.stream()
//                .map(CategoryCommand::toEntity).collect(Collectors.toList());
        List<CategoryVO> categories = new ArrayList<>();
        for(CategoryCommand command : commands) {
            CategoryVO categoryVO = new CategoryVO();
            BeanUtils.copyProperties(command, categoryVO);
            categories.add(categoryVO);
        }
        // 관련 중분류, 소분류도 모두 삭제
        commonCodeMapper.deleteCategoryWithChild(categories, new BatchRequest(1000));
    }

    @Override
    @CacheEvict(value = "commonCode", allEntries = true)
    public void deleteCode(List<CodeCommand> commands) {
//        List<CodeVO> codes = commands.stream().map(CodeCommand::toEntity)
//                .collect(Collectors.toList());
        List<CodeVO> codes = new ArrayList<>();
        for(CodeCommand command : commands) {
            CodeVO codeVO = new CodeVO();
            BeanUtils.copyProperties(command, codeVO);
            codes.add(codeVO);
        }
        // 관련 소분류도 모두 삭제
        commonCodeMapper.deleteCodeWithChild(codes, new BatchRequest(1000));
    }

    @Override
    @CacheEvict(value = "commonCode", allEntries = true)
    public void deleteCodeItem(List<CodeItemCommand> commands) {
//        List<CodeItemVO> codeItems = commands.stream().map(CodeItemCommand::toEntity)
//                .collect(Collectors.toList());
        List<CodeItemVO> codeItems = new ArrayList<>();
        for(CodeItemCommand command : commands) {
            CodeItemVO codeItemVO = new CodeItemVO();
            BeanUtils.copyProperties(command, codeItemVO);
            codeItems.add(codeItemVO);
        }
       commonCodeMapper.deleteCodeItem(codeItems, new BatchRequest(1000));
    }

    @Override
    @CacheEvict(value = "commonCode", allEntries = true)
    public void commonCodeBulkInsert(List<CategoryExcelUploadVO> categoryList, List<CodeExcelUploadVO> codeList, List<CodeItemExcelUploadVO> codeItemList) {
        // 대분류 insert
        insertCategories(categoryList);
        // 중분류 insert
        insertCodes(codeList);
        // 소분류 insert
        insertCodeItems(codeItemList);
    }

    private void insertCategories(List<CategoryExcelUploadVO> categoryList) {
        List<CategoryVO> checkCategoryList = new ArrayList<>();
        categoryList.forEach(e -> checkCategoryList.add(e.toEntity()));
        List<CategoryVO> insertCategoryList = new ArrayList<>();
        List<String> existCategoryList = commonCodeMapper.findCategoryListByIds(checkCategoryList);

        for (CategoryVO categoryVO : checkCategoryList) {
            if(!existCategoryList.stream().anyMatch(e -> e.equals(categoryVO.getCategoryId()))) {
                categoryVO.onCreate();
                insertCategoryList.add(categoryVO);
            }
        }
        commonCodeMapper.insertCategory(insertCategoryList, new BatchRequest(1000));
    }

    private void insertCodes(List<CodeExcelUploadVO> codeList) {
        List<CodeVO> checkCodeList = new ArrayList<>();
        codeList.forEach(e -> checkCodeList.add(e.toEntity()));
        List<CodeVO> insertCodeList = new ArrayList<>();
        List<CodeVO> existCodeList = commonCodeMapper.findCodeListByIds(checkCodeList);
        for(CodeVO codeVO : checkCodeList) {
            if(!existCodeList.stream()
                    .anyMatch(e -> e.getCategoryId().equals(codeVO.getCategoryId())
                            && e.getCodeId().equals(codeVO.getCodeId()))) {
                codeVO.onCreate();
                insertCodeList.add(codeVO);
            }
        }
        commonCodeMapper.insertCode(insertCodeList, new BatchRequest(1000));
    }

    private void insertCodeItems(List<CodeItemExcelUploadVO> codeItemList) {
        List<CodeItemVO> checkCodeItmeList = new ArrayList<>();
        codeItemList.forEach(e -> checkCodeItmeList.add(e.toEntity()));
        List<CodeItemVO> insertCodeItemList = new ArrayList<>();
        List<CodeItemVO> existCodeItemList = commonCodeMapper.findCodeItemListByIds(checkCodeItmeList);
        for(CodeItemVO codeItemVO : checkCodeItmeList) {
            if(!existCodeItemList.stream()
                    .anyMatch(e -> e.getCategoryId().equals(codeItemVO.getCategoryId())
                            && e.getCodeId().equals(codeItemVO.getCodeId()))) {
                codeItemVO.onCreate();
                insertCodeItemList.add(codeItemVO);
            }
        }
        commonCodeMapper.insertCodeItem(insertCodeItemList, new BatchRequest(1000));
    }

    @Override
    public boolean checkDuplicateCategory(CategoryQuery query) {
        return commonCodeMapper.checkDuplicateCategory(query) > 0;
    }
    @Override
    public boolean checkDuplicateCode(CodeQuery query) {
        return commonCodeMapper.checkDuplicateCode(query) > 0;
    }
    @Override
    public boolean checkDuplicateCodeItem(CodeItemQuery query) {
        return commonCodeMapper.checkDuplicateCodeItem(query) > 0;
    }
}
