package net.lotte.chamomile.admin.commoncode.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import net.lotte.chamomile.admin.WebApplicationTest;
import net.lotte.chamomile.admin.commoncode.api.dto.CategoryQuery;
import net.lotte.chamomile.admin.commoncode.api.dto.CodeItemQuery;
import net.lotte.chamomile.admin.commoncode.api.dto.CodeQuery;
import net.lotte.chamomile.module.database.mybatis.batch.BatchRequest;

class CommonCodeMapperTest extends WebApplicationTest {

    @Autowired CommonCodeMapper commonCodeMapper;
    @Autowired ObjectMapper objectMapper;
    @BeforeEach
    void init() throws Exception {
        Map<String, Object> body1 = new HashMap<>();
        initTestDataBase(body1, "test");
        String content1 = objectMapper.writeValueAsString(body1);

        Map<String, Object> body2 = new HashMap<>();
        initTestDataBase(body2, "test");
        body2.put("codeId", "test");
        String content2 = objectMapper.writeValueAsString(body2);

        Map<String, Object> body3 = new HashMap<>();
        initTestDataBase(body3, "test");
        body3.put("codeId", "test");
        body3.put("codeItemId", "test");
        String content3 = objectMapper.writeValueAsString(body3);

        Map<String, Object> body4 = new HashMap<>();
        initTestDataBase(body4, "test2");
        String content4 = objectMapper.writeValueAsString(body4);

        Map<String, Object> body5 = new HashMap<>();
        initTestDataBase(body5, "test");
        body5.put("codeId", "test2");
        String content5 = objectMapper.writeValueAsString(body5);

        Map<String, Object> body6 = new HashMap<>();
        initTestDataBase(body6, "test");
        body6.put("codeId", "test");
        body6.put("codeItemId", "test2");
        String content6 = objectMapper.writeValueAsString(body6);


        MockHttpServletRequestBuilder requestBuilder1 = postRequestBuilder("/chmm/common-code/category/create")
                .content(content1);
        mockMvc.perform(requestBuilder1);

        MockHttpServletRequestBuilder requestBuilder2 = postRequestBuilder("/chmm/common-code/code/create")
                .content(content2);
        mockMvc.perform(requestBuilder2);

        MockHttpServletRequestBuilder requestBuilder3 = postRequestBuilder("/chmm/common-code/code-item/create")
                .content(content3);
        mockMvc.perform(requestBuilder3);

        MockHttpServletRequestBuilder requestBuilder4 = postRequestBuilder("/chmm/common-code/category/create")
                .content(content4);
        mockMvc.perform(requestBuilder4);

        MockHttpServletRequestBuilder requestBuilder5 = postRequestBuilder("/chmm/common-code/code/create")
                .content(content5);
        mockMvc.perform(requestBuilder5);

        MockHttpServletRequestBuilder requestBuilder6 = postRequestBuilder("/chmm/common-code/code-item/create")
                .content(content6);
        mockMvc.perform(requestBuilder6);
    }

    private static void initTestDataBase(Map<String, Object> map, String categoryId) {
        map.put("categoryId", categoryId);
        map.put("orderNum", 0);
        map.put("useYn", "0");
    }

    @Test
    void updateCategoryOrderTest() {
        // given
        CategoryVO test = new CategoryVO("test", "", 1,"0", "");
        CategoryVO test2 = new CategoryVO("test2", "", 2,"0", "");
        List<CategoryVO> list = Arrays.asList(test, test2);

        // when
        commonCodeMapper.updateCategoryOrder(list, new BatchRequest(1000));
        List<CategoryVO> updatedList = commonCodeMapper.findCategoryList(new CategoryQuery("test"), PageRequest.of(0,10)).getContent();
        CategoryVO updated1 = updatedList.get(0);
        CategoryVO updated2 = updatedList.get(1);

        // then
        assertThat(updated1.getOrderNum()).isEqualTo(1);
        assertThat(updated2.getOrderNum()).isEqualTo(2);
    }

    @Test
    void updateCodeOrderTest() {
        // given
        CodeVO test = new CodeVO("test", "test", null, 1, "0", null);
        CodeVO test2 = new CodeVO("test", "test2", null, 100, "0", null);
        List<CodeVO> list = Arrays.asList(test, test2);

        // when
        commonCodeMapper.updateCodeOrder(list, new BatchRequest(1000));
        List<CodeVO> updateList = commonCodeMapper.findCodeList(new CodeQuery("test", "test"), PageRequest.of(0,10)).getContent();
        CodeVO update1 = updateList.get(0);
        CodeVO update2 = updateList.get(1);

        // then
        assertThat(update1.getOrderNum()).isEqualTo(1);
        assertThat(update2.getOrderNum()).isEqualTo(100);
    }

    @Test
    void updateCodeItemOrderTest() {
        // given
        CodeItemVO test = new CodeItemVO("test", "test", "test", null, 1, "0", null);
        CodeItemVO test2 = new CodeItemVO("test", "test", "test2", null, 3, "0", null);

        // when
        commonCodeMapper.updateCodeItemOrder(Arrays.asList(test, test2), new BatchRequest(1000));
        List<CodeItemVO> updateList = commonCodeMapper.findCodeItemList(new CodeItemQuery("test", "test", "test"), PageRequest.of(0,10)).getContent();
        CodeItemVO update1 = updateList.get(0);
        CodeItemVO update2 = updateList.get(1);

        //then
        assertThat(update1.getOrderNum()).isEqualTo(1);
        assertThat(update2.getOrderNum()).isEqualTo(3);
    }

    @Test
    void deleteCategoryTest() {
        // given
        List<CategoryVO> deleteList = new ArrayList<>();
        CategoryVO deleteOne = new CategoryVO("test", "", 0, "0", "");
        deleteList.add(deleteOne);

        // when
        commonCodeMapper.deleteCategoryWithChild(deleteList, new BatchRequest(1000));

        // then
        List<CategoryVO> findCategories = commonCodeMapper.findCategoryList(new CategoryQuery("test"), PageRequest.of(0,10)).getContent();
        assertThat(findCategories.stream().filter(c -> c.getCategoryId().equals("test")).count()).isEqualTo(0);

        List<CodeVO> findCodes = commonCodeMapper.findCodeList(new CodeQuery("test", "test"), PageRequest.of(0,10)).getContent();
        assertThat(findCodes.size()).isEqualTo(0);

        List<CodeItemVO> findCodeItems = commonCodeMapper.findCodeItemList(new CodeItemQuery("test", "test", "test"), PageRequest.of(0, 10)).getContent();
        assertThat(findCodeItems.size()).isEqualTo(0);
    }

    @Test
    void deleteCodeTest(){
        // given
        List<CodeVO> deleteList = new ArrayList<>();
        CodeVO deleteOne = new CodeVO("test", "test", null, null, null, null);
        deleteList.add(deleteOne);

        // when
        commonCodeMapper.deleteCodeWithChild(deleteList, new BatchRequest(1000));

        // then
        List<CodeVO> findCodes = commonCodeMapper.findCodeList(new CodeQuery("test", "test"), PageRequest.of(0,10)).getContent();
        assertThat(findCodes.stream().filter(c -> c.getCodeId().equals("test")).count()).isEqualTo(0);
        
        List<CodeItemVO> findCodeItems = commonCodeMapper.findCodeItemList(new CodeItemQuery("test", "test", "test"), PageRequest.of(0,10)).getContent();
        assertThat(findCodeItems.size()).isEqualTo(0);
    }

    @Test
    void deleteCodeItemTest() {
        CodeItemVO delete1 = new CodeItemVO("test", "test", "test");
        CodeItemVO delete2 = new CodeItemVO("test", "test", "test2");
        commonCodeMapper.deleteCodeItem(Arrays.asList(delete1, delete2), new BatchRequest(1000));
        List<CodeItemVO> findList = commonCodeMapper.findCodeItemList(new CodeItemQuery("test", "test", "test"), PageRequest.of(0,10)).getContent();
        assertThat(findList.stream().filter(c -> c.getCodeItemId().contains("test")).count()).isEqualTo(0);

    }

    @Test
    void updateCategoryTest() {
        CategoryVO updateOne = new CategoryVO("test", "testDesc", 4, "1", "testValue");
        commonCodeMapper.updateCategory(updateOne);
        List<CategoryVO> findList = commonCodeMapper.findCategoryList(new CategoryQuery("test"), PageRequest.of(0,10)).getContent();
        CategoryVO updatedOne = findList.get(0);
        assertThat(updatedOne).usingRecursiveComparison()
                .ignoringFields("sysUpdateDtm", "sysUpdateUserId", "sysInsertDtm", "sysInsertUserId")
                .isEqualTo(updateOne);
    }

    @Test
    void updateCodeTest() {
        CodeVO updateOne = new CodeVO("test", "test","testDesc", 0, "1", "testValue");
        commonCodeMapper.updateCode(updateOne);
        List<CodeVO> findList = commonCodeMapper.findCodeList(new CodeQuery("test", "test"), PageRequest.of(0,10)).getContent();
        CodeVO updatedOne = findList.get(0);
        assertThat(updatedOne).usingRecursiveComparison()
                .ignoringFields("sysUpdateDtm", "sysUpdateUserId", "sysInsertDtm", "sysInsertUserId")
                .isEqualTo(updateOne);
    }

    @Test
    void updateCodeItemTest() {
        CodeItemVO updateOne = new CodeItemVO("test", "test", "test", "testDesc", 0, "1", "testValue");
        commonCodeMapper.updateCodeItem(updateOne);
        List<CodeItemVO> findList = commonCodeMapper.findCodeItemList(new CodeItemQuery("test", "test", "test"), PageRequest.of(0,10)).getContent();
        CodeItemVO updatedOne = findList.get(0);
        assertThat(updatedOne).usingRecursiveComparison()
                .ignoringFields("sysUpdateDtm", "sysUpdateUserId", "sysInsertDtm", "sysInsertUserId")
                .isEqualTo(updateOne);
    }
}
