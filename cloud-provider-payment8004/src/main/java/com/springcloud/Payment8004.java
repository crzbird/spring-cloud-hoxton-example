package com.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
//@EnableEurekaClient eureka注册中心，换成zk
@EnableDiscoveryClient
public class Payment8004 {

    public static void main(String[] args) {
        SpringApplication.run(Payment8004.class, args);
    }

}
