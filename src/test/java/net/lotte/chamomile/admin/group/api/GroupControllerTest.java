package net.lotte.chamomile.admin.group.api;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import net.lotte.chamomile.admin.WebApplicationTest;
import net.lotte.chamomile.admin.group.domain.GroupMapper;
import net.lotte.chamomile.admin.group.domain.GroupVO;
import net.lotte.chamomile.admin.grouphierarchy.domain.GroupHierarchyMapper;
import net.lotte.chamomile.admin.groupuser.domain.GroupUserMapper;

public class GroupControllerTest extends WebApplicationTest {

    private static final String BASE_URL = "/chmm/group";

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private GroupHierarchyMapper groupHierarchyMapper;

    @Autowired
    private GroupUserMapper groupUserMapper;

    @Test
    @DisplayName("그룹 목록 호출 성공")
    void getGroupList() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder(BASE_URL + "/list")
                .param("page", "1")
                .param("size", "10")
                .param("sort", "groupId,desc")
                .param("sort", "sysInsertDtm,desc");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.content[0].groupId").value("group03"))
                .andExpect(jsonPath("$.data.pageable.pageSize").value("10"))
                .andExpect(jsonPath("$.data.totalPages").value("2"))
                .andExpect(jsonPath("$.data.numberOfElements").value("3"));
    }

    @Test
    @DisplayName("그룹 상세 호출 성공")
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
                .andExpect(jsonPath("$.data.groupId").value("group01"))
                .andExpect(jsonPath("$.data.parentGroupIds[0]").value("group04"));
    }

    @Test
    @DisplayName("그룹 상세 호출 실패 (그룹 없음)")
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
    @DisplayName("그룹 ID 체크 성공")
    void getGroupCheck() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder(BASE_URL + "/check")
                .param("groupId", "group01");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data").value("false"));
    }

    @Test
    @DisplayName("그룹 ID 체크 실패 (그룹 없음)")
    void getGroupCheckFailureNoGroupId() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder(BASE_URL + "/check")
                .param("groupId", "group14");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data").value("true"));
    }

    @Test
    @DisplayName("그룹 생성 성공")
    void createGroup() throws Exception {
        // given
        Map<String, String> body = new HashMap<>();
        body.put("groupId", "group13");
        body.put("groupName", "그룹13 이름");
        body.put("groupDesc", "그룹13 설명");
        body.put("useYn", "1");

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

        GroupVO groupVO = groupMapper.findGroup("group13")
                .orElseThrow(() -> new Exception("그룹이 존재하지 않습니다."));
        assertThat(groupVO.getGroupId()).isEqualTo("group13");
        assertThat(groupVO.getGroupName()).isEqualTo("그룹13 이름");
        assertThat(groupVO.getGroupDesc()).isEqualTo("그룹13 설명");
        assertThat(groupVO.getUseYn()).isEqualTo("1");
        assertThat(groupVO.getSysInsertDtm()).isNotNull();
        assertThat(groupVO.getSysInsertUserId()).isEqualTo("chmm23");
    }

    @Test
    @DisplayName("그룹 생성 실패 (PK 중복)")
    void createGroupFailure() throws Exception {
        // given
        Map<String, String> body = new HashMap<>();
        body.put("groupId", "group10");
        body.put("groupName", "그룹13 이름");
        body.put("groupDesc", "그룹13 설명");
        body.put("useYn", "1");

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
                .andExpect(jsonPath("$.message").value("Unique index or primary key violation"));
    }

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = "12345123451234512345123451234512345123451234512345XXX")
    @DisplayName("그룹 생성 실패 (groupId 길이)")
    void createGroupFailureGroupIdLength(String groupId) throws Exception {
        // given
        Map<String, String> body = new HashMap<>();
        body.put("groupId", groupId);
        body.put("groupName", "그룹13 이름이름");
        body.put("groupDesc", "그룹13 설명설명");
        body.put("useYn", "1");

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
                .andExpect(jsonPath("$.message").value("size must be between 5 and 50"));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("그룹 생성 실패 (groupId 없음)")
    void createGroupFailureNoGroupId(String groupId) throws Exception {
        // given
        Map<String, String> body = new HashMap<>();
        body.put("groupId", groupId);
        body.put("groupName", "그룹13 이름이름");
        body.put("groupDesc", "그룹13 설명설명");
        body.put("useYn", "1");

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
                .andExpect(jsonPath("$.message").value("그룹 아이디는 필수값입니다."));
    }

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = "Y")
    @DisplayName("그룹 생성 실패 (useYn 없음)")
    void createGroupFailureNoUseYn(String useYn) throws Exception {
        // given
        Map<String, String> body = new HashMap<>();
        body.put("groupId", "group13");
        body.put("groupName", "그룹13 이름이름");
        body.put("groupDesc", "그룹13 설명설명");
        body.put("useYn", useYn);

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
                .andExpect(jsonPath("$.message").value("참/거짓은 0/1로 구분 합니다."));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("그룹 생성 실패 (useYn NULL)")
    void createGroupFailureNullUseYn(String useYn) throws Exception {
        // given
        Map<String, String> body = new HashMap<>();
        body.put("groupId", "group13");
        body.put("groupName", "그룹13 이름이름");
        body.put("groupDesc", "그룹13 설명설명");
        body.put("useYn", useYn);

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
                .andExpect(jsonPath("$.message").value("사용여부는 필수값입니다."));
    }


    @Test
    @DisplayName("그룹 생성 실패 (groupName 길이)")
    void createGroupFailureGroupNameLength() throws Exception {
        // given
        Map<String, String> body = new HashMap<>();
        body.put("groupId", "group13");
        body.put("groupName", "그룹13");
        body.put("groupDesc", "설명131234124");
        body.put("useYn", "1");

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
                .andExpect(jsonPath("$.message").value("size must be between 5 and 50"));
    }

    @Test
    @DisplayName("그룹 생성 실패 (groupDesc 길이)")
    void createGroupFailureGroupDescLength() throws Exception {
        // given
        Map<String, String> body = new HashMap<>();
        body.put("groupId", "group13");
        body.put("groupName", "그룹1313");
        body.put("groupDesc", "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012");
        body.put("useYn", "1");

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
                .andExpect(jsonPath("$.message").value("size must be between 0 and 200"));
    }

    @Test
    @DisplayName("그룹 수정 성공")
    void updateGroup() throws Exception {
        // given
        Map<String, String> body = new HashMap<>();
        body.put("groupId", "group12");
        body.put("groupName", "그룹12 변경");
        body.put("groupDesc", "그룹12 설명변경");
        body.put("useYn", "0");

        String content = objectMapper.writeValueAsString(body);

        // when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder(BASE_URL + "/update")
                .content(content);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"));

        GroupVO groupVO = groupMapper.findGroup("group12")
                .orElseThrow(() -> new Exception("그룹이 존재하지 않습니다."));
        assertThat(groupVO.getGroupId()).isEqualTo("group12");
        assertThat(groupVO.getGroupName()).isEqualTo("그룹12 변경");
        assertThat(groupVO.getGroupDesc()).isEqualTo("그룹12 설명변경");
        assertThat(groupVO.getUseYn()).isEqualTo("0");
        assertThat(groupVO.getSysInsertDtm()).isNotEqualTo(groupVO.getSysUpdateDtm());
        assertThat(groupVO.getSysUpdateUserId()).isEqualTo("chmm23");
    }

    @Test
    @DisplayName("그룹 삭제 성공")
    void deleteGroup() throws Exception {
        // given
        List<GroupVO> deleteIds = Arrays.asList(GroupVO.id("group11"), GroupVO.id("group12"));


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

        assertThatCode(() -> groupMapper.findGroup("group11").orElseThrow(NoSuchElementException::new))
                .isInstanceOf(NoSuchElementException.class);
        assertThatCode(() -> groupMapper.findGroup("group12").orElseThrow(NoSuchElementException::new))
                .isInstanceOf(NoSuchElementException.class);
        assertThat(groupHierarchyMapper.findGroupHierarchyList("group11")).isEmpty();
        assertThat(groupHierarchyMapper.findGroupHierarchyList("group12")).isEmpty();
        assertThat(groupUserMapper.findGroupUserList("group11")).isEmpty();
        assertThat(groupUserMapper.findGroupUserList("group12")).isEmpty();
    }
}
