package com.sliit.lanka_mart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LankaMartApplication {

    public static void main(String[] args) {
        SpringApplication.run(LankaMartApplication.class, args);
    }
}