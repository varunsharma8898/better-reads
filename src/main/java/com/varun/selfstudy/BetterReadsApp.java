package com.varun.selfstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableConfigurationProperties(DataStaxAstraProperties.class)
public class BetterReadsApp {

    public static void main(String[] args) {
        SpringApplication.run(BetterReadsApp.class, args);
    }

}
