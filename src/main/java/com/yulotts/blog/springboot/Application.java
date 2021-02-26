package com.yulotts.blog.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableJpaAuditing //JPA Auditing 활성화
@SpringBootApplication
public class Application {

    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            +"classpath:application.yml,"
            +"classpath:application-oauth.yml,"
            +"classpath:aws.yml";

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
//        new SpringApplicationBuilder(Application.class)
//                .properties(APPLICATION_LOCATIONS)
//                .run(args);
    }
}
