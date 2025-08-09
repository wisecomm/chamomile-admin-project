package net.lotte.chamomile.admin.user.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import net.lotte.chamomile.admin.common.exception.code.AdminExceptionCode;
import net.lotte.chamomile.admin.logging.domain.UserAccessLoggingVO;
import net.lotte.chamomile.admin.user.api.dto.ConfirmUserRequest;
import net.lotte.chamomile.admin.user.api.dto.RegisterUserDTO;
import net.lotte.chamomile.admin.user.api.dto.UserQuery;
import net.lotte.chamomile.admin.user.domain.UserExcelUploadVO;
import net.lotte.chamomile.admin.user.domain.UserMailVO;
import net.lotte.chamomile.admin.user.domain.UserMapper;
import net.lotte.chamomile.admin.user.domain.UserPasswordVO;
import net.lotte.chamomile.admin.user.domain.UserPreviousPasswordMapper;
import net.lotte.chamomile.admin.user.domain.UserVO;
import net.lotte.chamomile.admin.user.domain.WithdrawnUserMapper;
import net.lotte.chamomile.core.exception.BusinessException;
import net.lotte.chamomile.core.exception.ChamomileException;
import net.lotte.chamomile.core.exception.ChamomileExceptionCode;
import net.lotte.chamomile.module.database.mybatis.batch.BatchRequest;
import net.lotte.chamomile.module.logging.LoggingUtils;
import net.lotte.chamomile.module.logging.type.PrivacyActionType;
import net.lotte.chamomile.module.logging.type.UserAccessActionType;
import net.lotte.chamomile.module.notification.mail.MailUtil;
import net.lotte.chamomile.module.notification.mail.MailVo;
import net.lotte.chamomile.module.security.login.JdbcLoginService;
import net.lotte.chamomile.module.security.password.decoder.PasswordDecoder;
import net.lotte.chamomile.module.util.DateUtil;

/**
 * <pre>
 * 사용자 관련 서비스 인터페이스 구현체.
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
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final MailUtil mailUtil;
    private final JdbcLoginService jdbcLoginService;
    private final PasswordDecoder passwordDecoder;
    private final WithdrawnUserMapper withdrawnUserMapper;

    private final UserPreviousPasswordMapper userPreviousPasswordsMapper;

    @Value("${chmm.user.previous-password-save-count:3}")
    private int maxPasswordHistory;

    @Value("${chmm.user.withdrawn-id-reuse-delay-days:90}")
    private int withdrawnUserIdReuseDelayDays;

    @Value("${chmm.mobile.admin.controller:false}")
    private String mobileAdminFlag;

    @Override
    public Page<UserVO> getUserList(UserQuery userQuery, Pageable pageable) {
        userQuery.setMobileFlag(mobileAdminFlag);
        Page<UserVO> userListDataPage = userMapper.findUserListWithGroup(userQuery, pageable);
        LoggingUtils.privacyLogging(userListDataPage, "유저 목록을 봤음", PrivacyActionType.READ);
        return userListDataPage;
    }

    @Override
    public List<UserVO> getUserList(UserQuery userQuery) {
        List<UserVO> userListData = userMapper.findUserListData(userQuery);
        LoggingUtils.privacyLogging(userListData,"유저 목록을 봤음", PrivacyActionType.READ);
        return userListData;
    }

    @Override
    public UserVO getUserDetail(UserQuery userQuery) {
        UserVO userVO = userMapper.findUserDetail(userQuery)
                .orElseThrow(() -> new NoSuchElementException("해당 리소스가 존재하지 않습니다."));

        // 모바일 기능 - 기기관리여부, 보유기기수 조회
        if(mobileAdminFlag.equals("true")) {
            userMapper.findUserMobileDetail(userQuery).ifPresent(user -> {
                userVO.setUserDeviceCount(user.getUserDeviceCount());
                userVO.setMdmYn(user.getMdmYn());
             }
            );
        }

        LoggingUtils.privacyLogging(userVO,"유저 상세를 봤음", PrivacyActionType.READ);
        return userVO;
    }

    @Override
    public void createUser(UserVO userVO) throws Exception {

        if (ObjectUtils.isEmpty(userVO)) {
            throw new BusinessException(AdminExceptionCode.ServerError);
        }

        // 회원 아이디 중복 체크
        if(userIdCheck(userVO)>0){
            throw new ChamomileException(ChamomileExceptionCode.DUPLICATED_ID);
        }

        // 삭제한 회원 아이디 체크
        if(!checkAndDeleteWithdrawnUserId(userVO.getUserId())){
            throw new ChamomileException(ChamomileExceptionCode.DUPLICATED_ID);
        }

        //등록한 유저의 처음 비밀번호를 저장.
        checkAndSaveNewPassword(userVO.getUserId(),userVO.getUserPwd());

        String encodedPw = passwordEncoder.encode(passwordDecoder.decrypt(userVO.getUserPwd()));
        userVO.setUserPwd(encodedPw);

        if(validationCheck(userVO)) {
            userVO.onCreate();
            userVO.onUpdate();
            userMapper.insertUser(userVO);
            LoggingUtils.privacyLogging(userVO,"유저 데이터를 생성함", PrivacyActionType.WRITE);
        } else {
            throw new BusinessException(AdminExceptionCode.ServerError, "Cannot update");
        }

        // 모바일 기능 - 기기관리여부 업데이트
        if(mobileAdminFlag.equals("true")) {
            userMapper.updateMdmYnOfUser(userVO);
        }
    }

    @Override
    public void updateUser(UserVO userVO) throws Exception {

        // 비밀번호 변경 로직
        if(userVO.getPwChange().equals("true")) {
            validatePreviousPassword(userVO);
            checkAndSaveNewPassword(userVO.getUserId(),passwordDecoder.decrypt(userVO.getUserPwd()));
            String encodedPw = passwordEncoder.encode(passwordDecoder.decrypt(userVO.getUserPwd()));
            userVO.setUserPwd(encodedPw);
        }

        if(validationCheck(userVO)) {
            userVO.onUpdate();
            userMapper.updateUser(userVO);
            LoggingUtils.privacyLogging(userVO,"유저 데이터를 변경함", PrivacyActionType.WRITE);
        } else {
            throw new BusinessException(AdminExceptionCode.ServerError, "Cannot save");
        }

        // 모바일 기능 - 기기관리여부 업데이트
        if(mobileAdminFlag.equals("true")) {
            userMapper.updateMdmYnOfUser(userVO);
        }
    }

    @Override
    public void createUser(List<UserExcelUploadVO> list) throws Exception {
        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException(AdminExceptionCode.ServerError);
        }

        List<UserVO> userList = list.stream()
                .map(vo -> {
                    UserVO userVO = new UserVO();
                    // 엑샐로 등록한 유저의 처음 비밀번호를 저장.
                    checkAndSaveNewPassword(userVO.getUserId(),userVO.getUserPwd());
                    String encodedPw = passwordEncoder.encode(vo.getUserPwd());
                    BeanUtils.copyProperties(vo, userVO);
                    userVO.setUserPwd(encodedPw);
                    userVO.onCreate();
                    userVO.onUpdate();
                    return userVO;
                })
                .collect(Collectors.toList());

        BatchRequest batchRequest = new BatchRequest(1000);
        userMapper.insertUser(userList, batchRequest);
        LoggingUtils.privacyLogging(userList,"대량 유저를 엑셀로 생성함", PrivacyActionType.READ);
    }

    @Override
    public void deleteUser(List<UserVO> userVOList) {
        if (CollectionUtils.isEmpty(userVOList)) {
            throw new BusinessException(AdminExceptionCode.ServerError);
        }

        userVOList.stream()
                .filter(vo -> userIdCheck(vo) != 1)
                .findFirst()
                .ifPresent(vo -> {
                    throw new BusinessException(AdminExceptionCode.DataNotFoundError);
                });

        BatchRequest batchRequest = new BatchRequest(1000);

        userMapper.deleteUserGroupMap(userVOList, batchRequest);
        userMapper.deleteUserauth(userVOList, batchRequest);
        userMapper.deleteUser(userVOList, batchRequest);

        // 삭제 회원 아이디 보관일 수가 0 이상일때
        if(withdrawnUserIdReuseDelayDays > 0)
            for(UserVO userVO: userVOList) // 삭제 회원 아이디, 탈퇴일을 저장함.
                withdrawnUserMapper.insertWithdrawnUser(userVO.getUserId(),LocalDateTime.now());

        LoggingUtils.privacyLogging(userVOList,"대량 유저를 삭제함", PrivacyActionType.DELETE);
    }

    @Override
    public int userIdCheck(UserVO userVO) {
        // 삭제한 회원 아이디 체크
        if(!checkAndDeleteWithdrawnUserId(userVO.getUserId())){
            return 1;
        }
        // 원래 회원 아이디 체크
        return userMapper.userIdCheck(userVO);
    }

    @Override
    public void confirmPassword(ConfirmUserRequest confirmUserRequest) throws Exception {
        UserVO userVO = new UserVO();
        userVO.setUserId(confirmUserRequest.getUserId());

        String encryptedPassword = userMapper.findUserPwd(userVO);
        String decryptedPassword = passwordDecoder.decrypt(confirmUserRequest.getPassword());

        if(!passwordEncoder.matches(decryptedPassword, encryptedPassword)) {
            throw new ChamomileException(ChamomileExceptionCode.PASSWORD_NOT_MATCH);
        }
    }

    @Override
    public int userMobileDupCheck(UserVO userVO) {
        return userMapper.userMobileDupCheck(userVO);
    }

    @Override
    public int userEmailDupCheck(UserVO userVO) {
        return userMapper.userEmailDupCheck(userVO);
    }

    @Override
    public void sendEmail(UserMailVO userMailVo) {
        if (ObjectUtils.isEmpty(userMailVo)) {
            throw new BusinessException(AdminExceptionCode.ServerError);
        }

        String userId = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        UserQuery userQuery = new UserQuery();
        userQuery.setUserId(userId);
        UserVO userVO = userMapper.findUserDetail(userQuery)
                .orElseThrow(() -> new ChamomileException(ChamomileExceptionCode.EMAIL_SEND_ERROR));

        MailVo mailVo = new MailVo.MailVoBuilder()
                .setFrom(userVO.getUserEmail())
                .setCharset("UTF-8")
                .setSubject(userMailVo.getTitle())
                .setMsg(userMailVo.getBody())
                .addTo(userMailVo.getEmail())
                .build();
        mailUtil.send(new MailVo[]{mailVo});
    }

    @Transactional(noRollbackFor = BadCredentialsException.class)
    @Override
    public void updatePasswordUser(UserPasswordVO userPasswordVO) throws Exception {
        int userLockCnt;
        if (ObjectUtils.isEmpty(userPasswordVO)) {
            throw new BusinessException(AdminExceptionCode.ServerError);
        }

        try {
            userLockCnt = jdbcLoginService.checkLockCnt(userPasswordVO.getUserId());
        } catch (Exception ex) {
            throw new ChamomileException(ChamomileExceptionCode.AuthenticationFailed);
        }
        // 오류횟수가 설정한 임계치를 넘었을 때 계정 락 on
        if (userLockCnt >= jdbcLoginService.getLockCountLimit()) {
            throw new BusinessException(AdminExceptionCode.AuthenticationFailed);
        }

        // 사용자 비밀번호 설정
        UserVO userVO = new UserVO();
        userVO.setUserId(userPasswordVO.getUserId());
        userVO.setUserPrevPwd(passwordDecoder.decrypt(userPasswordVO.getUserPrevPwd()));
        userVO.setUserPwd(passwordDecoder.decrypt(userPasswordVO.getUserPwd()));
        userVO.setUserPwdCheck(passwordDecoder.decrypt(userPasswordVO.getUserPwdCheck()));

        // 이전에 저장한 비밀번호들과 일치 여부 체크
        checkAndSaveNewPassword(userVO.getUserId(),userVO.getUserPwd());
        
        // 이전 비밀번호 유효성 검사
        if (StringUtils.hasText(userVO.getUserPrevPwd())) {
            String oldPwd = userMapper.findUserPwd(userVO);
            String prevPwd = userVO.getUserPrevPwd();

            if (!passwordEncoder.matches(prevPwd, oldPwd)) {
                jdbcLoginService.lockProcessAccount(userVO.getUserId());
                throw new BadCredentialsException("패스워드가 일치하지 않습니다.");
            }

            if (!userVO.getUserPwd().equals(userVO.getUserPwdCheck())) {
                throw new IllegalArgumentException("새로운 패스워드가 일치하지 않습니다.");
            }
        }

        // 비밀번호 만료 또는 계정 종료 날짜 설정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateUtil.PATTERN_yyyyMMdd);
        LocalDate futureDate = LocalDate.now().plusDays(userPasswordVO.getPlusDays());
        String formattedDate = futureDate.format(formatter);

        if ("account".equals(userPasswordVO.getType())) {
            userPasswordVO.setAccountEndDt(formattedDate);
        } else {
            futureDate = LocalDate.now().plusDays(90);
            formattedDate = futureDate.format(DateTimeFormatter.ofPattern(DateUtil.PATTERN_yyyyMMdd));
            userPasswordVO.setPasswordExpireDt(formattedDate);
        }

        // 비밀번호 암호화 및 추가 설정
        userPasswordVO.setUserPwd(passwordEncoder.encode(userVO.getUserPwd()));
        userPasswordVO.setAccountNonLock("1");
        userPasswordVO.setSysUpdateDtm(LocalDateTime.now());
        userPasswordVO.setSysUpdateUserId(userVO.getUserId());


        // 사용자 비밀번호 업데이트
        userMapper.updatePasswordUser(userPasswordVO);
    }


    private boolean validationCheck(UserVO userVo) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateUtil.PATTERN_yyyyMMdd);
        LocalDate startDate = LocalDate.parse(userVo.getAccountStartDt(), formatter);
        LocalDate endDate = LocalDate.parse(userVo.getAccountEndDt(), formatter);
        LocalDate passwordEndDate = LocalDate.parse(userVo.getPasswordExpireDt(), formatter);
        LocalDate now = LocalDate.now();

        boolean isDateRangeValid = !startDate.isAfter(endDate);
        boolean isPasswordStillValid = !passwordEndDate.isBefore(now);

        if (!isDateRangeValid) {
            throw new BusinessException(AdminExceptionCode.ServerError, "endDate must be equal or greater than startDate");
        }

        if (!isPasswordStillValid) {
            throw new BusinessException(AdminExceptionCode.ServerError, "passwordEndDate must be equal or greater than today.");
        }

        return true;
    }


    /**
     * 비밀번호 검증 로직
     */
    private void validatePreviousPassword(UserVO userVO) throws Exception {
        if (StringUtils.hasText(userVO.getUserPrevPwd())) {
            String oldPwd = userMapper.findUserPwd(userVO);
            String prevPwd = passwordDecoder.decrypt(userVO.getUserPrevPwd());

            if (!passwordEncoder.matches(prevPwd, oldPwd)) {
                throw new NoSuchElementException("패스워드가 일치 하지 않습니다.");
            }

            if (!passwordDecoder.decrypt(userVO.getUserPwd()).equals(passwordDecoder.decrypt(userVO.getUserPwdCheck()))) {
                throw new IllegalArgumentException("새로운 패스워드가 일치하지 않습니다.");
            }
        }
    }

    /**
     * 최근 접속 여부 확인
     */
    public void checkRecentlySuccessAccessed(UserQuery query, int checkNum, String checkType) {

        // 1. 로그인 성공 log 정보 가져오기
        UserAccessLoggingVO userAccessLoggingVO = lastSuccessLoginInfo(query);

        // 2. 일정기간 동안 사용하지 않았는지 체크

        // 로그에 찍힌 날짜
        LocalDate targetDate = DateUtil.stringToDate(userAccessLoggingVO.getLogDate(), DateUtil.PATTERN_yyyyMMddHHmmss_DASH);
        // 일정기간(비교할 날짜)
        LocalDate comparisonDate = DateUtil.getComparisonDate(checkNum, checkType);

        if (!targetDate.isAfter(comparisonDate)) {
            // 계정 락
            userMapper.accountOnLock(query.getUserId());
        }

    }


    /**
     * 비밀번호 변경 제한
     * 1. 이전에 사용한 비밀번호 재사용
     * 2. 키보드의 연속적인 배열
     * 3. 계정명(id)와 동일한 비밀번호
     */
    public void checkPasswordValidation(UserVO userVO) {

        // 1. 이전에 사용한 비밀번호 재사용 여부 체크
        checkBeforePwdValidation(userVO);

        // 2. 키보드의 연속적인 배열
        PasswordValidation.repeatPassword(userVO.getUserPwdCheck());

        // 3. 계정명(id)와 동일한 비밀번호
        PasswordValidation.checkSameUserId(userVO.getUserId(), userVO.getUserPwdCheck());

    }

    /**
     * 이전 비밀번호와 동일한지 체크
     */
    public void checkBeforePwdValidation(UserVO userVO) {
        String originPassword = userMapper.findUserPwd(userVO); // 이전 비밀번호
        String changePassword = userVO.getUserPwdCheck(); // 변경할 비밀번호

        if (passwordEncoder.matches(changePassword, originPassword)) {
            throw new ChamomileException(ChamomileExceptionCode.SAME_AS_CURRENT_PASSWORD);
        }
    }

    /**
     * 사용자 인증 메소드
     * - 비인가자에 의한 로그인 여부를 확인하기 위함
     * - 직전에 성공적으로 로그인한 일시, ip 등의 정보를 리턴
     */
    public UserAccessLoggingVO lastSuccessLoginInfo(UserQuery userQuery) {

        userQuery.setSearchLogType(String.valueOf(UserAccessActionType.LOGIN_SUCCESS));

        UserAccessLoggingVO userAccessLoggingVO = userMapper.findUserRecentlyLoggingInfo(userQuery).orElseThrow(
                () -> new ChamomileException(ChamomileExceptionCode.USER_NOT_FOUNT_ACCESS_LOG));

        return userAccessLoggingVO;
    }

    /**
     * 사용자 인증 메소드2(쿼리변경)
     * - 비인가자에 의한 로그인 여부를 확인하기 위함
     * - 직전에 성공적으로 로그인한 일시, ip 등의 정보를 리턴
     */
    public UserAccessLoggingVO getLastSuccessLoginInfo(UserQuery userQuery) {

        userQuery.setSearchLogType(String.valueOf(UserAccessActionType.LOGIN_SUCCESS));

        UserAccessLoggingVO userAccessLoggingVO = userMapper.findUserRecentlyLoggingInfo2(userQuery).orElseThrow(
                () -> new ChamomileException(ChamomileExceptionCode.USER_NOT_FOUNT_ACCESS_LOG));

        // 로그일시 Timezone offset 만큼 보정
        LocalDateTime logDate = (userAccessLoggingVO.getOrgLogDate() != null) ?
                userAccessLoggingVO.getOrgLogDate().plus(TimeZone.getDefault().getRawOffset(), ChronoUnit.MILLIS) :
                LocalDateTime.now();
        userAccessLoggingVO.setLogDate(logDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        return userAccessLoggingVO;
    }

    @Override
    public void registerUser(RegisterUserDTO registerUserDTO) throws Exception {


        UserVO userVO = UserVO.builder()
                .userId(registerUserDTO.getUserId())
                .userName(registerUserDTO.getUserName())
                .userMobile(registerUserDTO.getUserMobile())
                .userEmail(registerUserDTO.getUserEmail())
                .userPwd(registerUserDTO.getUserPwd())
                .build();

        // 회원 아이디 중복 체크
        if(userIdCheck(userVO)>0){
            throw new ChamomileException(ChamomileExceptionCode.DUPLICATED_ID);
        }

        // 삭제한 회원 아이디 체크
        if(!checkAndDeleteWithdrawnUserId(userVO.getUserId())){
            throw new ChamomileException(ChamomileExceptionCode.DUPLICATED_ID);
        }

        // 가입한 유저의 처음 비밀번호를 저장.
        checkAndSaveNewPassword(userVO.getUserId(),passwordDecoder.decrypt(userVO.getUserPwd()));

        String encodedPw = passwordEncoder.encode(passwordDecoder.decrypt(userVO.getUserPwd()));
        userVO.setUserPwd(encodedPw);

        LocalDate currentDate = LocalDate.now();
        // 날짜를 "yyyyMMdd" 형식의 문자열로 포맷합니다.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        userVO.setAccountStartDt(currentDate.format(formatter));
        userVO.setAccountEndDt(currentDate.plusYears(10).format(formatter));
        userVO.setPasswordExpireDt(currentDate.plusYears(1).format(formatter));
        userVO.setUseYn("1");

        if(validationCheck(userVO)) {
            userVO.onCreate();
            userVO.onUpdate();
            userMapper.insertUser(userVO);
            LoggingUtils.privacyLogging(userVO,"회원 가입", PrivacyActionType.WRITE);
        } else {
            throw new BusinessException(AdminExceptionCode.ServerError, "Cannot update");
        }
    }

    public void checkAndSaveNewPassword(String userId, String newPassword) {

        // 현재까지 사용한 비밀번호들 가져오기
        List<String> passwordList = userPreviousPasswordsMapper.getAllPasswordsByUserId(userId);
        for(String prevPassword : passwordList) {
            if (passwordEncoder.matches(newPassword,prevPassword)) {
                throw new ChamomileException(ChamomileExceptionCode.MATCHES_PREVIOUS_PASSWORD);
            }
        }

        // 최대 저장 횟수를 초과하면 가장 오래된 비밀번호 삭제
        if (passwordList.size() >= maxPasswordHistory) {
            userPreviousPasswordsMapper.deleteOldestPassword(userId);
        }

        // 새로운 비밀번호 저장
        userPreviousPasswordsMapper.insertNewPassword(userId, passwordEncoder.encode(newPassword));
    }


    // 삭제된 아이디 체크하는 메서드
    public boolean checkAndDeleteWithdrawnUserId(String userId) {
        if (withdrawnUserIdReuseDelayDays <= 0) {
            return true;
        }

        LocalDateTime withdrawalDate = withdrawnUserMapper.selectWithdrawalDateByUserId(userId);

        if (withdrawalDate == null) {
            return true;
        }

        LocalDateTime reuseDate = withdrawalDate.plusDays(withdrawnUserIdReuseDelayDays);
        LocalDateTime today = LocalDateTime.now();

        if (today.isAfter(reuseDate)) {
            withdrawnUserMapper.deleteWithdrawnUserByUserId(userId);
            return true;
        } else {
            return false;
        }
    }
}
