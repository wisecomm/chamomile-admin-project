package net.lotte.chamomile.admin.authresource.api;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import net.lotte.chamomile.admin.WebApplicationTest;
import net.lotte.chamomile.admin.authresource.api.dto.AuthResourceQuery;
import net.lotte.chamomile.admin.authresource.domain.AuthResourceMapper;
import net.lotte.chamomile.admin.authtree.AuthTreeVO;

public class AuthResourceControllerTest extends WebApplicationTest {

    private static final String BASE_URL = "/chmm/auth-resource";

    @Autowired
    private AuthResourceMapper authResourceMapper;

    @Test
    @DisplayName("리소스 권한 페이지 리소스 목록 호출 성공")
    void getAuthResourceList() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder(BASE_URL + "/list");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.content[0].resourceId").value("RESOURCE_ADMIN_ID"));
    }

    @Test
    @DisplayName("리소스 권한 페이지 리소스 상세 호출 성공")
    void getAuthResourceDetail() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder(BASE_URL + "/detail")
                .param("resourceId", "RESOURCE_ADMIN_ID");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.authResourceVO.resourceId").value("RESOURCE_ADMIN_ID"))
                .andExpect(jsonPath("$.data.tree.topNodes",hasSize(1)))
                .andExpect(jsonPath("$.data.tree.topNodes[0].roleId").value("ROLE_ADMIN_ID"))
                .andExpect(jsonPath("$.data.tree.topNodes[0].roleName").value("어드민 권한"))
                .andExpect(jsonPath("$.data.tree.topNodes[0].hasRole").value(true))
                .andExpect(jsonPath("$.data.tree.topNodes[0].from").isEmpty())
                .andExpect(jsonPath("$.data.tree.topNodes[0].children").isNotEmpty())
                .andExpect(jsonPath("$.data.tree.topNodes[0].children[0].roleId").value("ROLE_USER_ID"))
                .andExpect(jsonPath("$.data.tree.topNodes[0].children[0].roleName").value("사용자 권한"))
                .andExpect(jsonPath("$.data.tree.topNodes[0].children[0].hasRole").value(false))
                .andExpect(jsonPath("$.data.tree.topNodes[0].children[0].from").isEmpty())
                .andExpect(jsonPath("$.data.tree.topNodes[0].children[0].children").isNotEmpty())
                .andExpect(jsonPath("$.data.tree.topNodes[0].children[0].children[0].roleId").value("ROLE_DEFAULT_ID"))
                .andExpect(jsonPath("$.data.tree.topNodes[0].children[0].children[0].roleName").value("기본 권한"))
                .andExpect(jsonPath("$.data.tree.topNodes[0].children[0].children[0].hasRole").value(false))
                .andExpect(jsonPath("$.data.tree.topNodes[0].children[0].children[0].from").isEmpty())
                .andExpect(jsonPath("$.data.tree.topNodes[0].children[0].children[0].children").isEmpty())
                .andExpect(jsonPath("$.data.authResourceLeftList[0].roleId").value("ROLE_DEFAULT_ID"))
                .andExpect(jsonPath("$.data.authResourceLeftList[0].roleName").value("기본 권한"))
                .andExpect(jsonPath("$.data.authResourceLeftList[1].roleId").value("ROLE_USER_ID"))
                .andExpect(jsonPath("$.data.authResourceLeftList[1].roleName").value("사용자 권한"))
                .andExpect(jsonPath("$.data.authResourceList[0].roleId").value("ROLE_ADMIN_ID"))
                .andExpect(jsonPath("$.data.authResourceList[0].roleName").value("어드민 권한"));

    }

    @Test
    @DisplayName("리소스 권한 페이지 리소스 상세 호출 성공 권한 호출 안함")
    void getAuthResourceDetailTree() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder(BASE_URL + "/detail")
                .param("resourceId", "RESOURCE_ADMIN_ID")
                .param("showEmptyTreeYn", "1");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.authResourceVO.resourceId").value("RESOURCE_ADMIN_ID"))
                .andExpect(jsonPath("$.data.tree.topNodes",hasSize(1)))
                .andExpect(jsonPath("$.data.tree.topNodes[0].roleId").value("ROLE_ADMIN_ID"))
                .andExpect(jsonPath("$.data.tree.topNodes[0].roleName").value("어드민 권한"))
                .andExpect(jsonPath("$.data.tree.topNodes[0].hasRole").value(false))
                .andExpect(jsonPath("$.data.tree.topNodes[0].from").isEmpty())
                .andExpect(jsonPath("$.data.tree.topNodes[0].children").isNotEmpty())
                .andExpect(jsonPath("$.data.tree.topNodes[0].children[0].roleId").value("ROLE_USER_ID"))
                .andExpect(jsonPath("$.data.tree.topNodes[0].children[0].roleName").value("사용자 권한"))
                .andExpect(jsonPath("$.data.tree.topNodes[0].children[0].hasRole").value(false))
                .andExpect(jsonPath("$.data.tree.topNodes[0].children[0].from").isEmpty())
                .andExpect(jsonPath("$.data.tree.topNodes[0].children[0].children").isNotEmpty())
                .andExpect(jsonPath("$.data.tree.topNodes[0].children[0].children[0].roleId").value("ROLE_DEFAULT_ID"))
                .andExpect(jsonPath("$.data.tree.topNodes[0].children[0].children[0].roleName").value("기본 권한"))
                .andExpect(jsonPath("$.data.tree.topNodes[0].children[0].children[0].hasRole").value(false))
                .andExpect(jsonPath("$.data.tree.topNodes[0].children[0].children[0].from").isEmpty())
                .andExpect(jsonPath("$.data.tree.topNodes[0].children[0].children[0].children").isEmpty());
    }

    @Test
    @DisplayName("리소스 권한 페이지 리소스 상세 호출 실패(없는 자원)")
    void getAuthResourceDetailFailureNoResource() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder(BASE_URL + "/detail")
                .param("resourceId", "RESOURCE_asdf");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("500"))
                .andExpect(jsonPath("$.message").value("해당 리소스가 존재하지 않습니다.{RESOURCE_asdf}"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("리소스 권한 페이지 리소스 상세 호출 실패(입력값 없음)")
    void getAuthResourceDetailFailureNullAndEmpty(String input) throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder(BASE_URL + "/detail")
                .param("resourceId", input);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("권한 리소스 ID는 필수 값 입니다."));
    }

    @Test
    @DisplayName("리소스 권한 수정 성공")
    void getAuthResourceUpdate() throws Exception {
        // given
        String resourceId = "RESOURCE_ADMIN_ID";
        Map<String, String> rightValue1 = new HashMap<>();
        rightValue1.put("fixed", "false");
        rightValue1.put("origin", "left");
        rightValue1.put("text", "기본 리소스");
        rightValue1.put("val", "ROLE_DEFAULT_ID");
        Map<String, String> rightValue2 = new HashMap<>();
        rightValue2.put("fixed", "false");
        rightValue2.put("origin", "left");
        rightValue2.put("text", "어드민 리소스");
        rightValue2.put("val", "ROLE_ADMIN_ID");
        List<Map<String, String>> rightValues = new ArrayList<>();
        rightValues.add(rightValue1);
        rightValues.add(rightValue2);

        Map<String, Object> body = new HashMap<>();
        body.put("resourceId", resourceId);
        body.put("rightValue", rightValues);

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

        // then
        List<AuthTreeVO> result = authResourceMapper.findUsedResourceList(new AuthResourceQuery("RESOURCE_ADMIN_ID"));
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getRoleId()).isEqualTo("ROLE_DEFAULT_ID");
        assertThat(result.get(1).getRoleId()).isEqualTo("ROLE_ADMIN_ID");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("리소스 권한 수정 실패(resourceId null and empty)")
    void getAuthResourceUpdateFailure(String resourceId) throws Exception {
        String asdf = "asdf";
        //given
        Map<String, Object> body = new HashMap<>();
        body.put("resourceId", resourceId);

        String content = objectMapper.writeValueAsString(body);
        // when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder(BASE_URL + "/update")
                .content(content);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("resourceId는 필수 입력값입니다."));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("리소스 권한 수정 실패(val null and empty)")
    void getAuthResourceUpdateFailureVal(String val) throws Exception {
        // given
        String resourceId = "RESOURCE_ADMIN_ID";
        Map<String, String> rightValue1 = new HashMap<>();
        rightValue1.put("fixed", "false");
        rightValue1.put("origin", "left");
        rightValue1.put("text", "기본 리소스");
        rightValue1.put("val", val);
        List<Map<String, String>> rightValues = new ArrayList<>();
        rightValues.add(rightValue1);

        Map<String, Object> body = new HashMap<>();
        body.put("resourceId", resourceId);
        body.put("rightValue", rightValues);

        String content = objectMapper.writeValueAsString(body);

        // when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder(BASE_URL + "/update")
                .content(content);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("val은 필수 입력값입니다."));
    }
}
