package net.lotte.chamomile.admin.common.validation;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestService {

    public void methodWithValidAnnotation(@Valid Object target) {
        System.out.println("this is methodWithValidAnnotation to check validation");
    }

    public void methodWithValidatedAnnotation(@Validated Object target) {
        System.out.println("this is methodWithValidatedAnnotation to check validation");
    }
}
