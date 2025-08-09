package net.lotte.chamomile.admin.grouphierarchy.api;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import net.lotte.chamomile.admin.WebApplicationTest;
import net.lotte.chamomile.admin.grouphierarchy.domain.GroupHierarchyMapper;
import net.lotte.chamomile.admin.grouphierarchy.domain.GroupHierarchyVO;

public class GroupHierarchyControllerTest extends WebApplicationTest {
    private static final String BASE_URL = "/chmm/group-hierarchy";

    @Autowired
    private GroupHierarchyMapper groupHierarchyMapper;

    @Test
    @DisplayName("그룹 전체 상하 목록 호출 성공")
    void getGroupHierarchyList() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder(BASE_URL + "/list")
                .param("page", "0")
                .param("size", "10");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.content",hasSize(4)))
                .andExpect(jsonPath("$.data.pageable.pageSize").value("10"))
                .andExpect(jsonPath("$.data.totalPages").value("1"))
                .andExpect(jsonPath("$.data.numberOfElements").value("4"));
    }

    @Test
    @DisplayName("그룹 상세 하위 목록 호출 성공(하위그룹이 하위그룹을 가지지 않음)")
    void getGroupHierarchyDetailSuccessNoChild() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder(BASE_URL + "/detail")
                .param("groupId", "group11");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data", hasSize(1)));
    }

    @Test
    @DisplayName("그룹 상세 하위 목록 호출 성공(하위그룹이 하위그룹을 가짐)")
    void getGroupHierarchyDetailSuccessHasChild() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder(BASE_URL + "/detail")
                .param("groupId", "group10");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data", hasSize(3)));
    }

    @Test
    @DisplayName("그룹 상세 하위 목록 호출 실패(그룹 없음)")
    void getGroupDetailFailureNoGroupId() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder(BASE_URL + "/detail")
                .param("groupId", "group14");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("500"))
                .andExpect(jsonPath("$.message").value("그룹이 존재하지 않습니다."));
    }

    @Test
    @DisplayName("그룹 상하 관계 생성 성공")
    void createGroupHierarchy() throws Exception {
        // given
        Map<String, String> body = new HashMap<>();
        body.put("parentGroupId", "group09");
        body.put("childGroupId", "group10");

        String content = objectMapper.writeValueAsString(body);

        // when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder(BASE_URL + "/create")
                .content(content);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"));

        // when
        MockHttpServletRequestBuilder result = getRequestBuilder(BASE_URL + "/detail")
                .param("groupId", "group09");

        // then
        mockMvc.perform(result)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data", hasSize(4)));
    }

    @Test
    @DisplayName("그룹 상하 관계 생성 실패(parentId 없음)")
    void createGroupHierarchyFailureWrongParentId() throws Exception {
        // given
        Map<String, String> body = new HashMap<>();
        body.put("parentGroupId", "group15");
        body.put("childGroupId", "group10");

        String content = objectMapper.writeValueAsString(body);

        // when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder(BASE_URL + "/create")
                .content(content);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("500"))
                .andExpect(jsonPath("$.message").value("부모 그룹이 존재하지 않습니다."));
    }

    @Test
    @DisplayName("그룹 상하 관계 생성 실패(ChildId 없음)")
    void createGroupHierarchyFailureWrongChildId() throws Exception {
        // given
        Map<String, String> body = new HashMap<>();
        body.put("parentGroupId", "group10");
        body.put("childGroupId", "group15");

        String content = objectMapper.writeValueAsString(body);

        // when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder(BASE_URL + "/create")
                .content(content);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("500"))
                .andExpect(jsonPath("$.message").value("자식 그룹이 존재하지 않습니다."));
    }

    @Test
    @DisplayName("그룹 상하 관계 생성 실패(Cycle 에러)")
    void createGroupHierarchyFailureIsCycle() throws Exception {
        // given
        Map<String, String> body = new HashMap<>();
        body.put("parentGroupId", "group12");
        body.put("childGroupId", "group10");

        String content = objectMapper.writeValueAsString(body);

        // when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder(BASE_URL + "/create")
                .content(content);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("500"))
                .andExpect(jsonPath("$.message").value("Cycle Error"));
    }

    @Test
    @DisplayName("그룹 상하 관계 생성 실패(parentId NULL)")
    void createGroupHierarchyFailureNoParentId() throws Exception {
        // given
        Map<String, String> body = new HashMap<>();
        body.put("parentGroupId", null);
        body.put("childGroupId", "group10");

        String content = objectMapper.writeValueAsString(body);

        // when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder(BASE_URL + "/create")
                .content(content);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("부모 그룹 ID는 필수 입력값 입니다."));
    }

    @Test
    @DisplayName("그룹 상하 관계 생성 실패(childId NULL)")
    void createGroupHierarchyFailureNoChildId() throws Exception {
        // given
        Map<String, String> body = new HashMap<>();
        body.put("parentGroupId", "group12");
        body.put("childGroupId", null);

        String content = objectMapper.writeValueAsString(body);

        // when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder(BASE_URL + "/create")
                .content(content);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("자식 그룹 ID는 필수 입력값 입니다."));
    }

    @Test
    @DisplayName("그룹 삭제 성공")
    void deleteGroupHierarchy() throws Exception {
        // given
        List<GroupHierarchyVO> deleteIds = Arrays.asList(
                new GroupHierarchyVO("group10","group11"),
                new GroupHierarchyVO("group11","group12"));
        String content = objectMapper.writeValueAsString(deleteIds);

        // when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder(BASE_URL + "/delete")
                .content(content);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"));

        assertThat(groupHierarchyMapper.findGroupHierarchyList("group10")).hasSize(1);
    }

    @Test
    @DisplayName("그룹 삭제 실패(상하 관계 매핑 오류)")
    void deleteGroupHierarchyFailure() throws Exception {
        // given
        List<GroupHierarchyVO> deleteIds = Collections.singletonList(
                new GroupHierarchyVO("group10", "group1"));
        String content = objectMapper.writeValueAsString(deleteIds);

        // when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder(BASE_URL + "/delete")
                .content(content);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("500"))
                .andExpect(jsonPath("$.message").value("그룹 상하관계가 존재하지 않습니다."));

    }
}
