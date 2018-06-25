package com.justosbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OsboWebappApplication {

    public static void main(String[] args) {
        String userName = System.getenv("USERNAME");
        SpringApplication springApplication = new SpringApplication(OsboWebappApplication.class);
        springApplication.setAdditionalProfiles(userName == null ? "UNKNOWN_USERNAME" : userName);
        springApplication.run(args);
    }
}