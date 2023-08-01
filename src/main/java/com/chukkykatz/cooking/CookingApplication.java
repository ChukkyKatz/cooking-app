package com.chukkykatz.cooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.chukkykatz.cooking.repository")
@EntityScan(basePackages = "com.chukkykatz.cooking.domain")
public class CookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(CookingApplication.class, args);
    }
}
