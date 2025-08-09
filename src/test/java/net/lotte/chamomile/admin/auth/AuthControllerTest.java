package net.lotte.chamomile.admin.auth;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import net.lotte.chamomile.admin.WebApplicationTest;

class AuthControllerTest extends WebApplicationTest {

    @Test
    @DisplayName("권한 목록 호출 성공")
    void getAuthList() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder("/chmm/auth/list");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"));
    }


    @Test
    @DisplayName("권한 생성 성공")
    void createAuth() throws Exception {
        // given
        String jsonContent = "{\n" +
                "    \"flag\": \"I\",\n" +
                "    \"roleId\": \"test5\",\n" +
                "    \"roleName\": \"테스트5\",\n" +
                "    \"roleDesc\": \"데스트\",\n" +
                "    \"roleStartDt\": \"20231010\",\n" +
                "    \"roleEndDt\": \"20231031\",\n" +
                "    \"useYn\": \"1\"\n" +
                "}";
        // when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder("/chmm/auth/create")
                .content(jsonContent);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"));
    }

    @Test
    @DisplayName("권한 삭제 성공")
    void deleteResource() throws Exception {
        createAuth();
        // given
        String jsonContent = "[{\n" +
                "    \"roleId\": \"test5\"\n" +
                "}]";

        // when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder("/chmm/auth/delete")
                .content(jsonContent);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"));
    }

    @Test
    @DisplayName("권한 중복 확인")
    void userCheckId() throws Exception {
        String jsonContent = "{\n" +
                "    \"searchRoleId\": \"test5\"\n" +
                "}";
        // when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder("/chmm/auth/auth-check-id")
                .content(jsonContent);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.valid").value("true"));
    }
}
