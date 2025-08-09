package net.lotte.chamomile.admin.user.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.lotte.chamomile.admin.logging.domain.UserAccessLoggingVO;
import net.lotte.chamomile.admin.user.api.dto.ConfirmUserRequest;
import net.lotte.chamomile.admin.user.api.dto.PasswordChangeRequest;
import net.lotte.chamomile.admin.user.api.dto.PasswordResetRequest;
import net.lotte.chamomile.admin.user.api.dto.RegisterUserDTO;
import net.lotte.chamomile.admin.user.api.dto.UserQuery;
import net.lotte.chamomile.admin.user.api.dto.VerifyCodeRequest;
import net.lotte.chamomile.admin.user.api.dto.VerifyEmailRequest;
import net.lotte.chamomile.admin.user.domain.UserExcelUploadVO;
import net.lotte.chamomile.admin.user.domain.UserMailVO;
import net.lotte.chamomile.admin.user.domain.UserPasswordVO;
import net.lotte.chamomile.admin.user.domain.UserVO;
import net.lotte.chamomile.admin.user.service.UserService;
import net.lotte.chamomile.admin.user.service.VerificationService;
import net.lotte.chamomile.module.excel.ExcelExporter;
import net.lotte.chamomile.module.excel.ExcelImporter;
import net.lotte.chamomile.module.file.util.ArchiveUtil;
import net.lotte.chamomile.module.file.util.FileUploader;
import net.lotte.chamomile.module.logging.LoggingUtils;
import net.lotte.chamomile.module.logging.type.PrivacyActionType;
import net.lotte.chamomile.module.mask.MaskingUtil;
import net.lotte.chamomile.module.security.login.PreLoginService;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;


/**
 * <pre>
 * 사용자 관련 컨트롤러.
 * </pre>
 *
 * @author teahoPark
 * @since 2023-09-19
 * @version 3.0
 * @Modification
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-09-19     TaehoPark            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@Slf4j
@RestController
@RequestMapping(path = "/chmm/user")
@RequiredArgsConstructor
public class UserController implements UserControllerDoc {

    private final UserService userService;
    private final FileUploader fileUpload;
    private final VerificationService verificationService;
    private final PreLoginService preLoginService;
    @Value("${chmm.admin.zip-password}")
    private String excelZipPassword;

    @GetMapping(path = "/list")
    public ChamomileResponse<Page<UserVO>> getUserList(UserQuery userQuery, Pageable pageable) {
        Page<UserVO> results = userService.getUserList(userQuery, pageable);
        // 리스트로 보여줄 떄 개인정보 마스킹 처리하여 반환함.
        return new ChamomileResponse<>(MaskingUtil.annotationMasking(results));
    }

    @GetMapping(path = "/detail")
    public ChamomileResponse<UserVO> getUserDetail(UserQuery userQuery) {
        if(userQuery.getUserId() == null) {
            String userId = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            userQuery.setUserId(userId);
        }

        UserVO result = userService.getUserDetail(userQuery);
        return new ChamomileResponse<>(result);
    }

    @GetMapping(path = "/me")
    public ChamomileResponse<UserVO> getMeDetail() {
        UserQuery userQuery = new UserQuery();

        String userId = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        userQuery.setUserId(userId);

        UserVO result = userService.getUserDetail(userQuery);
        return new ChamomileResponse<>(result);
    }

    @PostMapping(path = "/create")
    public ChamomileResponse<Void> createUser(@Validated @RequestBody UserVO request) throws Exception {
        userService.createUser(request);
        return new ChamomileResponse<>();
    }

    @PostMapping(path = "/update")
    public ChamomileResponse<Void> updateUser(@Validated @RequestBody UserVO request) throws Exception {
        userService.updateUser(request);
        return new ChamomileResponse<>();
    }

    @PostMapping(path = "me")
    public ChamomileResponse<Void> updateMe(@Validated @RequestBody UserVO request) throws Exception {
        String userId = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        request.setUserId(userId);
        userService.updateUser(request);
        return new ChamomileResponse<>();
    }

    @PostMapping(path = "/update-password")
    public ChamomileResponse<Void> updatePasswordUser(@RequestBody UserPasswordVO request) throws Exception {
        userService.updatePasswordUser(request);
        return new ChamomileResponse<>();
    }

    @GetMapping(path = "/confirm-password")
    public ChamomileResponse<Void> confirmPassword(ConfirmUserRequest request) throws Exception {
        userService.confirmPassword(request);
        return new ChamomileResponse<>();
    }

    @PostMapping(path = "/delete")
    public ChamomileResponse<Void> deleteUser(@RequestBody List<UserVO> userVOList) throws Exception {
        userService.deleteUser(userVOList);
        return new ChamomileResponse<>();
    }

    @GetMapping(path = "/user-check-id")
    public ChamomileResponse<UserVO> userCheckId(UserQuery userQuery) throws Exception {
        UserVO userVO = new UserVO();
        userVO.setUserId(userQuery.getSearchUserId());
        userVO.setValid(userService.userIdCheck(userVO) == 0);

        return new ChamomileResponse<>(userVO) ;
    }
    @GetMapping(path = "/user-check-mobile")
    public ChamomileResponse<UserVO> userCheckMobile(UserQuery userQuery) throws Exception {
        UserVO userVO = new UserVO();
        userVO.setUserMobile(userQuery.getSearchUserMobile());
        userVO.setUserId(userQuery.getUserId());
        userVO.setValid(userService.userMobileDupCheck(userVO) == 0);

        return new ChamomileResponse<>(userVO) ;
    }
    @GetMapping(path = "/user-check-email")
    public ChamomileResponse<UserVO> userCheckEmail(UserQuery userQuery) throws Exception {
        UserVO userVO = new UserVO();
        userVO.setUserEmail(userQuery.getSearchUserEmail());
        userVO.setUserId(userQuery.getUserId());
        userVO.setValid(userService.userEmailDupCheck(userVO) == 0);

        return new ChamomileResponse<>(userVO) ;
    }
    @PostMapping(path = "/send-email")
    public ChamomileResponse<Void> sendEmail(@RequestBody UserMailVO userVO) throws Exception {
        userService.sendEmail(userVO);
        return new ChamomileResponse<>();
    }

    @GetMapping("/excel/download")
    public ResponseEntity<InputStreamResource>  excelDownload(UserQuery userQuery, String downloadReason) throws Exception {
        String tmpPath = System.getProperty("java.io.tmpdir");
        ExcelExporter excelExporter = new ExcelExporter("usermgmt.xlsx");
        List<UserVO> results = userService.getUserList(userQuery);

        // userPwd를 제거
        results = results.stream()
                .peek(user -> user.setUserPwd(null))
                .collect(Collectors.toList());


        excelExporter.addDataList(results);
        File excelFile = excelExporter.toFile(tmpPath);
        File zipfile = ArchiveUtil.makeZip(tmpPath+"/usermgmt.zip",excelZipPassword,excelFile);

        excelFile.delete(); // 원본 액셀 파일은 삭제.

        InputStream inputStream = new FileInputStream(zipfile) {
            @Override
            public void close() throws IOException {
                super.close();
                // InputStream이 닫힐 때 파일 삭제
                Files.delete(excelFile.toPath());
            }
        };
        InputStreamResource resource = new InputStreamResource(inputStream);

        // ResponseEntity로 파일 전송
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + zipfile.getName());
        headers.add(HttpHeaders.CONTENT_TYPE, "application/zip");

        // 개인정보 엑셀 파일로 다운로드시 개인 정보 로그 남김.
        LoggingUtils.privacyLogging(results,downloadReason, PrivacyActionType.READ);
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @GetMapping("/excel/sample")
    public ResponseEntity<byte[]> excelSample() throws Exception {
        ExcelExporter excelExporter = new ExcelExporter("sample_usermgmt.xlsx");

        List<UserExcelUploadVO> list = new ArrayList<>();
        UserExcelUploadVO userExcelVO = UserExcelUploadVO.builder()
                .userId("userId")
                .userEmail("test12@naver.com")
                .userMobile("010-1234-5678")
                .userName("userName")
                .userNick("userNick")
                .userPwd("password")
                .userMsg("userMsg")
                .userDesc("userDesc")
                .userStatCd("휴가")
                .useYn("1")
                .accountStartDt("20000101")
                .accountEndDt("21001231")
                .passwordExpireDt("21001231")
                .build();
        list.add(userExcelVO);

        excelExporter.addDataList(list);
        return excelExporter.toHttpResponse();
    }

    @PostMapping(value = "/excel/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ChamomileResponse<Void> excelUpload(@RequestParam("file") MultipartFile file) throws Exception {
        List<UserExcelUploadVO> list = new ExcelImporter().toCustomClass(file, UserExcelUploadVO.class);
        userService.createUser(list);

        return new ChamomileResponse<>();
    }

    @GetMapping("/user-last-success")
    public ChamomileResponse<UserAccessLoggingVO> getLastSuccessLoginInfo(UserQuery userQuery) throws Exception {
        UserAccessLoggingVO result = userService.getLastSuccessLoginInfo(userQuery);
        return new ChamomileResponse<>(result) ;
    }

    @PostMapping("/email-check")
    public ChamomileResponse<Void> checkEmail(@RequestBody VerifyEmailRequest request) {
        verificationService.sendVerificationCodeEmail("",request.getEmail(),request.getRequestTime());
        return new ChamomileResponse<>();
    }

    @PostMapping("/send-verify-code")
    public ChamomileResponse<Void> sendVerifyCodeEmail(@RequestBody PasswordResetRequest request) {
        verificationService.isUserExist(request.getId(),request.getEmail());
        verificationService.sendVerificationCodeEmail(request.getId(),request.getEmail(),request.getRequestTime());
        return new ChamomileResponse<>();
    }


    @PostMapping("/check-verify-code")
    public ChamomileResponse<Void> verifyCode(@RequestBody VerifyCodeRequest request) {
        String userId = verificationService.checkVerifyCode(request.getEmail(), request.getVerifyCode(), LocalDateTime.now());
        preLoginService.preLoginSuccess(userId);
        return new ChamomileResponse<>();
    }

    @PostMapping("/send-temporary-password")
    public ChamomileResponse<Void> sendTemporaryPassword(@RequestBody String id) throws Exception{
        UserQuery userQuery = new UserQuery();
        userQuery.setUserId(id);
        UserVO userVO =  userService.getUserDetail(userQuery);
        String password = verificationService.sendTemporaryPassword(userVO.getUserEmail());
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        String formattedDate = tomorrow.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        userVO.setUserPwd(password);
        userVO.setPasswordExpireDt(formattedDate);
        userVO.setPwChange("true");
        userService.updateUser(userVO);
        return new ChamomileResponse<>();
    }

    @PostMapping("/change-password")
    public ChamomileResponse<Void> changePassword(@RequestBody PasswordChangeRequest request) throws Exception{
        verificationService.checkVerifyCode(request.getVerifyCode());
        UserQuery userQuery = new UserQuery();
        userQuery.setUserId(request.getUserId());
        UserVO userVO =  userService.getUserDetail(userQuery);
        String password = request.getNewPassword();
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        String formattedDate = tomorrow.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        userVO.setUserPwd(password);
        userVO.setPasswordExpireDt(formattedDate);
        userVO.setPwChange("true");
        userService.updateUser(userVO);
        return new ChamomileResponse<>();
    }


    @PostMapping(path = "/register")
    public ChamomileResponse<Void> registerUser(@Validated @RequestBody RegisterUserDTO request) throws Exception {
        // 이메일 인증 우회 시도 대응을 위해 인증번호 재검증
        verificationService.checkVerifyCode(request.getVerifyCode());

        userService.registerUser(request);
        return new ChamomileResponse<>();
    }

    public static Map<String, Object> convertVOToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> resultMap = new HashMap<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            // static 필드 무시하기
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            field.setAccessible(true); // private 필드 접근 허용
            Object value = field.get(obj);
            if (value != null) {
                resultMap.put(field.getName(), value);
            }
        }
        return resultMap;
    }

}
