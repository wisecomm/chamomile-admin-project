package net.lotte.chamomile.admin.resource.domain;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.lotte.chamomile.admin.resource.api.dto.ResourceQuery;
import net.lotte.chamomile.module.database.mybatis.batch.BatchRequest;

/**
 * <pre>
 * 리소스 관련 마이바티스 매핑 인터페이스.
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
@Mapper
public interface ResourceMapper {
    Page<ResourceVO> findResourceListData(ResourceQuery query, Pageable pageable);

    Optional<ResourceVO> findResource(String resourceId);

    int insertResource(ResourceVO command);

    int updateResource(ResourceVO command);

    int deleteResource(List<String> deleteIds);

    void insertResourceExcel(List<ResourceExcelVO> command, BatchRequest batchRequest);

    Page<ResourceExcelVO> findResourceListDataExcel(ResourceQuery request, Pageable pageable);

    List<ResourceExcelVO> findResourceListDataExcel2();
}
