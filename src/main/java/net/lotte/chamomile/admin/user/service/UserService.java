package net.lotte.chamomile.admin.user.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.lotte.chamomile.admin.logging.domain.UserAccessLoggingVO;
import net.lotte.chamomile.admin.user.api.dto.ConfirmUserRequest;
import net.lotte.chamomile.admin.user.api.dto.RegisterUserDTO;
import net.lotte.chamomile.admin.user.api.dto.UserQuery;
import net.lotte.chamomile.admin.user.domain.UserExcelUploadVO;
import net.lotte.chamomile.admin.user.domain.UserMailVO;
import net.lotte.chamomile.admin.user.domain.UserPasswordVO;
import net.lotte.chamomile.admin.user.domain.UserVO;
import net.lotte.chamomile.core.exception.ChamomileException;
import net.lotte.chamomile.core.exception.ChamomileExceptionCode;

/**
 * <pre>
 * 사용자 관련 서비스 인터페이스.
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
public interface UserService {

    /**
     * 사용자 리스트
     *
     * @param userQuery
     * @param pageable
     * @return
     */
    Page<UserVO> getUserList(UserQuery userQuery, Pageable pageable);

    /**
     * 사용자 리스트
     * @param userQuery
     * @return
     */
    List<UserVO> getUserList(UserQuery userQuery);

    /**
     * 사용자 상세
     * @param userQuery
     * @return
     */
    UserVO getUserDetail(UserQuery userQuery);

    /**
     * 사용자 생성, 수정
     * @param userVO
     * @throws Exception
     */
    void createUser(UserVO userVO) throws Exception;

    /**
     * 엑셀 업로드
     * @param list
     * @throws Exception
     */
    void createUser(List<UserExcelUploadVO> list) throws Exception;

    /**
     * 사용자 삭제
     * @param userVOList
     */
    void deleteUser(List<UserVO> userVOList);

    int userIdCheck(UserVO userVO);

    int userMobileDupCheck(UserVO userVO);

    int userEmailDupCheck(UserVO userVO);

    void sendEmail(UserMailVO userVO);

    void updateUser(UserVO request) throws Exception;

    void updatePasswordUser(UserPasswordVO request) throws Exception;

    /**
     * 계정 관리
     * -일정기간 내 미접근시 계정 사용 제한
     */
    void checkRecentlySuccessAccessed(UserQuery userQuery, int checkNum, String checkType) throws ParseException;

    /**
     * 비밀번호 유효성 체크
     * @param userVO
     */
    void checkPasswordValidation(UserVO userVO);

    /**
     * 사용자 인증
     * - 직전 성공한 로그인 로그 정보
     * @param userQuery
     * @return UserAccessLoggingVO
     */
    UserAccessLoggingVO lastSuccessLoginInfo(UserQuery userQuery);
    UserAccessLoggingVO getLastSuccessLoginInfo(UserQuery userQuery); // 쿼리 변경

    /**
     * 사용자 비밀번호 유효성 체크
     * - 현재 비밀번호와 동일한지 체크
     * @param userVO
     */
    void checkBeforePwdValidation(UserVO userVO);

    /**
     * 회원 가입
     * @param registerUserDTO
     * @throws Exception
     */
    void registerUser(RegisterUserDTO registerUserDTO) throws Exception;

    /**
     * 사용자 비밀번호를 이전 비밀번호인지 체크 및 저장하는 메서드
     * @param userId 사용자 아이디
     * @param passwordHash 암호화된 비밀번호
     * @throws Exception
     */
    void checkAndSaveNewPassword(String userId, String passwordHash) throws ChamomileException;

    void confirmPassword(ConfirmUserRequest confirmUserRequest) throws Exception;

    boolean checkAndDeleteWithdrawnUserId(String userId);
}


