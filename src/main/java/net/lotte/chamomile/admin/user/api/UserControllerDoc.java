package net.lotte.chamomile.admin.user.api;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import net.lotte.chamomile.admin.logging.domain.UserAccessLoggingVO;
import net.lotte.chamomile.admin.user.api.dto.ConfirmUserRequest;
import net.lotte.chamomile.admin.user.api.dto.PasswordChangeRequest;
import net.lotte.chamomile.admin.user.api.dto.PasswordResetRequest;
import net.lotte.chamomile.admin.user.api.dto.RegisterUserDTO;
import net.lotte.chamomile.admin.user.api.dto.UserQuery;
import net.lotte.chamomile.admin.user.api.dto.VerifyCodeRequest;
import net.lotte.chamomile.admin.user.api.dto.VerifyEmailRequest;
import net.lotte.chamomile.admin.user.domain.UserMailVO;
import net.lotte.chamomile.admin.user.domain.UserPasswordVO;
import net.lotte.chamomile.admin.user.domain.UserVO;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;

/**
 * <pre>
 * 사용자 관련 컨트롤러 스웨거 문서.
 * </pre>
 *
 * @author TaehoPark
 * @since 2023-09-20
 * @version 3.0
 * @see UserController
 * @Modification
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-09-20     TaehoPark            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@Tag(name = "캐모마일 어드민 UserAPI")
public interface UserControllerDoc {
    @Operation(summary = "사용자 목록 요청", description = "사용자의 목록을 조회합니다.")
    ChamomileResponse<Page<UserVO>> getUserList(UserQuery request, Pageable pageable);
    @Operation(summary = "사용자 단건 요청", description = "사용자의 단건을 조회합니다.")
    ChamomileResponse<UserVO> getUserDetail(UserQuery request);
    @Operation(summary = "사용자 생성 요청", description = "사용자를 생성합니다.")
    ChamomileResponse<Void> createUser(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    examples = @ExampleObject(value = "{\n" +
                            "    \"flag\": \"I\",\n" +
                            "    \"accountEndDt\": \"20231130\",\n" +
                            "    \"accountNonLock\": \"1\",\n" +
                            "    \"accountStartDt\": \"20231010\",\n" +
                            "    \"passwordExpireDt\": \"20231031\",\n" +
                            "    \"pwChange\": \"false\",\n" +
                            "    \"useYn\": \"1\",\n" +
                            "    \"userDesc\": \"\",\n" +
                            "    \"userEmail\": \"userId@ldcc.com\",\n" +
                            "    \"userId\": \"userId\",\n" +
                            "    \"userMobile\": \"\",\n" +
                            "    \"userMsg\": \"\",\n" +
                            "    \"userName\": \"테스트2\",\n" +
                            "    \"userNick\": \"userId\",\n" +
                            "    \"userPwd\": \"qwert12345!!\",\n" +
                            "    \"userPwdCheck\": \"qwert12345!!\",\n" +
                            "    \"userSnsId\": \"userId\",\n" +
                            "    \"userStatCd\": \"업무\"\n" +
                            "}")
            )
    ) UserVO request) throws Exception;

    @Operation(summary = "사용자 수정 요청", description = "사용자를 수정합니다.")
    ChamomileResponse<Void> updateUser(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    examples = @ExampleObject(value = "{\n" +
                            "    \"accountEndDt\": \"20231130\",\n" +
                            "    \"accountNonLock\": \"1\",\n" +
                            "    \"accountStartDt\": \"20231010\",\n" +
                            "    \"passwordExpireDt\": \"20231031\",\n" +
                            "    \"pwChange\": \"false\",\n" +
                            "    \"useYn\": \"1\",\n" +
                            "    \"userDesc\": \"\",\n" +
                            "    \"userEmail\": \"userId@ldcc.com\",\n" +
                            "    \"userId\": \"userId\",\n" +
                            "    \"userMobile\": \"\",\n" +
                            "    \"userMsg\": \"\",\n" +
                            "    \"userName\": \"테스트2\",\n" +
                            "    \"userNick\": \"userId\",\n" +
                            "    \"userPwd\": \"qwert12345!!\",\n" +
                            "    \"userPwdCheck\": \"qwert12345!!\",\n" +
                            "    \"userSnsId\": \"userId\",\n" +
                            "    \"userStatCd\": \"업무\"\n" +
                            "}")
            )
    ) UserVO request) throws Exception;

    @Operation(summary = "사용자 비밀번호 수정 요청", description = "사용자 비밀번호를 수정합니다.")
    ChamomileResponse<Void> updatePasswordUser(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    examples = @ExampleObject(value = "{\n" +
                            "    \"pwChange\": \"true\",\n" +
                            "    \"userId\": \"userId\",\n" +
                            "    \"userPrevPwd\": \"qwert12345!!\",\n" +
                            "    \"userPwd\": \"qwert12345!!\",\n" +
                            "    \"userPwdCheck\": \"qwert12345!!\",\n" +
                            "    \"plusDays\": \"90\",\n" +
                            "    \"type\": \"account\"\n" +
                            "}")
            )
    ) UserPasswordVO request) throws Exception;

    @Operation(summary = "사용자 삭제 요청", description = "사용자를 삭제합니다.")
    ChamomileResponse<Void> deleteUser(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    examples = @ExampleObject(value = "[\n" +
                            "    {\n" +
                            "        \"userId\": \"userId\"\n" +
                            "    }\n" +
                            "]")
            )
    ) List<UserVO>  request) throws Exception;
    @Operation(summary = "사용자 중복확인 ", description = "사용자 중복확인을 합니다. ")
    ChamomileResponse<UserVO> userCheckId(UserQuery request) throws Exception;
    @Operation(summary = "이메일 전송  ", description = "이메일 전송을합니다.")
    ChamomileResponse<Void> sendEmail(UserMailVO request) throws Exception;
    @Operation(summary = "엑셀 파일 저장  ", description = "엑셀 파일을 저장합니다.")
    ResponseEntity<InputStreamResource>  excelDownload(UserQuery request, String downloadReason) throws Exception;

    @Operation(summary = "엑세템플릿 저장  ", description = "엑셀 템플릿을 저장합니다.")
    ResponseEntity<byte[]> excelSample() throws Exception;
    @Operation(summary = "엑셀 업로드   ", description = "엑셀을 업로드 합니다.")
    ChamomileResponse<Void> excelUpload(MultipartFile request) throws Exception;

    @Operation(summary = "사용자 마지막 로그인정보 조회", description = "사용자의 마지막 로그인정보를 조회합니다.")
    ChamomileResponse<UserAccessLoggingVO> getLastSuccessLoginInfo(UserQuery userQuery) throws Exception;

    @Operation(summary = "임시 비밀번호 발송", description = "이메일로 임시 비밀번호 발송")
    ChamomileResponse<Void>  sendTemporaryPassword(String id) throws Exception;
    @Operation(summary = "회원 가입시 이메일 인증 코드 발송", description = "이메일을 확인하기 위한 인증 코드 발송합니다.")
    public ChamomileResponse<Void> checkEmail(VerifyEmailRequest request);
    @Operation(summary = "이메일 인증 코드 발송", description = "이메일을 확인하기 위한 인증 코드 발송합니다.")
    ChamomileResponse<Void> sendVerifyCodeEmail(PasswordResetRequest request);
    @Operation(summary = "인증 코드 확인", description = "인증 코드를 확인합니다.")
    ChamomileResponse<Void>  verifyCode(VerifyCodeRequest request);
    @Operation(summary = "인증 코드 기반 비밀번호 변경.", description = "인증 코드 기반으로 비밀번호 변경합니다.")
    ChamomileResponse<Void>  changePassword(PasswordChangeRequest request) throws Exception;
    @Operation(summary = "회원 가입", description = "사용자가 사이트에 등록합니다.")
    ChamomileResponse<Void> registerUser(RegisterUserDTO registerUserDTO)  throws Exception;
    @Operation(summary = "비밀번호 확인", description = "사용자 인증을 위해 비밀번호를 확인합니다.")
    ChamomileResponse<Void> confirmPassword(ConfirmUserRequest confirmUserRequest)  throws Exception;

}
