package net.lotte.chamomile.admin.common.exception;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.NoSuchElementException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import net.lotte.chamomile.admin.common.exception.code.AdminExceptionCode;
import net.lotte.chamomile.core.exception.ChamomileException;
import net.lotte.chamomile.module.logging.LoggingUtils;
import net.lotte.chamomile.module.web.variable.ChamomileResponse;


/**
 * <pre>
 * 글로벌 전역 예외 처리.
 * </pre>
 *
 * @author MoonHKLee
 * @since 2023-09-15
 * @version 3.0
 * @Modification
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2023-09-15     MoonHKLee            최초 생성
 *
 * </pre>
 * Copyright (C) 2023 by LOTTE INNOVATE COMPANY., All right reserved.
 */
@RestControllerAdvice
@Slf4j
public class AdminExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ChamomileResponse<ChamomileException>> noSuchElementExceptionHandler(NoSuchElementException e) {
        logErrorStackTrace(e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ChamomileResponse<>(AdminExceptionCode.ServerError.getCode(), e.getMessage(), null));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ChamomileResponse<ChamomileException>> bindException(BindException e) {
        logErrorStackTrace(e);
        String defaultMessage = e.getBindingResult().getFieldError().getDefaultMessage();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ChamomileResponse<>(AdminExceptionCode.BadRequest.getCode(), defaultMessage, null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ChamomileResponse<ChamomileException>> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        logErrorStackTrace(e);
        String defaultMessage = e.getBindingResult().getFieldError().getDefaultMessage();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ChamomileResponse<>(AdminExceptionCode.BadRequest.getCode(), defaultMessage, null));
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ChamomileResponse<ChamomileException>> duplicateKeyException(DuplicateKeyException e) {
        logErrorStackTrace(e);
        String defaultMessage = e.getCause().getMessage().split(":")[0];
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ChamomileResponse<>(AdminExceptionCode.ServerError.getCode(), defaultMessage, null));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ChamomileResponse<ChamomileException>> runtimeExceptionHandler(RuntimeException e) {
        logErrorStackTrace(e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ChamomileResponse<>(AdminExceptionCode.ServerError.getCode(), e.getMessage(), null));
    }

    @ExceptionHandler(ChamomileException.class)
    public ResponseEntity<ChamomileResponse<ChamomileException>> runtimeExceptionHandler(ChamomileException e) {
        logErrorStackTrace(e);
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(new ChamomileResponse<>(e.getFrameworkCode().getCode(), e.getMessage(), null));
    }

    @ExceptionHandler(UndeclaredThrowableException.class)
    public ResponseEntity<ChamomileResponse<ChamomileException>> handleUndeclaredThrowableException(UndeclaredThrowableException e) {
        Throwable cause = e.getUndeclaredThrowable();
        if (cause instanceof MethodArgumentNotValidException) {
            return methodArgumentNotValidException((MethodArgumentNotValidException) cause);
        }
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ChamomileResponse<>(AdminExceptionCode.ServerError.getCode(), e.getMessage(), null));
    }
    
    private static void logErrorStackTrace(Exception e) {
        LoggingUtils.errorLogging(e);
        log.error("message: {}", e.getMessage(), e);
    }

}
