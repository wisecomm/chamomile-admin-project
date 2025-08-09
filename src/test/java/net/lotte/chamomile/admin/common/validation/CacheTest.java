package net.lotte.chamomile.admin.common.validation;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hazelcast.core.HazelcastInstance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import net.lotte.chamomile.admin.WebApplicationTest;
import net.lotte.chamomile.admin.commoncode.service.CommonCodeService;
import net.lotte.chamomile.admin.message.api.dto.MessageQuery;
import net.lotte.chamomile.admin.message.service.MessageService;

public class CacheTest extends WebApplicationTest {

    @Autowired
    private CommonCodeService commonCodeService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private HazelcastInstance hazelcastInstance;

    @Test
    @DisplayName("캐시가 전부 삭제되는지 검증한다.")
    void testCacheClearAll() throws Exception {
        //given
        MessageQuery messageQuery = MessageQuery.builder()
                .searchCode("confirm.httpmethod.alert")
                .build();

        commonCodeService.getCommonCodeAllList();
        messageService.getMessageList(messageQuery, PageRequest.of(0,10));

        //when
        getRequestBuilder("/chmm/cache/clear");

        //then
        assertThat(hazelcastInstance.getMap("commonCode").isEmpty());
        assertThat(hazelcastInstance.getMap("message").isEmpty());
    }
}
