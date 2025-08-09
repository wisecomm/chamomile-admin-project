// package net.lotte.chamomile.admin.mobile.app.api;
//
// import java.util.Arrays;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
//
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
//
// import org.springframework.http.MediaType;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//
// import net.lotte.chamomile.admin.WebApplicationTest;
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
// @ActiveProfiles("mobile-admin")
// public class AppControllerTest extends WebApplicationTest {
//
//     private static final String BASE_URL = "/chmm/admin/mobile/app";
//
//     @Test
//     @DisplayName("앱 목록 호출 성공")
//     void getAppList() throws Exception {
//
//         // when
//         MockHttpServletRequestBuilder requestBuilder = getRequestBuilder(BASE_URL + "/list")
//                 .param("searchAppName", "캐모마일(AOS)");
//
//         // then
//         mockMvc.perform(requestBuilder)
//                 .andDo(print())
//                 .andExpect(status().isOk())
//                 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                 .andExpect(jsonPath("$.code").value("200"))
//                 .andExpect(jsonPath("$.data.content[0].appId").value("chamomileappaos"));
//     }
//
//     @Test
//     @DisplayName("앱 등록 성공(전체 입력값)")
//     void createAppByAllData() throws Exception {
//         // given
//         Map<String, String> body = new HashMap<>();
//         body.put("appId", "cashbee");
//         body.put("appName", "캐시비");
//         body.put("appDesc", "교통카드 캐시비 앱");
//         body.put("deviceAutoAprvYn", "1");
//         body.put("appDeployType", "B2C");
//         body.put("useYn", "1");
//
//         String content = objectMapper.writeValueAsString(body);
//
//         // when
//         MockHttpServletRequestBuilder requestBuilder = postRequestBuilder(BASE_URL + "/create")
//                 .content(content);
//
//         // then
//         mockMvc.perform(requestBuilder)
//                 .andDo(print())
//                 .andExpect(status().isOk())
//                 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                 .andExpect(jsonPath("$.code").value("200"));
//     }
//
//     @Test
//     @DisplayName("앱 등록 성공(필수 입력값)")
//     void createAppByEssentialData() throws Exception {
//         // given
//         Map<String, String> body = new HashMap<>();
//         body.put("appId", "cashbee");
//         body.put("appName", "캐시비");
//         body.put("appDeployType", "B2C");
//         body.put("useYn", "1");
//
//         String content = objectMapper.writeValueAsString(body);
//
//         // when
//         MockHttpServletRequestBuilder requestBuilder = postRequestBuilder(BASE_URL + "/create")
//                 .content(content);
//
//         // then
//         mockMvc.perform(requestBuilder)
//                 .andDo(print())
//                 .andExpect(status().isOk())
//                 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                 .andExpect(jsonPath("$.code").value("200"));
//     }
//
//     @Test
//     @DisplayName("앱 등록 실패(필수 입력값 누락)")
//     void createAppFailed() throws Exception {
//         // given
//         Map<String, String> body = new HashMap<>();
//         body.put("appId", "cashbee");
//         body.put("appName", "캐시비");
//         body.put("useYn", "1");
//
//         String content = objectMapper.writeValueAsString(body);
//
//         // when
//         MockHttpServletRequestBuilder requestBuilder = postRequestBuilder(BASE_URL + "/create")
//                 .content(content);
//
//         // then
//         mockMvc.perform(requestBuilder)
//                 .andDo(print())
//                 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                 .andExpect(jsonPath("$.code").value("400"))
//                 .andExpect(jsonPath("$.message").value("must not be null"));
//     }
//
//     @Test
//     @DisplayName("앱 등록 실패(앱아이디 한글 포함)")
//     void createAppFailedInvalidValue() throws Exception {
//         // given
//         Map<String, String> body = new HashMap<>();
//         body.put("appId", "캐시비");
//         body.put("appName", "캐시비");
//         body.put("appDeployType", "B2C");
//         body.put("useYn", "1");
//
//         String content = objectMapper.writeValueAsString(body);
//
//         // when
//         MockHttpServletRequestBuilder requestBuilder = postRequestBuilder(BASE_URL + "/create")
//                 .content(content);
//
//         // then
//         mockMvc.perform(requestBuilder)
//                 .andDo(print())
//                 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                 .andExpect(jsonPath("$.code").value("400"))
//                 .andExpect(jsonPath("$.message").value("알파벳 소문자(a~z), 숫자(0~9)만 입력 가능합니다."));
//     }
//
//     @Test
//     @DisplayName("앱 등록 실패(앱아이디 공백 포함)")
//     void createAppFailedIncludeBlank() throws Exception {
//         // given
//         Map<String, String> body = new HashMap<>();
//         body.put("appId", "cashbee 3");
//         body.put("appName", "캐시비");
//         body.put("appDeployType", "B2C");
//         body.put("useYn", "1");
//
//         String content = objectMapper.writeValueAsString(body);
//
//         // when
//         MockHttpServletRequestBuilder requestBuilder = postRequestBuilder(BASE_URL + "/create")
//                 .content(content);
//
//         // then
//         mockMvc.perform(requestBuilder)
//                 .andDo(print())
//                 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                 .andExpect(jsonPath("$.code").value("400"))
//                 .andExpect(jsonPath("$.message").value("알파벳 소문자(a~z), 숫자(0~9)만 입력 가능합니다."));
//     }
//
//     @Test
//     @DisplayName("앱 수정 성공")
//     void updateApp() throws Exception {
//
//         // given
//         Map<String, String> body = new HashMap<>();
//         body.put("appId", "chamomileappaos");
//         body.put("appName", "캐시비");
//         body.put("appDesc", "교통카드 캐시비 앱");
//         body.put("deviceAutoAprvYn", "1");
//         body.put("appDeployType", "B2C");
//         body.put("useYn", "0");
//
//         String content = objectMapper.writeValueAsString(body);
//
//         // when
//         MockHttpServletRequestBuilder requestBuilder = postRequestBuilder(BASE_URL + "/update")
//                 .content(content);
//
//         // then
//         mockMvc.perform(requestBuilder)
//                 .andDo(print())
//                 .andExpect(status().isOk())
//                 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                 .andExpect(jsonPath("$.code").value("200"));
//     }
//
//     @Test
//     @DisplayName("앱 삭제")
//     void deleteApp() throws Exception {
//         // given
//         Map<String, String> body = new HashMap<>();
//         body.put("appId", "chamomileappaos");
//
//         List<Map<String, String>> list = Arrays.asList(body);
//
//         String content = objectMapper.writeValueAsString(list);
//
//         // when
//         MockHttpServletRequestBuilder requestBuilder = postRequestBuilder(BASE_URL + "/delete")
//                 .content(content);
//
//         // then
//         mockMvc.perform(requestBuilder)
//                 .andDo(print())
//                 .andExpect(status().isOk())
//                 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                 .andExpect(jsonPath("$.code").value("200"));
//
//     }
// }
