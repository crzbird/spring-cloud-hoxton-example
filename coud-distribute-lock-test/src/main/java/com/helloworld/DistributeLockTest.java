package com.helloworld;

import com.helloworld.test.DistributeLockResource;
import com.helloworld.test.DistributeLockTestService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class DistributeLockTest {
    public static void main(String[] args) {

        ConfigurableApplicationContext ctx = SpringApplication.run(DistributeLockTest.class, args);
        StringRedisTemplate bean = ctx.getBean(StringRedisTemplate.class);
        DistributeLockTestService service = ctx.getBean(DistributeLockTestService.class);
        CountDownLatch countDownLatch = new CountDownLatch(100);

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                service.test();
                countDownLatch.countDown();
            }).start();
        }
        try {
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(bean.boundValueOps("testLockD").get());
        System.out.println(DistributeLockResource.count);

    }

}
