package org.adweb.learningcommunityback.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.adweb.learningcommunityback.entity.response.SimpleUnSuccessfulResponseBody;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * @author shuang.kou
 * AuthenticationEntryPoint 用来解决匿名用户访问需要权限才能访问的资源时的异常
 */
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /**
     * 当用户尝试访问需要权限才能的REST资源而不提供Token或者Token过期时，
     * 将调用此方法发送401响应以及错误信息
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException){

        SimpleUnSuccessfulResponseBody body = new SimpleUnSuccessfulResponseBody();
        body.setErrorCode("UNAUTHORIZED");
        body.setTimestamp(new Timestamp(System.currentTimeMillis()));
        body.setMessage("你未登陆或登陆已失效，请重新登陆后再试试");

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try {
            response.getOutputStream().write(new ObjectMapper().writeValueAsBytes(body));
        } catch (IOException ignored) {
        }

    }
}
