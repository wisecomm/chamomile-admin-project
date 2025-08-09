package net.lotte.chamomile.admin.user;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import net.lotte.chamomile.admin.WebApplicationTest;
import net.lotte.chamomile.admin.authuser.domain.AuthUserVO;

class UserControllerTest extends WebApplicationTest {

    @Autowired
    private UserHistoryMapper mapper;

    @Test
    @DisplayName("사용자 목록 호출 성공")
    void getUserList() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder("/chmm/user/list");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.content[0].userId").value("chmm23"));
    }

    @Test
    @DisplayName("사용자 목록 호출 성공 (검색_사용자_이름)")
    void getUserDetail() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder("/chmm/user/detail")
                .param("userId", "chmm23");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.userId").value("chmm23"));
    }


    @Test
    @DisplayName("사용자 생성 성공")
    void createUser() throws Exception {
        // given
        String jsonContent = "{\n" +
                "    \"flag\": \"I\",\n" +
                "    \"accountEndDt\": \"21001031\",\n" +
                "    \"accountNonLock\": \"1\",\n" +
                "    \"accountStartDt\": \"20231010\",\n" +
                "    \"passwordExpireDt\": \"21001031\",\n" +
                "    \"pwChange\": \"false\",\n" +
                "    \"useYn\": \"1\",\n" +
                "    \"userDesc\": \"\",\n" +
                "    \"userEmail\": \"test2@ldcc.com\",\n" +
                "    \"userId\": \"test10\",\n" +
                "    \"userMobile\": \"\",\n" +
                "    \"userMsg\": \"\",\n" +
                "    \"userName\": \"테스트2\",\n" +
                "    \"userNick\": \"test1\",\n" +
                "    \"userPwd\": \"qwert12345!!\",\n" +
                "    \"userPwdCheck\": \"qwert12345!!\",\n" +
                "    \"userSnsId\": \"test1\",\n" +
                "    \"userStatCd\": \"업무\"\n" +
                "}";
        // when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder("/chmm/user/create")
                .content(jsonContent);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"));

        //then
        List<UserHistoryVO> test10 = mapper.findUserHistory("test10");
        assertThat(test10).hasSize(1);
        assertThat(test10.get(0).getRevtype()).isEqualTo("0");
    }

    @Test
    @DisplayName("사용자 삭제 성공")
    void deleteUser() throws Exception {
        // given
        createUser();
        String jsonContent = "[\n" +
                "    {\n" +
                "        \"userId\": \"test10\"\n" +
                "    }\n" +
                "]";

        // when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder("/chmm/user/delete")
                .content(jsonContent);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"));

        //TODO: 사용자 삭제 히스토리 테스트코드에서만 발생하는 REVTYPE 오류 있는거 테스트해야함
    }

    @Test
    @DisplayName("사용자 중복 확인")
    void userCheckId() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder("/chmm/user/user-check-id")
                .param("searchUserId", "chmm23");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.valid").value("false"));
    }

    @Test
    @DisplayName("사용자 마지막 로그인정보 조회")
    void getLastSuccessLoginInfo() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder("/chmm/user/user-last-success")
                .param("searchUserId", "chmm23")
                .param("searchLogType", "LOGIN_SUCCESS");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"));
    }

}
