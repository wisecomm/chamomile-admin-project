package net.lotte.chamomile.admin.authuser.api;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import net.lotte.chamomile.admin.WebApplicationTest;
import net.lotte.chamomile.admin.authuser.domain.AuthUserVO;

public class AuthUserControllerTest extends WebApplicationTest {
    private static final String BASE_URL = "/chmm/auth-user";

    @Autowired
    private AuthUserHistoryMapper mapper;

    @Test
    @DisplayName("유저 권한 목록 호출 성공")
    void getAuthUserDetail() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder(BASE_URL + "/detail")
                .param("userId", "user");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.topNodes",hasSize(1)))
                .andExpect(jsonPath("$.data.topNodes[0].roleId").value("ROLE_ADMIN_ID"))
                .andExpect(jsonPath("$.data.topNodes[0].roleName").value("어드민 권한"))
                .andExpect(jsonPath("$.data.topNodes[0].hasRole").value(false))
                .andExpect(jsonPath("$.data.topNodes[0].from").isEmpty())
                .andExpect(jsonPath("$.data.topNodes[0].children").isNotEmpty())
                .andExpect(jsonPath("$.data.topNodes[0].children[0].roleId").value("ROLE_USER_ID"))
                .andExpect(jsonPath("$.data.topNodes[0].children[0].roleName").value("사용자 권한"))
                .andExpect(jsonPath("$.data.topNodes[0].children[0].hasRole").value(true))
                .andExpect(jsonPath("$.data.topNodes[0].children[0].from[0]").value("그룹01"))
                .andExpect(jsonPath("$.data.topNodes[0].children[0].children").isNotEmpty())
                .andExpect(jsonPath("$.data.topNodes[0].children[0].children[0].roleId").value("ROLE_DEFAULT_ID"))
                .andExpect(jsonPath("$.data.topNodes[0].children[0].children[0].roleName").value("기본 권한"))
                .andExpect(jsonPath("$.data.topNodes[0].children[0].children[0].hasRole").value(true))
                .andExpect(jsonPath("$.data.topNodes[0].children[0].children[0].from[0]").value("그룹01"))
                .andExpect(jsonPath("$.data.topNodes[0].children[0].children[0].children").isEmpty());
    }

    @Test
    @DisplayName("유저 권한 목록 호출 성공 tree 1이면 빈트리")
    void getAuthUserDetailNoUserId() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder(BASE_URL + "/detail")
                .param("userId", "user")
                .param("showEmptyTreeYn", "1");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.topNodes",hasSize(1)))
                .andExpect(jsonPath("$.data.topNodes[0].roleId").value("ROLE_ADMIN_ID"))
                .andExpect(jsonPath("$.data.topNodes[0].roleName").value("어드민 권한"))
                .andExpect(jsonPath("$.data.topNodes[0].hasRole").value(false))
                .andExpect(jsonPath("$.data.topNodes[0].from").isEmpty())
                .andExpect(jsonPath("$.data.topNodes[0].children").isNotEmpty())
                .andExpect(jsonPath("$.data.topNodes[0].children[0].roleId").value("ROLE_USER_ID"))
                .andExpect(jsonPath("$.data.topNodes[0].children[0].roleName").value("사용자 권한"))
                .andExpect(jsonPath("$.data.topNodes[0].children[0].hasRole").value(false))
                .andExpect(jsonPath("$.data.topNodes[0].children[0].from[0]").value("그룹01"))
                .andExpect(jsonPath("$.data.topNodes[0].children[0].children").isNotEmpty())
                .andExpect(jsonPath("$.data.topNodes[0].children[0].children[0].roleId").value("ROLE_DEFAULT_ID"))
                .andExpect(jsonPath("$.data.topNodes[0].children[0].children[0].roleName").value("기본 권한"))
                .andExpect(jsonPath("$.data.topNodes[0].children[0].children[0].hasRole").value(false))
                .andExpect(jsonPath("$.data.topNodes[0].children[0].children[0].from[0]").value("그룹01"))
                .andExpect(jsonPath("$.data.topNodes[0].children[0].children[0].children").isEmpty());
    }

    @Test
    @DisplayName("유저 권한 수정 성공(HISTORY 적재 성공)")
    void updateAuthUser() throws Exception {
        // given
        List<String> roleIds = new ArrayList<>();
        roleIds.add("ROLE_ADMIN_ID");
        String content = objectMapper.writeValueAsString(roleIds);

        // when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder(BASE_URL + "/update")
                .param("userId", "user")
                .content(content);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"));

        // then - history 결과가 데이터베이스에 쌓여있어야함
        List<AuthUserVO> user = mapper.findAuthUserHistory("user");
        assertThat(user).hasSize(2);// 삭제관련 쿼리1 + 추가된 권한1

        // when
        MockHttpServletRequestBuilder requestBuilder2 = getRequestBuilder(BASE_URL + "/detail")
                .param("userId", "user");

        // then
        mockMvc.perform(requestBuilder2)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.topNodes",hasSize(1)))
                .andExpect(jsonPath("$.data.topNodes[0].roleId").value("ROLE_ADMIN_ID"))
                .andExpect(jsonPath("$.data.topNodes[0].roleName").value("어드민 권한"))
                .andExpect(jsonPath("$.data.topNodes[0].hasRole").value(true))
                .andExpect(jsonPath("$.data.topNodes[0].from").isEmpty())
                .andExpect(jsonPath("$.data.topNodes[0].children").isNotEmpty())
                .andExpect(jsonPath("$.data.topNodes[0].children[0].roleId").value("ROLE_USER_ID"))
                .andExpect(jsonPath("$.data.topNodes[0].children[0].roleName").value("사용자 권한"))
                .andExpect(jsonPath("$.data.topNodes[0].children[0].hasRole").value(true))
                .andExpect(jsonPath("$.data.topNodes[0].children[0].from[0]").value("그룹01"))
                .andExpect(jsonPath("$.data.topNodes[0].children[0].children").isNotEmpty())
                .andExpect(jsonPath("$.data.topNodes[0].children[0].children[0].roleId").value("ROLE_DEFAULT_ID"))
                .andExpect(jsonPath("$.data.topNodes[0].children[0].children[0].roleName").value("기본 권한"))
                .andExpect(jsonPath("$.data.topNodes[0].children[0].children[0].hasRole").value(true))
                .andExpect(jsonPath("$.data.topNodes[0].children[0].children[0].from[0]").value("그룹01"))
                .andExpect(jsonPath("$.data.topNodes[0].children[0].children[0].children").isEmpty());
    }

}
