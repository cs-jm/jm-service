package com.optile.cs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
public class JobManagementServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(JobManagementServiceApp.class, args);
    }

}
