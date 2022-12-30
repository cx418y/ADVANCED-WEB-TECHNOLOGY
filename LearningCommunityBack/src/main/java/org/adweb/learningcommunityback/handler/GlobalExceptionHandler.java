package org.adweb.learningcommunityback.handler;


import org.adweb.learningcommunityback.entity.response.SimpleUnSuccessfulResponseBody;
import org.adweb.learningcommunityback.exception.AdWebBaseException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Timestamp;

@RestControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AdWebBaseException.class)// 拦截所有异常
    public ResponseEntity<SimpleUnSuccessfulResponseBody> globalExceptionHandler(AdWebBaseException e) {
        SimpleUnSuccessfulResponseBody body = new SimpleUnSuccessfulResponseBody();
        body.setTimestamp(new Timestamp(System.currentTimeMillis()));
        body.setMessage(e.getMessage());
        body.setErrorCode(e.getErrorCode());

        if (e.getType().equals(IllegalArgumentException.class)) {
            //参数错误，400异常
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
        } else {
            //其他情况，给500异常
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
        }
    }
}
