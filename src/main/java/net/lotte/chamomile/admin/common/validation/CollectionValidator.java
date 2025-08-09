package net.lotte.chamomile.admin.common.validation;

import java.lang.reflect.Method;
import java.util.Collection;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import lombok.RequiredArgsConstructor;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.MethodArgumentNotValidException;

import net.lotte.chamomile.core.exception.ChamomileException;
import net.lotte.chamomile.core.exception.ChamomileExceptionCode;


@Aspect
@Component
@RequiredArgsConstructor
public class CollectionValidator {

    private final SpringValidatorAdapter validator;

    @Pointcut("execution(* *(.., @org.springframework.validation.annotation.Validated (*), ..))")
    public void validatedPointcut() {
    }

    @Pointcut("execution(* *(.., @javax.validation.Valid (*), ..))")
    public void validPointcut() {
    }

    @Pointcut("validatedPointcut() || validPointcut()")
    public void collectionValidatorPointcut() {
    }

    @Before("collectionValidatorPointcut() && args(target)")
    public void collectionValidation(Object target) throws Throwable {

        if (target instanceof Collection) {
            Collection<?> collection = (Collection<?>) target;
            for (Object object : collection) {
                validate(object);
            }
        } else {
            validate(target);
        }
    }

    private void validate(Object target) throws MethodArgumentNotValidException, NoSuchMethodException {
        // 1. 검증 결과를 담는 BindingResult 객체 생성
        BindingResult errors = new BeanPropertyBindingResult(target, target.getClass().getName());

        // 2. 검증
        validator.validate(target, errors);

        // 3. 실패한 경우
        if (errors.hasErrors()) {
            // 4. MethodParameter 생성
            Method method = this.getClass().getDeclaredMethod("validate", Object.class);
            MethodParameter methodParameter = new MethodParameter(method, 0);

            // 5. MethodArgumentNotValidException 던지기
            throw new MethodArgumentNotValidException(methodParameter, errors);
        }
    }

}
