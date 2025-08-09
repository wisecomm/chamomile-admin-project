package net.lotte.chamomile.admin.auth.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.lotte.chamomile.admin.auth.api.dto.AuthQuery;
import net.lotte.chamomile.admin.auth.domain.AuthExcelUploadVO;
import net.lotte.chamomile.admin.auth.domain.AuthMapper;
import net.lotte.chamomile.admin.auth.domain.AuthVO;
import net.lotte.chamomile.admin.common.exception.code.AdminExceptionCode;
import net.lotte.chamomile.core.exception.BusinessException;
import net.lotte.chamomile.module.database.mybatis.batch.BatchRequest;
import net.lotte.chamomile.module.util.DateUtil;

/**
 * <pre>
 * 권한 관련 서비스 인터페이스 구현체.
 * </pre>
 *
 * @author TaehoPark
 * @since 2023-09-20
 * @version 3.0
 * @Modification
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-09-20     TaehoPark            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthMapper authMapper;

    @Override
    public Page<AuthVO> getAuthList(AuthQuery authQuery, Pageable pageable) {
        return authMapper.findAuthListDataPage(authQuery, pageable);
    }
    @Override
    public List<AuthVO> getAuthList(AuthQuery authQuery) {
        return authMapper.findAuthListData(authQuery);
    }

    @Override
    public void createAuth(AuthVO authVO) throws Exception {
        if (ObjectUtils.isEmpty(authVO)) {
            throw new BusinessException(AdminExceptionCode.ServerError);
        }

        if(validationCheck(authVO)) {
            authVO.onCreate();
            authVO.onUpdate();
            authMapper.insertAuth(authVO);
        } else {
            throw new BusinessException(AdminExceptionCode.MissingParameter);
        }
    }

    @Override
    public void updateAuth(AuthVO authVO) throws Exception {
        if (ObjectUtils.isEmpty(authVO)) {
            throw new BusinessException(AdminExceptionCode.ServerError);
        }

        if(validationCheck(authVO)) {
            authVO.onUpdate();
            authMapper.updateAuth(authVO);
        } else {
            throw new BusinessException(AdminExceptionCode.MissingParameter);
        }
    }

    @Override
    public void createAuth(List<AuthExcelUploadVO> list) {
        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException(AdminExceptionCode.ServerError);
        }

        List<AuthVO> authList = list.stream()
                .map(vo -> {
                    AuthVO authVO = new AuthVO();
                    BeanUtils.copyProperties(vo, authVO);
                    authVO.onCreate();
                    return authVO;
                })
                .collect(Collectors.toList());

        BatchRequest batchRequest = new BatchRequest(1000);
        authMapper.insertAuth(authList, batchRequest);
    }

    @Override
    public void deleteAuth(List<AuthVO> authVOList) {

        if (CollectionUtils.isEmpty(authVOList)) {
           throw new BusinessException(AdminExceptionCode.ServerError);
        }

        authVOList.stream()
                .filter(vo -> authIdCheck(vo) != 1)
                .findFirst()
                .ifPresent(vo -> {
                    throw new BusinessException(AdminExceptionCode.DataNotFoundError);
                });

        BatchRequest batchRequest = new BatchRequest(1000);
        authMapper.deleteUserAuthAll(authVOList, batchRequest);              //사용자 권한 삭제
        authMapper.deleteGroupAuthAll(authVOList, batchRequest);             //그룹 권한 삭제
        authMapper.deleteResourceAuthAll(authVOList, batchRequest);          //리소스 권한 삭제
        authMapper.deleteMenuAuthAll(authVOList, batchRequest);              //메뉴 권한 삭제
        authMapper.deleteMultiAuthAll(authVOList, batchRequest);             //다중 롤 그룹정보 삭제
        authMapper.deleteAuth(authVOList, batchRequest);               		 //권한 삭제
    }

    @Override
    public int authIdCheck(AuthVO authVO) {
        return authMapper.authIdCheck(authVO);
    }


    private boolean validationCheck(AuthVO authVo) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateUtil.PATTERN_yyyyMMdd);
        LocalDate startDate = LocalDate.parse(authVo.getRoleStartDt(), formatter);
        LocalDate endDate = LocalDate.parse(authVo.getRoleEndDt(), formatter);
        LocalDate now = LocalDate.now();

        boolean isDateRangeValid = !startDate.isAfter(endDate);

        if (!isDateRangeValid) {
            throw new BusinessException(AdminExceptionCode.ServerError, "endDate must be equal or greater than startDate");
        }
        return true;
    }
}
