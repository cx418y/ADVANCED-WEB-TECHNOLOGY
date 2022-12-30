package org.adweb.learningcommunityback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class LearningCommunityBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearningCommunityBackApplication.class, args);

    }


}
