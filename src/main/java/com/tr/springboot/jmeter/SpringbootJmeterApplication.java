package com.tr.springboot.jmeter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SpringbootJmeterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootJmeterApplication.class, args);
    }

}
