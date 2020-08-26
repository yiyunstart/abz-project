package com.abz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients

public class AbzOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(AbzOrderApplication.class, args);
    }

}
