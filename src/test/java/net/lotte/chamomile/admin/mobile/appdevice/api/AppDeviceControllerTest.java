// package net.lotte.chamomile.admin.mobile.appdevice.api;
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
//
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
// /**
//  * <pre>
//  * </pre>
//  *
//  * @author HyunaKim
//  * @Modification <pre>
//  *     since          author              description
//  *  ===========    =============    ===========================
//  *  2024-06-12        HyunaKim          최초 생성
//  * </pre>
//  * Copyright (C) 2024 by HyunaKim., All right reserved.
//  * @since 2024-06-12
//  */
//
// @ActiveProfiles("mobile-admin")
// public class AppDeviceControllerTest extends WebApplicationTest {
//
//     private static final String BASE_URL = "/chmm/admin/mobile/app-device";
//
//     @Test
//     @DisplayName("앱기기 목록 호출 성공")
//     void getAppDeviceList() throws Exception {
//
//         // when
//         MockHttpServletRequestBuilder requestBuilder = getRequestBuilder(BASE_URL + "/list");
//
//         // then
//         mockMvc.perform(requestBuilder)
//                 .andDo(print())
//                 .andExpect(status().isOk())
//                 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                 .andExpect(jsonPath("$.code").value("200"))
//                 .andExpect(jsonPath("$.data.content[0].appId").value("chamomile"))
//                 .andExpect(jsonPath("$.data.content[0].deviceId").value("1234"));
//     }
//
//     @Test
//     @DisplayName("앱기기 수정")
//     void updateAppDevice() throws Exception {
//
//         // given
//         Map<String, Object> body = new HashMap<>();
//         body.put("appId", "1234");
//         body.put("deviceId", "5678");
//         body.put("deviceName", "1");
//         body.put("aprvlPhase", "APRVL");
//         body.put("lossYn", "0");
//         body.put("publicYn", "0");
//         body.put("useYn", "0");
//
//         String content = objectMapper.writeValueAsString(body);
//
//         // when
//         MockHttpServletRequestBuilder requestBuilder = postRequestBuilder(BASE_URL + "/update")
//                 .content(content);
//
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
//     @DisplayName("앱기기 삭제")
//     void deleteAppDevice() throws Exception {
//
//         // given
//         Map<String, Object> body = new HashMap<>();
//         body.put("appId", "chamomile");
//         body.put("deviceId", "1234");
//
//         List<Map<String, Object>> list = Arrays.asList(body);
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
//     }
//
// }
