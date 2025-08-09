package net.lotte.chamomile.admin.commoncode.api;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import net.lotte.chamomile.admin.WebApplicationTest;
import net.lotte.chamomile.admin.commoncode.api.dto.CategoryCommand;
import net.lotte.chamomile.admin.commoncode.domain.CommonCodeMapper;

class CommonCodeControllerTest extends WebApplicationTest {

    @Autowired
    CommonCodeMapper commonCodeMapper;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void insertTestData() throws Exception {
        Map<String, Object> body = createCategoryBodyMap("test", 0);
        String content = objectMapper.writeValueAsString(body);

        Map<String, Object> body2 = createCategoryBodyMap("test2", 0);
        String content2 = objectMapper.writeValueAsString(body2);

        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder("/chmm/common-code/category/create")
                .content(content);
        mockMvc.perform(requestBuilder);

        MockHttpServletRequestBuilder requestBuilder2 = postRequestBuilder("/chmm/common-code/category/create")
                .content(content2);
        mockMvc.perform(requestBuilder2);
    }

    @Test
    void findCategoryList() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder("/chmm/common-code/category/list");

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updateCategoryOrder() throws Exception {
        // given
        CategoryCommand test = new CategoryCommand("test", "", 1,"0", "");
        CategoryCommand test2 = new CategoryCommand("test2", "", 2,"0", "");
        List<CategoryCommand> list = Arrays.asList(test, test2);

        // when
        MockHttpServletRequestBuilder requestBuilder = postRequestBuilder("/chmm/common-code/category/order/update")
                .content(objectMapper.writeValueAsString(list))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());

    }

    public static Map<String, Object> createCategoryBodyMap(String categoryId, int orderNum) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("categoryId", categoryId);
        body.put("orderNum", orderNum);
        body.put("useYn", "0");
        return body;
    }
}
