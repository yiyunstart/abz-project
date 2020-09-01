package com.abz;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@Log
public class AbzAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(AbzAccountApplication.class, args);
    }


}
