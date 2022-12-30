package org.adweb.learningcommunityback.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * 对业务中出现的异常的包装类
 */
@Getter
@Setter
@AllArgsConstructor
public class AdWebBaseException extends RuntimeException {
    /**
     * 异常信息
     */
    private String message;

    /**
     * 异常码
     */
    private String errorCode;

    /**
     * 时间戳
     */
    private Timestamp timestamp;

    /**
     * 造成异常的原因，这里传入的类型会在全局异常处理器中，做为判断HTTP响应码的依据
     * 比如
     * IllegalArgumentException -> 400
     * ResourceNotFoundException -> 404
     */
    private Class<? extends Exception> type;


    public AdWebBaseException(Class<? extends Exception> type, String errorCode, String message) {
        this.type = type;
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.errorCode = errorCode;
        this.message = message;
    }
}
