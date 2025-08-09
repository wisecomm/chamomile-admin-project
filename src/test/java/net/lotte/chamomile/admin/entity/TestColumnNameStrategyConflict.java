package net.lotte.chamomile.admin.entity;

import java.util.NoSuchElementException;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import net.lotte.chamomile.admin.WebApplicationTest;

import static org.assertj.core.api.Assertions.*;


public class TestColumnNameStrategyConflict extends WebApplicationTest {

    @Autowired
    private ChmmUserRepository chmmUserRepository;

    @Test
    @DisplayName("캐모마일 유저 상속 하여 유저 객체 만들때 확인하는 오류")
    void test() {
        ChmmUser user = ChmmUser.builder()
                .userId("test")
                .password("1111")
                .build();

        chmmUserRepository.saveAndFlush(user);
        ChmmUser test = chmmUserRepository.findById("test").orElseThrow(NoSuchElementException::new);
        assertThat(test.getUserId()).isEqualTo("test");
    }
}
