package net.lotte.chamomile.admin.authgroup.api;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import net.lotte.chamomile.admin.WebApplicationTest;

public class AuthGroupControllerTest extends WebApplicationTest {
    private static final String BASE_URL = "/chmm/auth-group";

    @Test
    @DisplayName("그룹 권한 목록 호출 성공")
    void getAuthGroupDetail() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder(BASE_URL + "/detail")
                .param("groupId", "group01");

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
                .andExpect(jsonPath("$.data.topNodes[0].children[0].from").isEmpty())
                .andExpect(jsonPath("$.data.topNodes[0].children[0].children").isNotEmpty())
                .andExpect(jsonPath("$.data.topNodes[0].children[0].children[0].roleId").value("ROLE_DEFAULT_ID"))
                .andExpect(jsonPath("$.data.topNodes[0].children[0].children[0].roleName").value("기본 권한"))
                .andExpect(jsonPath("$.data.topNodes[0].children[0].children[0].hasRole").value(true))
                .andExpect(jsonPath("$.data.topNodes[0].children[0].children[0].from").isEmpty())
                .andExpect(jsonPath("$.data.topNodes[0].children[0].children[0].children").isEmpty());
    }

    @Test
    @DisplayName("그룹 권한 목록 호출 빈트리 노출")
    void getAuthGroupDetailEmptyGroup() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder(BASE_URL + "/detail")
                .param("groupId", "group01")
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
                .andExpect(jsonPath("$.data.topNodes[0].children[0].from").isEmpty())
                .andExpect(jsonPath("$.data.topNodes[0].children[0].children").isNotEmpty())
                .andExpect(jsonPath("$.data.topNodes[0].children[0].children[0].roleId").value("ROLE_DEFAULT_ID"))
                .andExpect(jsonPath("$.data.topNodes[0].children[0].children[0].roleName").value("기본 권한"))
                .andExpect(jsonPath("$.data.topNodes[0].children[0].children[0].hasRole").value(false))
                .andExpect(jsonPath("$.data.topNodes[0].children[0].children[0].from").isEmpty())
                .andExpect(jsonPath("$.data.topNodes[0].children[0].children[0].children").isEmpty());
    }

    @Test
    @DisplayName("그룹 권한 수정 성공")
    void updateAuthGroup() throws Exception {
        // given
        List<String> roleIds = new ArrayList<>();
        roleIds.add("ROLE_ADMIN_ID");
        String content = objectMapper.writeValueAsString(roleIds);

        // when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder(BASE_URL + "/update")
                .param("groupId", "group01")
                .content(content);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"));

        // when
        MockHttpServletRequestBuilder requestBuilder2 = getRequestBuilder(BASE_URL + "/detail")
                .param("groupId", "group01");

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
                .andExpect(jsonPath("$.data.topNodes[0].children[0].from").isEmpty())
                .andExpect(jsonPath("$.data.topNodes[0].children[0].children").isNotEmpty())
                .andExpect(jsonPath("$.data.topNodes[0].children[0].children[0].roleId").value("ROLE_DEFAULT_ID"))
                .andExpect(jsonPath("$.data.topNodes[0].children[0].children[0].roleName").value("기본 권한"))
                .andExpect(jsonPath("$.data.topNodes[0].children[0].children[0].hasRole").value(true))
                .andExpect(jsonPath("$.data.topNodes[0].children[0].children[0].from").isEmpty())
                .andExpect(jsonPath("$.data.topNodes[0].children[0].children[0].children").isEmpty());


    }

}
