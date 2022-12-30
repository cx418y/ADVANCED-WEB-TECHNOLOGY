package org.adweb.learningcommunityback.security.constants;

/**
 * @author shuang.kou
 * @description Spring Security相关配置常量
 */
public final class SecurityConstants {

    public static final String AUTH_LOGIN_URL = "/auth/login";

    /**
     * 角色的key
     **/
    public static final String ROLE_CLAIMS = "role";

    /**
     * token7天有效
     */
    public static final long EXPIRATION = 60 * 60 * 24 * 7L;

    /**
     * JWT签名密钥硬编码到应用程序代码中，应该存放在环境变量或.properties文件中。
     */
    public static final String JWT_SECRET_KEY = "C*F-JaNdRgUkXn2r5u8x/A?D(G+KbPeShVmYq3s6v9y$B&E)H@McQfTjWnZr4u7w";

    // JWT token defaults
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_TYPE = "JWT";

    // System WHITELIST
    public static final String[] SYSTEM_WHITELIST = {
            "/auth/login",
            "/users/sign-up"
    };

    private SecurityConstants() {
    }

}