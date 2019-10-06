package com.viki.cloud.consumer.exception;

import com.viki.cloud.consumer.bean.ErrorBodyBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionErrorHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorBodyBean> error(Exception e) {
        log.error("发生异常了", e);
        return new ResponseEntity<>(
                ErrorBodyBean.builder()
                        .body(e.getMessage())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
