package org.adweb.learningcommunityback.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.adweb.learningcommunityback.entity.response.SimpleUnSuccessfulResponseBody;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * @author shuang.kou
 * AccessDeineHandler 用来解决认证过的用户访问需要权限才能访问的资源时的异常
 */
public class JWTAccessDeniedHandler implements AccessDeniedHandler {
    /**
     * 当用户尝试访问需要权限才能的REST资源而权限不足的时候，
     * 将调用此方法发送403响应以及错误信息
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        SimpleUnSuccessfulResponseBody body = new SimpleUnSuccessfulResponseBody();
        body.setErrorCode("FORBIDDEN");
        body.setTimestamp(new Timestamp(System.currentTimeMillis()));
        body.setMessage("很抱歉，你的权限不足");

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        try {
            response.getOutputStream().write(new ObjectMapper().writeValueAsBytes(body));
        } catch (IOException ignored) {
        }
    }
}