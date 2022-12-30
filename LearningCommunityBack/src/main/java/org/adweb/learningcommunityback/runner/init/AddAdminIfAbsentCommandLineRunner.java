package org.adweb.learningcommunityback.runner.init;

import lombok.extern.slf4j.Slf4j;
import org.adweb.learningcommunityback.entity.db.User;
import org.adweb.learningcommunityback.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class AddAdminIfAbsentCommandLineRunner implements CommandLineRunner {

    @Resource
    private UserRepository userRepository;

    /**
     * 如果数据库中还没有管理员，就在数据库中加入管理员
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("admin") == null) {
            log.info("发现数据库中还没有管理员，正在添加管理员用户......");
            userRepository.save(new User(
                    "admin",
                    "系统管理员",
                    null,
                    DigestUtils.md5DigestAsHex("admin".getBytes(StandardCharsets.UTF_8)).toLowerCase(),
                    User.ROLE_ADMIN
            ));
            log.info("管理员用户添加成功！用户名为：admin，密码为：admin");
        }

    }
}
