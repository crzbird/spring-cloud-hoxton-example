package com.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
//rabbitmq自动配置会连localhost:5672 屏蔽之
@SpringBootApplication(exclude = {RabbitAutoConfiguration.class})
public class StreamMQMain8801 {

    public static void main(String[] args) {
        SpringApplication.run(StreamMQMain8801.class, args);
    }

}
