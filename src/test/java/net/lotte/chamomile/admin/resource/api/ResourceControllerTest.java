package net.lotte.chamomile.admin.resource.api;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import net.lotte.chamomile.admin.WebApplicationTest;
import net.lotte.chamomile.admin.resource.domain.ResourceMapper;
import net.lotte.chamomile.admin.resource.domain.ResourceVO;

class ResourceControllerTest extends WebApplicationTest {

    @Autowired
    private ResourceMapper resourceMapper;

    @Test
    @DisplayName("리소스 목록 호출 성공")
    void getResourceList() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder("/chmm/resource/list");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.content[0].resourceId").value("RESOURCE_ADMIN_ID"));
    }

    @Test
    @DisplayName("리소스 호출 성공")
    void getResource() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder("/chmm/resource/detail")
                .param("resourceId", "RESOURCE_ADMIN_ID");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"));
    }

    @Test
    @DisplayName("리소스 ID 체크 성공")
    void getResourceCheck() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder("/chmm/resource/check")
                .param("resourceId", "RESOURCE_ADMIN_ID");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data").value("false"));
    }

    @Test
    @DisplayName("그룹 ID 체크 실패 (리소스 없음)")
    void getResourceCheckFailureNoResourceId() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder("/chmm/resource/check")
                .param("resourceId", "resourceasdfasd");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data").value("true"));
    }

    @Test
    @DisplayName("리소스 목록 호출 성공 (페이징)")
    void getResourcePaging() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder("/chmm/resource/list")
                .param("page", "0")
                .param("size", "1")
                .param("sort", "resourceId,desc");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.content[0].resourceId").value("리소스"));

    }

    @Test
    @DisplayName("리소스 목록 호출 성공 (정렬)")
    void getResourcePagingOrder() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder("/chmm/resource/list")
                .param("page", "0")
                .param("size", "1")
                .param("sort", "resourceId,asc");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.content[0].resourceId").value("RESOURCE_ADMIN_ID"));
    }

    @Test
    @DisplayName("리소스 목록 호출 성공 (검색_리소스ID)")
    void getResourceSearchResourceId() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder("/chmm/resource/list")
                .param("searchResourceId", "RESOURCE_ADMIN_ID");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.content[0].resourceId").value("RESOURCE_ADMIN_ID"));
    }

    @Test
    @DisplayName("리소스 목록 호출 성공 (검색_리소스_이름)")
    void getResourceSearchResourceName() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder("/chmm/resource/list")
                .param("searchResourceName", "어드민 리소스");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.content[0].resourceName").value("어드민 리소스"));
    }

    @Test
    @DisplayName("리소스 목록 호출 성공 (검색_사용_여부)")
    void getResourceSearchUseYn() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder("/chmm/resource/list")
                .param("searchUseYn", "0");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.content", hasSize(0)));
    }

    @Test
    @DisplayName("리소스 호출 성공(한글 쿼리)")
    void getResourceKorea() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder("/chmm/resource/detail")
                .param("resourceId", "리소스");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"));
    }

    @Test
    @DisplayName("리소스 호출 실패(데이터 없음)")
    void getResourceFailure() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder("/chmm/resource/detail")
                .param("resourceId", "RESOURCE_NO_DATA");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("500"));
    }

    @ParameterizedTest
    @ValueSource(strings = { "0", "1" })
    @DisplayName("리소스 생성 성공")
    void createResource(String useYn) throws Exception {
        // given
        Map<String, Object> body = createResourceBodyMap("testId","/testUri","","testName","testDesc",1234,useYn);
        String content = objectMapper.writeValueAsString(body);

        // when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder("/chmm/resource/create")
                .content(content);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"));

        assertCreateResult("testId","/testUri","","testName","testDesc",1234);
    }

    @Test
    @DisplayName("리소스 생성 실패(동일키 존재)")
    void createResourceFailureSameKey() throws Exception {
        // given
        createResource("1");
        Map<String, Object> body = createResourceBodyMap("testId","/testUri","","testName","testDesc",1234,"1");


        String content = objectMapper.writeValueAsString(body);

        // when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder("/chmm/resource/create")
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
    @ValueSource(ints = { -1, 123456 })
    @DisplayName("리소스 생성 실패(order 오류)")
    void createResourceFailureOrder(int order) throws Exception {
        // given
        Map<String, Object> body = createResourceBodyMap("testId","/testUri","","testName","testDesc",order,"1");
        String content = objectMapper.writeValueAsString(body);

        // when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder("/chmm/resource/create")
                .content(content);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("리소스 순서는 1~5 자리의 양수만 입력 가능합니다."));
    }

    @ParameterizedTest
    @ValueSource(strings = { "true", "false" })
    @DisplayName("리소스 생성 실패(useYn 오류)")
    void createResourceFailureUseYn(String useYn) throws Exception {
        // given
        Map<String, Object> body = createResourceBodyMap("testId","/testUri","","testName","testDesc",1234,useYn);
        String content = objectMapper.writeValueAsString(body);

        // when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder("/chmm/resource/create")
                .content(content);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("참/거짓은 0/1로 구분 합니다."));
    }


    @Test
    @DisplayName("리소스 수정 성공")
    void updateResource() throws Exception {
        //given
        createResource("1");
        Map<String, Object> body = createResourceBodyMap("testId","/testUri---","---","testName---","testDesc---",1234,"1");
        String content = objectMapper.writeValueAsString(body);

        // when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder("/chmm/resource/update")
                .content(content);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"));

        assertUpdateResult("testId","/testUri---","---","testName---","testDesc---",1234);

    }

    @ParameterizedTest
    @ValueSource(ints = { -1, 123456 })
    @DisplayName("리소스 수정 실패(order 오류)")
    void updateResourceFailureOrder(int order) throws Exception {
        //given
        createResource("1");
        Map<String, Object> body = createResourceBodyMap("testId","/testUri---","---","testName---","testDesc---",order,"1");
        String content = objectMapper.writeValueAsString(body);

        // when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder("/chmm/resource/update")
                .content(content);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("리소스 순서는 1~5 자리의 양수만 입력 가능합니다."));
    }

    @ParameterizedTest
    @ValueSource(strings = { "true", "false" })
    @DisplayName("리소스 수정 실패(useYn 오류)")
    void updateResourceFailureUseYn(String useYn) throws Exception {
        // given
        createResource("1");
        Map<String, Object> body = createResourceBodyMap("testId","/testUri---","---","testName---","testDesc---",1234,useYn);
        String content = objectMapper.writeValueAsString(body);

        // when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder("/chmm/resource/update")
                .content(content);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("참/거짓은 0/1로 구분 합니다."));
    }

    @Test
    @DisplayName("리소스 삭제 성공")
    void deleteResource() throws Exception {
        // given
        createResource("1");
        List<ResourceVO> body = Arrays.asList(new ResourceVO("delId"),new ResourceVO("testId"));
        String content = objectMapper.writeValueAsString(body);

        // when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder("/chmm/resource/delete")
                .content(content);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"));
    }

    @Test
    @DisplayName("리소스 삭제 실패(값 null)")
    void deleteResourceFailureNoKey() throws Exception {
        // given
        Map<String, String> body = new HashMap<>();
        body.put("delId",null);
        String content = objectMapper.writeValueAsString(body);

        // when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder("/chmm/resource/delete")
                .content(content);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("500"));
    }

    @Test
    @DisplayName("리소스 대용량 엑셀 다운로드 성공")
    void getBigSizeResourceListByExcel() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder("/chmm/resource/testBatchExcel");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/vnd.ms-excel"));
    }


    private static Map<String, Object> createResourceBodyMap(String resourceId, String resourceUri, String resourceHttpMethod,
            String resourceName, String resourceDesc, Integer securityOrder, String useYn) {
        Map<String, Object> body = new HashMap<>();
        body.put("resourceId", resourceId);
        body.put("resourceUri", resourceUri);
        body.put("resourceHttpMethod", resourceHttpMethod);
        body.put("resourceName", resourceName);
        body.put("resourceDesc", resourceDesc);
        body.put("securityOrder", securityOrder);
        body.put("useYn", useYn);
        return body;
    }


    private void assertCreateResult(String resourceId, String resourceUri, String resourceHttpMethod,
            String resourceName, String resourceDesc, int securityOrder) {
        ResourceVO testId = resourceMapper.findResource(resourceId).get();
        assertThat(testId.getResourceId()).isEqualTo(resourceId);
        assertThat(testId.getResourceUri()).isEqualTo(resourceUri);
        assertThat(testId.getResourceHttpMethod()).isEqualTo(resourceHttpMethod);
        assertThat(testId.getResourceName()).isEqualTo(resourceName);
        assertThat(testId.getResourceDesc()).isEqualTo(resourceDesc);
        assertThat(testId.getSecurityOrder()).isEqualTo(securityOrder);
        assertThat(testId.getSysInsertUserId()).isEqualTo("chmm23");
        assertThat(testId.getSysInsertDtm()).isNotNull();
    }

    private void assertUpdateResult(String resourceId, String resourceUri, String resourceHttpMethod,
            String resourceName, String resourceDesc, int securityOrder) {
        ResourceVO testId = resourceMapper.findResource(resourceId).get();
        assertThat(testId.getResourceId()).isEqualTo(resourceId);
        assertThat(testId.getResourceUri()).isEqualTo(resourceUri);
        assertThat(testId.getResourceHttpMethod()).isEqualTo(resourceHttpMethod);
        assertThat(testId.getResourceName()).isEqualTo(resourceName);
        assertThat(testId.getResourceDesc()).isEqualTo(resourceDesc);
        assertThat(testId.getSecurityOrder()).isEqualTo(securityOrder);
        assertThat(testId.getSysInsertUserId()).isEqualTo("chmm23");
        assertThat(testId.getSysInsertDtm()).isNotNull();
        assertThat(testId.getSysUpdateUserId()).isEqualTo("chmm23");
        assertThat(testId.getSysUpdateDtm()).isNotNull();
    }
}
