package net.lotte.chamomile.admin.common.validation;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Collections;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CollectionValidatorTest {

    @Autowired
    private TestService testService;

    TestCollection invalid = TestCollection.builder()
            .value("value")
            .array(null)
            .list(new ArrayList<String>() {{
                add("");
            }})
            .build();

    TestCollection valid = TestCollection.validCollection();

    @Test
    @DisplayName("유효하지 않는 Collection이 포함된 validation")
    public void testValidAnnotationWithInvalidCollection() {
        Assertions.assertThatThrownBy(() -> testService.methodWithValidAnnotation(invalid))
                .isInstanceOf(UndeclaredThrowableException.class);
    }

    @Test
    @DisplayName("유효한 Collection이 포함된 validation")
    public void testValidAnnotationWithValidCollection() {
        testService.methodWithValidAnnotation(valid);
    }

    @Test
    @DisplayName("유효하지 않은 값이 포함된 Collection class")
    public void testValidAnnotationWithInvalidObject() {
        Assertions.assertThatThrownBy(() ->
                        testService.methodWithValidAnnotation(invalid))
                .isInstanceOf(UndeclaredThrowableException.class);
    }

    @Test
    @DisplayName("유효한 값이 포함된 Collection class")
    public void testValidAnnotationWithValidObject() {
        testService.methodWithValidatedAnnotation(Collections.singletonList(valid));
    }


    @Test
    @DisplayName("유효하지 않은 Collection의 exception 확인을 위한 validation")
    public void testExceptionWithInvalidCollection() {
        Assertions.assertThatThrownBy(() ->
                        testService.methodWithValidatedAnnotation(Collections.singletonList(invalid)))
                .isInstanceOf(UndeclaredThrowableException.class);
    }

}
