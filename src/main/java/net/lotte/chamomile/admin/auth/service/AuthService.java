package net.lotte.chamomile.admin.auth.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.lotte.chamomile.admin.auth.api.dto.AuthQuery;
import net.lotte.chamomile.admin.auth.domain.AuthExcelUploadVO;
import net.lotte.chamomile.admin.auth.domain.AuthVO;

/**
 * <pre>
 * 권한 관련 서비스 인터페이스.
 * </pre>
 *
 * @author TaehoPark
 * @since 2023-09-26
 * @version 3.0
 * @Modification
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-09-26     TaehoPark            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
public interface AuthService {

    /**
     * 권한 리스트
     *
     * @param authQuery
     * @param pageable
     * @return
     */
    Page<AuthVO> getAuthList(AuthQuery authQuery, Pageable pageable);

    /**
     * 권한 리스트
     * @param authQuery
     * @return
     */
    List<AuthVO> getAuthList(AuthQuery authQuery);


    /**
     * 권한 생성, 수정
     * @param authVO
     * @throws Exception
     */
    void createAuth(AuthVO authVO) throws Exception;

    /**
     * 엑셀 업로드
     * @param list
     * @throws Exception
     */
    void createAuth(List<AuthExcelUploadVO> list);

    /**
     * 권한 삭제
     * @param authVOList
     */
    void deleteAuth(List<AuthVO> authVOList);

    int authIdCheck(AuthVO authVO);

    void updateAuth(AuthVO request) throws Exception;
}
