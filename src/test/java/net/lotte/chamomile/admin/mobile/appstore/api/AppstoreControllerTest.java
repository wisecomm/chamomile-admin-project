// package net.lotte.chamomile.admin.mobile.appstore.api;
//
// import java.io.File;
//
// import javax.persistence.EntityManager;
// import javax.persistence.PersistenceContext;
// import javax.persistence.TypedQuery;
//
// import org.junit.jupiter.api.Disabled;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
//
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.MediaType;
// import org.springframework.mock.web.MockHttpServletResponse;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.test.web.servlet.MvcResult;
// import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//
// import net.lotte.chamomile.admin.WebApplicationTest;
// import net.lotte.chamomile.module.mobile.admin.entity.AppFileInfo;
//
// import static org.hamcrest.MatcherAssert.assertThat;
// import static org.hamcrest.Matchers.containsString;
//
// import static org.junit.jupiter.api.Assertions.*;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
// @ActiveProfiles("mobile-admin")
// public class AppstoreControllerTest extends WebApplicationTest {
//
//     private static final String BASE_URL = "/appstore";
//
//     @PersistenceContext
//     private EntityManager em;
//
//     @Test
//     @DisplayName("앱 스토어 목록 호출 성공")
//     void getAppstoreList() throws Exception {
//
//         // when
//         MockHttpServletRequestBuilder requestBuilder = getRequestBuilder(BASE_URL + "/app/list")
//                 .param("searchOsType", "aos")
//                 .param("searchOsVerInfo", "28.0.0");
//
//         // then
//         mockMvc.perform(requestBuilder)
//                 .andDo(print())
//                 .andExpect(status().isOk())
//                 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                 .andExpect(jsonPath("$.code").value("200"))
//                 .andExpect(jsonPath("$.data.content[0].appId").value("chamomileappaos"))
//                 .andExpect(jsonPath("$.data.content[0].appVer").value("1.2"));
//
//         // when
//         requestBuilder = getRequestBuilder(BASE_URL + "/app/list")
//                 .param("searchOsType", "ios")
//                 .param("searchOsVerInfo", "10.3");
//
//         // then
//         mockMvc.perform(requestBuilder)
//                 .andDo(print())
//                 .andExpect(status().isOk())
//                 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                 .andExpect(jsonPath("$.code").value("200"))
//                 .andExpect(jsonPath("$.data.content[0].appId").value("chamomileappios"))
//                 .andExpect(jsonPath("$.data.content[0].appVer").value("1.0"));
//     }
//
//     @Test
//     @Disabled
//     void download() throws Exception {
//         //given
//         String fileCode = "d520df36a75f46bc81cfca724905e6aa";
//
//         //when
//         MockHttpServletRequestBuilder builder = get(BASE_URL + "/file/" + fileCode)
//                 //.header("user-agent", "Mozilla/5.0 (iPad; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1")
//                 .header("user-agent", "Mozilla/5.0 (Linux; Android 28; Pixel 4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Mobile Safari/537.36")
//                 .contentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//         // then
//         doDownload(builder, fileCode);
//     }
//
//     private void doDownload(MockHttpServletRequestBuilder builder, String fileCode) throws Exception {
//         // then
//         MvcResult mvcResult = mockMvc.perform(builder)
//                 .andExpect(status().isOk())
//                 .andReturn();
//         MockHttpServletResponse response = mvcResult.getResponse();
//
//         int contentLength = response.getContentLength();
//         String contentType = response.getContentType();
//         String contentDisposition = response.getHeader(HttpHeaders.CONTENT_DISPOSITION);
//
//         File expectedFile = new File("./src/test/resources/d520df36a75f46bc81cfca724905e6aa");
//
//         assertEquals(contentLength, Math.toIntExact(expectedFile.length()));
//         assertThat(contentDisposition, containsString("filename=\"chamomile_dev_debug_1.0_1.apk\""));
//
//         String jpql = "select a from AppFileInfo a where a.uploadAppFileCode = :fileCode";
//         TypedQuery<AppFileInfo> query = em.createQuery(jpql, AppFileInfo.class);
//         query.setParameter("fileCode", fileCode);
//         AppFileInfo file = query.getResultList().get(0);
//
//         assertEquals(file.getDownloadCnt(), 1);
//     }
// }
