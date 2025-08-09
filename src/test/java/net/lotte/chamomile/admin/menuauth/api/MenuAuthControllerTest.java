package net.lotte.chamomile.admin.menuauth.api;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import net.lotte.chamomile.admin.WebApplicationTest;

class MenuAuthControllerTest extends WebApplicationTest {

    @Test
    @DisplayName("메뉴-권한 목록 호출 성공")
    void getMenuAuthList() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder("/chmm/menu-auth/list")
                .param("searchMenuLvl","")
                .param("searchMenuName","")
                .param("searchMenuUri","")
                .param("searchUseYn","")
                .param("searchUpperMenuId","")
                .param("searchMenuHelpUri","");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"));
    }

    @Test
    @DisplayName("메뉴-권한 상세 호출 성공")
    void getMenuAuthDetail() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder("/chmm/menu-auth/detail")
                .param("searchMenuId", "menu00000001");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"));
    }

    @Test
    @DisplayName("메뉴-권한 수정 성공")
    void updateMenuAuth() throws Exception {
        // when
        String menuId = "menu00000001";
        List<String> roleIdList = Lists.newArrayList("ROLE_DEFAULT_ID","ROLE_USER_ID");
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder("/chmm/menu-auth/update/" + menuId)
                .content(objectMapper.writeValueAsString(roleIdList));

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"));
    }

    @Test
    @DisplayName("권한-메뉴 목록 호출 성공")
    void getAuthMenuList() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder("/chmm/auth-menu/list")
                .param("searchRoleId","")
                .param("searchRoleName","")
                .param("searchRoleStartDt","")
                .param("searchRoleEndDt","")
                .param("searchUseYn","");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"));
    }

    @Test
    @DisplayName("권한-메뉴 상세 호출 성공")
    @Disabled//TODO:수정해야함 H2 빈문자열 불리언 변환문제있음.
    void getAuthMenuDetail() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder("/chmm/auth-menu/detail")
                .param("searchRoleId", "ROLE_ADMIN_ID");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.tree.topNodes[0].key").value("root"));
    }

    @Test
    @DisplayName("권한-메뉴 수정 성공")
    void updateAuthMenu() throws Exception {
        // when
        String roleId = "ROLE_ADMIN_ID";
        List<String> menuIdList = Lists.newArrayList("menu00000002","menu00000003");
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder("/chmm/auth-menu/update/" + roleId)
                .content(objectMapper.writeValueAsString(menuIdList));

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"));
    }
}
