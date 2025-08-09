package net.lotte.chamomile.admin.user.service;


import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import net.lotte.chamomile.admin.logging.domain.UserAccessLoggingVO;
import net.lotte.chamomile.admin.user.api.dto.UserQuery;
import net.lotte.chamomile.admin.user.domain.UserMapper;
import net.lotte.chamomile.admin.user.domain.UserVO;
import net.lotte.chamomile.core.exception.ChamomileException;
import net.lotte.chamomile.core.exception.ChamomileExceptionCode;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserMapper mapper;

    @Autowired
    UserService service;

    @Test
    void 접속_이력으로_사용자_계정_락() throws Exception {
        // step1. 사용자 로그 이력 확인 후 계정 잠그기
        // given
        int checkNum = 1;
        String checkType = "month";

        UserQuery setUserQuery = new UserQuery();
        setUserQuery.setSearchUserId("chmm23");

        // when
        service.checkRecentlySuccessAccessed(setUserQuery, checkNum, checkType);

        // then
        // step2. 사용자 계정 잠겼는지 확인
        // given
        UserQuery checkUserQuery = new UserQuery();
        checkUserQuery.setUserId("chmm23");

        // when
        UserVO userVO = mapper.findUserDetail(checkUserQuery).get();

        // then
        assertThat(userVO.getAccountNonLock()).isEqualTo("1");
    }

    @Test
    void 사용자의_최근_로그인성공_로그정보_가져오기() throws Exception {
        UserQuery userQuery = new UserQuery();
        userQuery.setSearchUserId("chmm23");

        UserAccessLoggingVO userAccessLoggingVO = service.lastSuccessLoginInfo(userQuery);
        assertThat(userAccessLoggingVO).isNotNull();
    }

    @Test
    void 키보드_반복적인_배열_체크_유틸() {
        // given
        String notUsablePwd = "asdfg123!"; // 키보드 5개 반복
        String usablePwd = "kimsha!!";

        // then
        // TEST-CASE1. 반복적인 키보드 배열을 사용할 경우 Exception일 발생되어야 한다
        assertThatThrownBy(() -> PasswordValidation.repeatPassword(notUsablePwd)).isInstanceOf(ChamomileException.class)
                .hasMessageContaining("%s", ChamomileExceptionCode.USER_PASSWORD_IS_REPETITIVE);

        // TEST-CASE2-1. 체크할 반복적인 키보드 배열의 개수를 인자값(6개)으로 보낼 경우에는
        // Exception이 발생하지 않는다.
        assertThatCode(() -> PasswordValidation.repeatPassword(notUsablePwd, 6)).doesNotThrowAnyException();

        // TEST-CASE2-2. 체크할 반복적인 키보드 배열의 개수를 인자값(5개)으로 보낼 경우는
        // Exception이 발생한다.
        assertThatThrownBy(() -> PasswordValidation.repeatPassword(notUsablePwd, 5)).isInstanceOf(ChamomileException.class)
                .hasMessageContaining("%s", ChamomileExceptionCode.USER_PASSWORD_IS_REPETITIVE);

        // TEST-CASE3. 정상 문자열로의 비밀번호 등록 - Exception이 발생하지 않는다.
        assertThatCode(() -> PasswordValidation.repeatPassword(usablePwd)).doesNotThrowAnyException();
    }

    @Test
    void 이전에_사용한_비밀번호체크() throws Exception {
        // given
        // step1. 유저 생성
        UserVO userVO = UserVO.builder()
                .accountEndDt("20250515")
                .accountNonLock("0")
                .accountStartDt("20240515")
                .passwordExpireDt("99991231")
                .pwChange("true")
                .useYn("1")
                .userId("admin3")
                .userName("테스트2")
                .userPwd("pwdispwd")
                .userPwdCheck("pwdispwd")
                .build();
        service.createUser(userVO);

        // step1-2. 생성 여부 확인
        UserQuery userQuery = new UserQuery();
        userQuery.setUserId("admin3");
        assertThat(mapper.findUserDetail(userQuery).get()).isNotNull();

        // step2.변경할 비밀번호 set
        String unusablePwd = "pwdispwd";
        String usablePwd = "newPwd";

        // then
        // TEST-CASE1. 현재 비밀번호와 동일한 비밀번호일 경우
        // ChamomileExceptionCode.SAME_AS_CURRENT_PASSWORD 발생
        assertThatThrownBy(() -> service.checkBeforePwdValidation(UserVO.builder()
                .userId("admin3")
                .userPwdCheck(unusablePwd).build())).isInstanceOf(ChamomileException.class)
                .hasMessageContaining("%s", ChamomileExceptionCode.SAME_AS_CURRENT_PASSWORD);

        // TEST-CASE2. 정상 참여
        assertThatCode(() -> service.checkBeforePwdValidation(UserVO.builder()
                .userId("admin3")
                .userPwdCheck(usablePwd).build())).doesNotThrowAnyException();

    }

    @Test
    void 계정명과_동일한_비밀번호_체크() {
        // given
        String notUsablePwd = "kimsha!!"; // 키보드 5개 반복
        String usablePwd = "ldcc22";

        String userId = "kimsha21";

        int checkCnt = 4;

        // TEST-CASE1. 계정명과 checkCnt의 개수만큼 동일한 비밀번호는
        // ChamomileExceptionCode.USER_PASSWORD_AND_ID_ARE_SAME 발생한다.
        assertThatThrownBy(() -> PasswordValidation.checkSameUserId(userId, notUsablePwd, checkCnt)).isInstanceOf(ChamomileException.class)
                .hasMessageContaining("%s", ChamomileExceptionCode.USER_PASSWORD_AND_ID_ARE_SAME);

        // TEST-CASE1-2. 계정명과 동일한 비밀명은 (checkCnt의 개수가 없는 경우는 default 3)
        // ChamomileExceptionCode.USER_PASSWORD_AND_ID_ARE_SAME 발생한다.
        assertThatThrownBy(() -> PasswordValidation.checkSameUserId(userId, notUsablePwd)).isInstanceOf(ChamomileException.class)
                .hasMessageContaining("%s", ChamomileExceptionCode.USER_PASSWORD_AND_ID_ARE_SAME);

        // TEST-CASE2. 계정명과 동일하지 않은 Password는 안전하다고 판단하여
        // Exception이 발생하지 않는다.
        assertThatCode(() -> PasswordValidation.checkSameUserId(userId, usablePwd)).doesNotThrowAnyException();

    }

}
