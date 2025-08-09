// package net.lotte.chamomile.admin.mobile.appfile.api;
//
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.util.Arrays;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
//
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpMethod;
// import org.springframework.http.MediaType;
// import org.springframework.mock.web.MockMultipartFile;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//
// import net.lotte.chamomile.admin.WebApplicationTest;
// import net.lotte.chamomile.module.mobile.admin.dto.AppFile;
// import net.lotte.chamomile.module.mobile.admin.dto.AppFile.AppFileIds;
// import net.lotte.chamomile.module.security.jwt.provider.JwtAuthInterface;
// import net.lotte.chamomile.module.security.jwt.vo.JwtRequest;
//
// @ActiveProfiles("mobile-admin")
// public class AppFileControllerTest extends WebApplicationTest {
//
//     private static final String BASE_URL = "/chmm/admin/mobile/app-file";
//
//     private String filePath;
//     private MockMultipartFile uploadFile;
//     private MockMultipartFile uploadLogoFile;
//     private MockMultipartFile uploadPlistFile;
//     private Map<String, String> token;
//
//     @Autowired
//     private JwtAuthInterface jwtAuthInterface;
//
//     @BeforeEach
//     void setData() throws Exception {
//
//         // 임시 파일 생성
//         Path tempDir = Files.createTempDirectory("test-upload-dir");
//         Path tempFile = tempDir.resolve("test-file.txt");
//
//         //Mock파일생성
//         uploadFile = new MockMultipartFile(
//                 "uploadFile", //name
//                 tempFile.getFileName().toString(), // 원본 파일 이름
//                 "text/plain", // 파일 타입
//                 "Chamomile App File".getBytes()
//         );
//         uploadLogoFile = new MockMultipartFile(
//                 "uploadLogoFile", //name
//                 tempFile.getFileName().toString(), // 원본 파일 이름
//                 "text/plain", // 파일 타입
//                 "Chamomile Logo File".getBytes()
//         );
//         uploadPlistFile = new MockMultipartFile(
//                 "uploadPlistFile", //name
//                 tempFile.getFileName().toString(), // 원본 파일 이름
//                 "text/plain", // 파일 타입
//                 "Chamomile Plist File".getBytes()
//         );
//
//         token = jwtAuthInterface.authenticateToken(new JwtRequest("chmm23","1111"));
//     }
//
//     @Test
//     @DisplayName("앱 파일 목록 호출 성공")
//     void getAppFileList() throws Exception {
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
//                 .andExpect(jsonPath("$.data.content[0].appFileIds.appId").value("chamomileappaos"));
//     }
//
//     @Test
//     @DisplayName("앱 파일 목록 호출 성공 - 검색 조건 포함")
//     void getAppFilesByCriteria() throws Exception {
//
//         // when
//         MockHttpServletRequestBuilder requestBuilder = getRequestBuilder(BASE_URL + "/list")
//                 .param("searchAppId", "chamomileappios")
//                 .param("searchOsType", "ios");
//
//         // then
//         mockMvc.perform(requestBuilder)
//                 .andDo(print())
//                 .andExpect(status().isOk())
//                 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                 .andExpect(jsonPath("$.code").value("200"))
//                 .andExpect(jsonPath("$.data.content[0].appFileIds.appId").value("chamomileappios"));
//     }
//
//     @Test
//     @DisplayName("앱 파일 저장 성공(전체 입력값)")
//     void createAppFileByAllData() throws Exception {
//
//         AppFile appFile = new AppFile();
//         appFile.setAppFileIds(new AppFileIds("chamomile", "1.0", "ios"));
//         appFile.setOsVerMin("12");
//         appFile.setOsVerMax("15");
//         appFile.setFileDesc("");
//         appFile.setAppFileName("ChamomileMobile.ipa");
//         appFile.setPlistFileName("ChamomileMobile.plist");
//         appFile.setUploadPlistFileCode("");
//         appFile.setUploadLogoFileCode("");
//         appFile.setUploadAppFileCode("");
//         appFile.setDeployPhase("FINAL");
//         appFile.setDownloadCnt(0);
//         appFile.setRequiredUpdates("1");
//
//         String jsonRequest = objectMapper.writeValueAsString(appFile);
//         MockMultipartFile jsonPart = new MockMultipartFile(
//                 "request", "", MediaType.APPLICATION_JSON_VALUE, jsonRequest.getBytes());
//
//         //When & Then
//         mockMvc.perform(
//                 multipart(HttpMethod.POST,BASE_URL + "/create")
//                         .file(uploadFile).file(uploadLogoFile).file(uploadPlistFile)
//                         .file(jsonPart)
//                         .header(HttpHeaders.AUTHORIZATION, "Bearer " +  token.get("accessToken"))
//                         .contentType(MediaType.MULTIPART_FORM_DATA)
//         ).andExpect(status().isOk());
//     }
//
//     @Test
//     @DisplayName("앱 파일 저장 성공(필수 입력값)")
//     void createAppFileByEssencialData() throws Exception {
//
//         AppFile appFile = new AppFile();
//         appFile.setAppFileIds(new AppFileIds("chamomile", "1.0", "ios"));
//         appFile.setOsVerMin("12");
//         appFile.setOsVerMax("15");
//         appFile.setAppFileName("ChamomileMobile.ipa");
//         appFile.setUploadAppFileCode("");
//         appFile.setUploadLogoFileCode("");
//         appFile.setDeployPhase("FINAL");
//
//         String jsonRequest = objectMapper.writeValueAsString(appFile);
//         MockMultipartFile jsonPart = new MockMultipartFile(
//                 "request", "", MediaType.APPLICATION_JSON_VALUE, jsonRequest.getBytes());
//
//         //When & Then
//         mockMvc.perform(
//                 multipart(HttpMethod.POST,BASE_URL + "/create")
//                         .file(uploadFile).file(uploadLogoFile)
//                         .file(jsonPart)
//                         .header(HttpHeaders.AUTHORIZATION, "Bearer " +  token.get("accessToken"))
//                         .contentType(MediaType.MULTIPART_FORM_DATA)
//         ).andExpect(status().isOk());
//     }
//
//     @Test
//     @DisplayName("앱 파일 저장 실패(필수 입력값 누락)")
//     void createAppFileFailed() throws Exception {
//
//         AppFile appFile = new AppFile();
//         appFile.setAppFileIds(new AppFileIds("chamomile", "1.0", "ios"));
//         appFile.setAppFileName("ChamomileMobile.ipa");
//         appFile.setUploadAppFileCode("");
//         appFile.setUploadLogoFileCode("");
//         appFile.setDeployPhase("FINAL");
//
//         String jsonRequest = objectMapper.writeValueAsString(appFile);
//         MockMultipartFile jsonPart = new MockMultipartFile(
//                 "request", "", MediaType.APPLICATION_JSON_VALUE, jsonRequest.getBytes());
//
//         //When & Then
//         mockMvc.perform(
//                 multipart(HttpMethod.POST,BASE_URL + "/create")
//                         .file(uploadFile).file(uploadLogoFile)
//                         .file(jsonPart)
//                         .header(HttpHeaders.AUTHORIZATION, "Bearer " +  token.get("accessToken"))
//                         .contentType(MediaType.MULTIPART_FORM_DATA)
//         ).andExpect(jsonPath("$.code").value("400")).andExpect(jsonPath("$.message").value("must not be null"));
//     }
//
//     @Test
//     @DisplayName("앱 파일 저장 실패(버전에 . 포함)")
//     void createAppFileIncludePointInVersion() throws Exception {
//
//         AppFile appFile = new AppFile();
//         appFile.setAppFileIds(new AppFileIds("chamomile", "1.0", "ios"));
//         appFile.setOsVerMin("12.0");
//         appFile.setOsVerMax("15.1.1");
//         appFile.setAppFileName("test.txt");
//         appFile.setUploadAppFileCode("");
//         appFile.setUploadLogoFileCode("");
//         appFile.setDeployPhase("FINAL");
//
//         String jsonRequest = objectMapper.writeValueAsString(appFile);
//         MockMultipartFile jsonPart = new MockMultipartFile(
//                 "request", "", MediaType.APPLICATION_JSON_VALUE, jsonRequest.getBytes());
//
//         //When & Then
//         mockMvc.perform(
//                 multipart(HttpMethod.POST,BASE_URL + "/create")
//                         .file(uploadFile).file(uploadLogoFile)
//                         .file(jsonPart)
//                         .header(HttpHeaders.AUTHORIZATION, "Bearer " +  token.get("accessToken"))
//                         .contentType(MediaType.MULTIPART_FORM_DATA)
//         ).andExpect(jsonPath("$.code").value("400")).andExpect(jsonPath("$.message").value("숫자만 입력 가능합니다."));
//     }
//
//     @Test
//     @DisplayName("앱 파일 수정")
//     void updateAppFile() throws Exception {
//
//         AppFile appFile = new AppFile();
//         appFile.setAppFileIds(new AppFileIds("chamomile", "2.0", "aos"));
//         appFile.setOsVerMin("12");
//         appFile.setOsVerMax("18");
//         appFile.setAppFileName("test.txt");
//         appFile.setUploadAppFileCode("da39a80477b347e6a5a712970c3f3e30");
//         appFile.setUploadLogoFileCode("");
//         appFile.setDeployPhase("FINAL");
//         appFile.setRequiredUpdates("1");
//
//         String jsonRequest = objectMapper.writeValueAsString(appFile);
//         MockMultipartFile jsonPart = new MockMultipartFile(
//                 "request", "", MediaType.APPLICATION_JSON_VALUE, jsonRequest.getBytes());
//
//         //When & Then
//         mockMvc.perform(
//                 multipart(HttpMethod.POST,BASE_URL + "/update")
//                         .file(uploadFile)
//                         .file(jsonPart)
//                         .header(HttpHeaders.AUTHORIZATION, "Bearer " +  token.get("accessToken"))
//                         .contentType(MediaType.MULTIPART_FORM_DATA)
//         ).andExpect(status().isOk());
//     }
//
//     @Test
//     @DisplayName("앱 파일 삭제")
//     void deleteApp() throws Exception {
//         // given
//         AppFile appFile = new AppFile();
//         appFile.setAppFileIds(new AppFileIds("chamomile", "1.0", "aos"));
//
//         Map<String, Object> body = new HashMap<>();
//         Map<String, String> ids = new HashMap<>();
//         ids.put("appId", "chamomile");
//         ids.put("appVer", "1.0");
//         ids.put("osType", "aos");
//         body.put("appFileIds", ids);
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
