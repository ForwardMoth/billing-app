package com.nexign.cdr_generator_app_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CallDataRecordApplication {

    public static void main(String[] args) {
        SpringApplication.run(CallDataRecordApplication.class, args);
    }

}
