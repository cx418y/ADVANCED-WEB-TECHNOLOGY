package org.adweb.learningcommunityback.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.adweb.learningcommunityback.entity.response.SimpleUnSuccessfulResponseBody;
import org.adweb.learningcommunityback.security.JwtTokenUtils;
import org.adweb.learningcommunityback.security.constants.SecurityConstants;
import org.adweb.learningcommunityback.security.entity.JwtUser;
import org.adweb.learningcommunityback.security.entity.LoginUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shuang.kou
 * 如果用户名和密码正确，那么过滤器将创建一个JWT Token 并在HTTP Response 的header中返回它，格式：token: "Bearer +具体token值"
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        // 设置登录请求的 URL
        super.setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // 从输入流中获取到登录的信息
            LoginUser loginRequest = objectMapper.readValue(request.getInputStream(), LoginUser.class);
            // 这部分和attemptAuthentication方法中的源码是一样的，
            // 只不过由于这个方法源码的是把用户名和密码这些参数的名字是死的，所以我们重写了一下
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), loginRequest.getPassword());
            return authenticationManager.authenticate(authRequest);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 如果验证成功，就生成token并返回
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authentication) {

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        class LoginResponseBody {
            private String message;
            private Timestamp timestamp;
            private String jwt;
        }

        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        List<String> roles = jwtUser.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        // 创建 Token
        String token = JwtTokenUtils.createToken(jwtUser.getUsername(), roles);

        //设置响应类型
        response.setContentType("application/json");

        //写入登陆成功的响应体
        try {
            LoginResponseBody body = new LoginResponseBody();
            body.jwt = token;
            body.message = "登陆成功";
            body.timestamp = new Timestamp(System.currentTimeMillis());
            response.getOutputStream().write(objectMapper.writeValueAsBytes(body));
        } catch (IOException ignored) {
        }

    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException {
        SimpleUnSuccessfulResponseBody rspBody = new SimpleUnSuccessfulResponseBody();
        rspBody.setErrorCode("LOGIN_FAILED");
        rspBody.setMessage("登陆失败，请检查用户名和密码");
        rspBody.setTimestamp(new Timestamp(System.currentTimeMillis()));


        //设置响应类型和状态
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        //设置响应体！
        try {
            response.getOutputStream().write(new ObjectMapper().writeValueAsBytes(rspBody));
        } catch (IOException ignored) {
        }

    }
}
