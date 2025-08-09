package net.lotte.chamomile.admin.message.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import net.lotte.chamomile.admin.WebApplicationTest;
import net.lotte.chamomile.admin.message.api.dto.MessageQuery;
import net.lotte.chamomile.module.database.mybatis.batch.BatchRequest;

@Slf4j
class MessageMapperTest extends WebApplicationTest {
    @Autowired ObjectMapper objectMapper;
    @Autowired MessageMapper messageMapper;

    @Test
    void findMessageListTest() {
        List<MessageVO> findList = messageMapper.findMessageList(new MessageQuery("searchCode", "common.message.applyAll", null, null, null, null, null)
                , PageRequest.of(0, 10)).getContent();
        MessageVO findOne = findList.get(0);
        //assertThat(findOne.getMessage()).isEqualTo("apply all");

        List<MessageVO> findList2 = messageMapper.findMessageList(new MessageQuery("searchCode", "common.message.applyAll", null, null, null, null, null)
                , PageRequest.of(0, 10)).getContent();
        assertThat(findList2.size()).isEqualTo(2);
    }

    @Test
    void findMessageDetailTest() {
        List<MessageVO> findList = messageMapper.findMessageDetail(new MessageQuery(null, null, null, null, "grid.column.sucRate", null, null));
        assertThat(findList.size()).isEqualTo(2);
    }

    @Test
    void existsByKeyTest() {
        List<MessageVO> input = new ArrayList<>();
        input.add(new MessageVO("grid.column.sucRate", "ko", "KR", ""));
        input.add(new MessageVO("grid.column.sucRate", "en", "US", ""));
        input.add(new MessageVO("grid.column.sucRate", "zn", "CN", ""));
        Optional<Boolean> exists = messageMapper.existsByKey(input);
        Assertions.assertTrue(exists.isPresent());
    }

    @Test
    void deleteMessageTest() {
        // given
        List<MessageVO> input = new ArrayList<>();
        input.add(new MessageVO("grid.column.sucRate", "ko", "KR", ""));
        input.add(new MessageVO("grid.column.sucRate", "en", "US", ""));

        // when
        messageMapper.deleteMessage(input);

        // then
        List<MessageVO> findList = messageMapper.findMessageList(new MessageQuery("searchCode", "grid.column.sucRate", null, null, null, null, null)
                , PageRequest.of(0, 10)).getContent();
        assertThat(findList.size()).isEqualTo(0);
    }

    @Test
    void insertLanguageTest() {
        LanguageVO input = new LanguageVO("en", "US", "");
        int count = messageMapper.languageCnt(input);
        assertThat(count).isEqualTo(1);
        LanguageVO input2 = new LanguageVO("ru", "RU", "러시아");
        messageMapper.insertLanguage(input2);
        int count2 = messageMapper.languageCnt(input2);
        assertThat(count2).isEqualTo(1);
    }

    @Test
    void findLanguageListTest() {
        List<LanguageVO> findList = messageMapper.findLanguageList();
        assertThat(findList.size()).isEqualTo(2);
    }

    @Test
    void deleteLanguageTest() {
        messageMapper.deleteLanguage(Collections.singletonList(new LanguageVO("en", "US", null)), new BatchRequest(1000));
        List<LanguageVO> findList = messageMapper.findLanguageList();
        assertThat(findList.size()).isEqualTo(1);
    }
}
