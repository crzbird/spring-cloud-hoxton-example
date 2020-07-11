package com.springcloud;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class DubboConsumerMain10000 {

    public static void main(String[] args) {
        SpringApplication.run(DubboConsumerMain10000.class, args);
    }

}
