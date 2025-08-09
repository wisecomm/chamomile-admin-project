package net.lotte.chamomile.admin.resource.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.lotte.chamomile.admin.resource.api.dto.ResourceQuery;
import net.lotte.chamomile.admin.resource.domain.ResourceExcelVO;
import net.lotte.chamomile.admin.resource.domain.ResourceVO;

/**
 * <pre>
 * 리소스 관련 서비스 인터페이스.
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
public interface ResourceService {

    Page<ResourceVO> getResourceList(ResourceQuery query, Pageable pageable);

    ResourceVO getResource(String resourceId);
    Boolean getResourceCheck(String resourceId);

    void createResource(ResourceVO command);

    void updateResource(ResourceVO command);

    void deleteResource(List<ResourceVO> command);

    void createResource(List<ResourceExcelVO> command);

    Page<ResourceExcelVO> getResourceListExcel(ResourceQuery request, Pageable pageable);

}
