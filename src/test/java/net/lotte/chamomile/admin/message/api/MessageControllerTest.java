package net.lotte.chamomile.admin.message.api;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import net.lotte.chamomile.admin.WebApplicationTest;
import net.lotte.chamomile.admin.message.api.dto.MessageQuery;
import net.lotte.chamomile.admin.message.domain.MessageVO;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class MessageControllerTest extends WebApplicationTest {
    private static final String BASE_URL = "/chmm/message";

    @Test
    @DisplayName("자바 컬렉션(List) valid 성공")
    void successCreateMessage() throws Exception {
        //given
        List<MessageVO> list = new ArrayList<>();
        list.add(MessageVO.builder()
                .sysInsertDtm(LocalDateTime.now())
                .sysUpdateDtm(LocalDateTime.now())
                .code("test.list.valid")
                .languageCode("ko")
                .countryCode("KR")
                .message("testForValidation")
                .build()
        );

        list.add(MessageVO.builder()
                .sysInsertDtm(LocalDateTime.now())
                .sysUpdateDtm(LocalDateTime.now())
                .code("test.list.valid2")
                .languageCode("ko")
                .countryCode("KR")
                .message("testForValidation")
                .build()
        );

        String content = objectMapper.writeValueAsString(list);

        //when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder(BASE_URL + "/create")
                .content(content);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"));
    }

    @Test
    @DisplayName("자바 컬렉션(List) valid 실패 - NotEmpty")
    void failCreateMessage() throws Exception {
        //given
        List<MessageVO> list = new ArrayList<>();
        list.add(MessageVO.builder()
                .sysInsertDtm(LocalDateTime.now())
                .sysUpdateDtm(LocalDateTime.now())
                .code("")
                .languageCode("ko")
                .countryCode("KR")
                .message("testForValidation")
                .build()
        );

        list.add(MessageVO.builder()
                .sysInsertDtm(LocalDateTime.now())
                .sysUpdateDtm(LocalDateTime.now())
                .code("")
                .languageCode("ko")
                .countryCode("KR")
                .message("testForValidation")
                .build()
        );

        String content = objectMapper.writeValueAsString(list);

        //when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder(BASE_URL + "/create")
                .content(content);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("must not be empty"));
    }

    @Test
    @DisplayName("모든 메시지 리스트 가져오기")
    void getMessageAllListByAllLanguage() throws Exception {
        //given
        MessageQuery messageQuery = MessageQuery.builder()
                .searchCode("")
                .searchCountryCode("")
                .searchLanguageCode("")
                .build();

        //when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder(BASE_URL + "/list/all");

        //then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(56));
    }

    @Test
    @DisplayName("언어별 모든 메시지 리스트 가져오기_언어 설정 안해둔 경우")
    void getMessageAllListByLanguage() throws Exception {
        //given
        MessageQuery messageQuery = MessageQuery.builder()
                .searchCode("")
                .searchCountryCode("KR")
                .searchLanguageCode("ko")
                .build();
        //when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder(BASE_URL + "/list/language/all");

        //then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data").isArray()) // data가 배열인지 확인
                .andExpect(jsonPath("$.data.length()").value(28)); // data 배열의 크기가 0인지 확인
    }

    @Test
    @DisplayName("언어별 모든 메시지 리스트 가져오기_언어 설정 안해둔 경우")
    void getMessageAllListWithoutLanguage() throws Exception {
        //given
        MessageQuery messageQuery = MessageQuery.builder()
                .searchCode("")
                .searchCountryCode("")
                .searchLanguageCode("")
                .build();
        //when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder(BASE_URL + "/list/language/all");

        //then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data").isArray()) // data가 배열인지 확인
                .andExpect(jsonPath("$.data.length()").value(28)); // data 배열의 크기가 0인지 확인
    }

}
