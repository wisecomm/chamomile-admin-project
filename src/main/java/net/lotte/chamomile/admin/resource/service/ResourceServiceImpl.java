package net.lotte.chamomile.admin.resource.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.lotte.chamomile.admin.authresource.domain.AuthResourceMapper;
import net.lotte.chamomile.admin.resource.api.dto.ResourceQuery;
import net.lotte.chamomile.admin.resource.domain.ResourceExcelVO;
import net.lotte.chamomile.admin.resource.domain.ResourceMapper;
import net.lotte.chamomile.admin.resource.domain.ResourceVO;
import net.lotte.chamomile.module.database.audit.TimeAuthorLog;
import net.lotte.chamomile.module.database.mybatis.batch.BatchRequest;

/**
 * <pre>
 * 리소스 관련 서비스 인터페이스 구현체.
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
@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private final ResourceMapper resourceMapper;
    private final AuthResourceMapper authResourceMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ResourceVO> getResourceList(ResourceQuery query, Pageable pageable) {
        return resourceMapper.findResourceListData(query, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public ResourceVO getResource(String resourceId) {
        return resourceMapper.findResource(resourceId)
                .orElseThrow(() -> new NoSuchElementException("해당 리소스가 존재하지 않습니다."));
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean getResourceCheck(String resourceId) {
        return !resourceMapper.findResource(resourceId).isPresent();
    }

    @Override
    public void createResource(ResourceVO command) {
        command.onCreate();
        resourceMapper.insertResource(command);
    }

    @Override
    public void createResource(List<ResourceExcelVO> command) {
        command.forEach(TimeAuthorLog::onCreate);
        resourceMapper.insertResourceExcel(command, new BatchRequest(1000));
    }

    @Override
    public void updateResource(ResourceVO command) {
        command.onUpdate();
        resourceMapper.updateResource(command);
    }

    @Override
    public void deleteResource(List<ResourceVO> commands) {
        List<String> deleteIds = commands.stream()
                .map(ResourceVO::getResourceId)
                .collect(Collectors.toList());
        resourceMapper.deleteResource(deleteIds);
        authResourceMapper.deleteAuthResource(deleteIds);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResourceExcelVO> getResourceListExcel(ResourceQuery request, Pageable pageable) {
        return resourceMapper.findResourceListDataExcel(request, pageable);
    }
}
