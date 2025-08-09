package net.lotte.chamomile.admin.privacypolicy.api;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jayway.jsonpath.JsonPath;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import net.lotte.chamomile.admin.WebApplicationTest;
import net.lotte.chamomile.admin.privacypolicy.domain.PrivacyPolicyMapper;
import net.lotte.chamomile.admin.privacypolicy.domain.PrivacyPolicyVO;

class PrivacyPolicyControllerTest extends WebApplicationTest {

    @Autowired
    private PrivacyPolicyMapper privacyPolicyMapper;

    @Test
    @DisplayName("정책 목록 호출 성공")
    void getPrivacyPolicyList() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder("/chmm/privacy-policy/list");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.content[0].policyVersion").value("1.0"));
    }

    @Test
    @DisplayName("정책 서브 목록 호출 성공")
    void getPrivacyPolicySubList() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder("/chmm/privacy-policy/sub-list")
                .param("policyId", "1");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.content[0].policyVersion").value("1.0"));
    }

    @Test
    @DisplayName("정책 호출 성공")
    void getPrivacyPolicyDetail() throws Exception {
        // when
        int policyId = 1;
        int policySubVersion = 1;
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder("/chmm/privacy-policy/detail/" + policyId + "/sub/" + policySubVersion);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"));
    }

    @Test
    @DisplayName("정책 생성 성공")
    void createPrivacyPolicy() throws Exception {
        // given
        String paramTitle = "Title 신규 정책 생성";
        String paramContent = "테스트 내용 1";
        Map<String, Object> body = createPrivacyPolicyBodyMap(null, paramTitle, paramContent);
        String content = objectMapper.writeValueAsString(body);

        // when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder("/chmm/privacy-policy/create")
                .content(content);

        // then
        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        LinkedHashMap<String, Object> data = JsonPath.parse(response).read("$.data");

        assertCreateResult(
                (long) (int) data.get("policyId"),
                (String) data.get("policyVersion"),
                (long) (int) data.get("policySubVersion"),
                paramTitle, paramContent);
    }

    @Test
    @DisplayName("정책 sub 생성 성공")
    void createPrivacyPolicySub() throws Exception {
        // given
        Long paramPolicyId = 1L;
        String paramTitle = "Title 하위 정책 추가";
        String paramContent = "테스트 내용 2";
        Map<String, Object> body = createPrivacyPolicySubBodyMap(paramPolicyId, "", paramTitle, paramContent);
        String content = objectMapper.writeValueAsString(body);

        // when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder("/chmm/privacy-policy/create-sub")
                .content(content);

        // then
        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        LinkedHashMap<String, Object> data = JsonPath.parse(response).read("$.data");

        // policyId 는 동일하나, policySubVersion 이 증가하여 1보다 커야함.
        assertThat((long) (int) data.get("policyId")).isEqualTo(paramPolicyId);
        assertThat((long) (int) data.get("policySubVersion")).isGreaterThan(1);
    }

    @Test
    @DisplayName("정책 버전업 성공")
    void increasePrivacyPolicy() throws Exception {
        // given
        String paramPolicyVersion = "1.0";
        String paramTitle = "Title 버전업";
        String paramContent = "테스트 내용 3";
        Map<String, Object> body = createPrivacyPolicyBodyMap(paramPolicyVersion, paramTitle, paramContent);
        String content = objectMapper.writeValueAsString(body);

        // when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder("/chmm/privacy-policy/increase")
                .content(content);

        // then
        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        LinkedHashMap<String, Object> data = JsonPath.parse(response).read("$.data");

        // policyVersion 이 0.1 증가하여야함.
        assertThat((String) data.get("policyVersion")).isGreaterThan(paramPolicyVersion);
    }

    @Test
    @DisplayName("정책 삭제 성공")
    void deletePrivacyPolicy() throws Exception {
        // given
        createPrivacyPolicy();
        List<PrivacyPolicyVO> body = Arrays.asList(new PrivacyPolicyVO(1L), new PrivacyPolicyVO(2L));
        String content = objectMapper.writeValueAsString(body);

        // when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder("/chmm/privacy-policy/delete")
                .content(content);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"));
    }

    @Test
    @DisplayName("정책 sub 삭제 성공")
    void deletePrivacyPolicySub() throws Exception {
        // given
        createPrivacyPolicySub();
        List<PrivacyPolicyVO> body = Arrays.asList(new PrivacyPolicyVO(1L, 1L), new PrivacyPolicyVO(1L, 2L), new PrivacyPolicyVO(1L, 3L));
        String content = objectMapper.writeValueAsString(body);

        // when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder("/chmm/privacy-policy/delete-sub")
                .content(content);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"));
    }

    @Test
    @DisplayName("정책 수정 성공")
    void updatePrivacyPolicy() throws Exception {
        //given
        long paramPolicyId = 1L;
        long paramPolicySubVersion = 1L;
        String paramTitle = "수정 테스트";
        String paramContent = "테스트";
        Map<String, Object> body = updatePrivacyPolicyBodyMap(paramPolicyId, paramPolicySubVersion, null, null,
                paramTitle, paramContent, "0", "0");
        String content = objectMapper.writeValueAsString(body);

        // when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder("/chmm/privacy-policy/update")
                .content(content);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"));

        // title, content 변경 확인
        PrivacyPolicyVO testId = privacyPolicyMapper.findPrivacyPolicyDetail(paramPolicyId, paramPolicySubVersion).get();
        assertThat(testId.getTitle()).isEqualTo(paramTitle);
        assertThat(testId.getContent()).isEqualTo(paramContent);
    }

    @Test
    @DisplayName("정책 적용 성공")
    void applyPrivacyPolicy() throws Exception {
        //given
        long paramPolicyId = 1L;
        long paramPolicySubVersion = 1L;
        String paramTitle = "적용 테스트";
        String paramContent = "테스트";
        Map<String, Object> body = updatePrivacyPolicyBodyMap(paramPolicyId, paramPolicySubVersion, "2024-09-26", "2024-09-26",
                paramTitle, paramContent, "1", "1");
        String content = objectMapper.writeValueAsString(body);

        // when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder("/chmm/privacy-policy/update")
                .content(content);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"));

        // title, content 변경 확인
        PrivacyPolicyVO testId = privacyPolicyMapper.findPrivacyPolicyDetail(paramPolicyId, paramPolicySubVersion).get();
        assertThat(testId.getTitle()).isEqualTo(paramTitle);
        assertThat(testId.getContent()).isEqualTo(paramContent);
    }

    private static Map<String, Object> createPrivacyPolicyBodyMap(String policyVersion, String title, String content) {
        Map<String, Object> body = new HashMap<>();
        body.put("policyId", "");
        body.put("policyVersion", policyVersion);
        body.put("policyNoticeDt", "");
        body.put("policyStartDt", "");
        body.put("postYn", "0");
        body.put("policySubVersion", "");
        body.put("title", title);
        body.put("content", content);
        body.put("applyYn", "0");
        return body;
    }

    private static Map<String, Object> createPrivacyPolicySubBodyMap(Long policyId, String policySubVersion, String title,
            String content) {
        Map<String, Object> body = new HashMap<>();
        body.put("policyId", policyId);
        body.put("policySubVersion", policySubVersion);
        body.put("title", title);
        body.put("content", content);
        body.put("applyYn", "0");
        body.put("postYn", "0");
        return body;
    }

    private static Map<String, Object> updatePrivacyPolicyBodyMap(Long policyId, Long policySubVersion,
            String policyNoticeDt, String policyStartDt,
            String title, String content, String applyYn, String postYn) {
        Map<String, Object> body = new HashMap<>();
        body.put("policyId", policyId);
        body.put("policySubVersion", policySubVersion);
        body.put("policyNoticeDt", "");
        body.put("policyStartDt", "");
        body.put("title", title);
        body.put("content", content);
        body.put("applyYn", applyYn);
        body.put("postYn", postYn);
        return body;
    }

    private void assertCreateResult(Long policyId, String policyVersion, Long policySubVersion,
            String title, String content) {
        PrivacyPolicyVO testId = privacyPolicyMapper.findPrivacyPolicyDetail(policyId, policySubVersion).get();
        assertThat(testId.getPolicyId()).isEqualTo(policyId);
        assertThat(testId.getPolicyVersion()).isEqualTo(policyVersion);
        assertThat(testId.getPolicySubVersion()).isEqualTo(policySubVersion);
        assertThat(testId.getTitle()).isEqualTo(title);
        assertThat(testId.getContent()).isEqualTo(content);
        assertThat(testId.getSysInsertDtm()).isNotNull();
    }


}
