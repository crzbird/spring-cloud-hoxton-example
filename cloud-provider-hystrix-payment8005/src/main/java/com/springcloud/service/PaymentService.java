package com.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class PaymentService {

    public String paymentInfo_OK(Integer id) {
        return "线程池" + Thread.currentThread().getName() + "paymentInfo_OK:" + id;
    }

    //HystrixProperty此处设置超时，
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public String paymentInfo_Timeout(Integer id) {
        try {
            int timeout = 2;
            TimeUnit.SECONDS.sleep(timeout);
            //int i=1/0;
            return "线程池" + Thread.currentThread().getName() + "paymentInfo_Timeout:" + id + "\t 耗时:" + timeout;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "time out 异常";
    }

    public String paymentInfo_TimeoutHandler(Integer id) {
        return "线程池" + Thread.currentThread().getName() + "paymentInfo_TimeoutHandler:" + id + "\t 超时FallBack";
    }

    //熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),//是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),//请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),//时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),//失败率达到多少后断路
    })//根据此配置：在10秒内请求10次，异常率达到60，则开启熔断10秒，在下一个10窗口期放行一次请求，如果成功则关闭断路器，失败则继续断路
    public String paymentCircuitBreaker(Integer id) {
        log.info(Thread.currentThread().getName()+ "paymentCircuitBreaker\t"+id);
        if (id < 0) {
            throw new RuntimeException("*****id 不能负数");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "\t 调用成功，流水号：" + serialNumber;

    }

    public String paymentCircuitBreaker_fallback(Integer id) {
        return "id 不能负数 fallback";
    }

}
