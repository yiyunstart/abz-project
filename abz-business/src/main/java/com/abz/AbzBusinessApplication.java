package com.abz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AbzBusinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(AbzBusinessApplication.class, args);
    }

}
