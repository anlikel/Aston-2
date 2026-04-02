package ru.astondevs.input;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class InputApplication {

    public static void main(String[] args) {
        SpringApplication.run(InputApplication.class, args);
    }

}
