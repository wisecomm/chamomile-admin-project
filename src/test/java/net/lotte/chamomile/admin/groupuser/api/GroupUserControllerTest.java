package net.lotte.chamomile.admin.groupuser.api;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import net.lotte.chamomile.admin.WebApplicationTest;

public class GroupUserControllerTest extends WebApplicationTest {
    private static final String BASE_URL = "/chmm/group-user";

    @Test
    @DisplayName("그룹 유저 상세 호출 성공")
    void getGroupDetail() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder(BASE_URL + "/detail")
                .param("groupId", "group01");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.returnList", hasSize(2)))
                .andExpect(jsonPath("$.data.returnList.[0].groupId").value("group01"))
                .andExpect(jsonPath("$.data.returnList.[0].userId").value("chmm23"))
                .andExpect(jsonPath("$.data.returnList.[0].groupName").value("그룹01"))
                .andExpect(jsonPath("$.data.returnList.[1].groupId").value("group01"))
                .andExpect(jsonPath("$.data.returnList.[1].userId").value("user"))
                .andExpect(jsonPath("$.data.returnList.[1].groupName").value("그룹01"));
    }

    @Test
    @DisplayName("그룹 유저 상세 호출 성공 (상하 관계 있음)")
    void getGroupUserDetailSuccessHierarchy() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder(BASE_URL + "/detail")
                .param("groupId", "group04");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.returnList", hasSize(3)));
    }

    @Test
    @DisplayName("그룹 유저 수정 성공")
    void updateGroupUser() throws Exception {
        // given - 최종적으로 매핑할 유저의 id만 넘김
        List<String> deleteIds = Collections.singletonList("chmm23");
        String content = objectMapper.writeValueAsString(deleteIds);

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
        MockHttpServletRequestBuilder result = getRequestBuilder(BASE_URL + "/detail")
                .param("groupId", "group04");

        // then
        mockMvc.perform(result)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.returnList", hasSize(2)));
    }

    @Test
    @DisplayName("그룹 유저 수정 실패(그룹ID 없음)")
    void updateGroupUserFailureNoGroupId() throws Exception {
        // given - 최종적으로 매핑할 유저의 id만 넘김
        List<String> deleteIds = Collections.singletonList("chmm23");
        String content = objectMapper.writeValueAsString(deleteIds);

        // when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder(BASE_URL + "/update")
                .param("groupId", "group19")
                .content(content);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("500"))
                .andExpect(jsonPath("$.message").value("해당 그룹이 존재하지 않습니다."));
    }

    @Test
    @DisplayName("그룹 유저 수정 실패(유저ID 없음)")
    void updateGroupUserFailureNoUserId() throws Exception {
        // given - 최종적으로 매핑할 유저의 id만 넘김
        List<String> deleteIds = Collections.singletonList("hello");
        String content = objectMapper.writeValueAsString(deleteIds);

        // when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder(BASE_URL + "/update")
                .param("groupId", "group10")
                .content(content);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("500"))
                .andExpect(jsonPath("$.message").value("해당 유저가 존재하지 않습니다."));
    }
}
