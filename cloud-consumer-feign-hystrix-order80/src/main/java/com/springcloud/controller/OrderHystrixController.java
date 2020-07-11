package com.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.springcloud.service.PaymentHystrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/consumer")
//@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
public class OrderHystrixController {

    @Autowired
    private PaymentHystrixService paymentHystrixService;

    @GetMapping(value = "/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable(value = "id") Integer id) {
        return paymentHystrixService.paymentInfo_OK(id);
    }


    @GetMapping(value = "/payment/hystrix/timeout/{id}")
    @HystrixCommand(fallbackMethod = "paymentTimeOutFallbackMethod", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")
    })//HystrixCommand针对调用方的方法fallback ，在feign接口上的fallbackfactory针对调用服务提供方的方法fallback
    public String paymentInfo_Timeout(@PathVariable(value = "id") Integer id) {
        //int i = 1 / 0;
        return paymentHystrixService.paymentInfo_Timeout(id);

    }

    public String paymentTimeOutFallbackMethod(Integer id) {
        return "consumer 80 fallback";
    }

    public String payment_Global_FallbackMethod() {
        return "consumer 80 global fallback";
    }

    @GetMapping(value = "/test")
    public void test(@RequestBody Test test, HttpServletRequest request){
        StringBuffer requestURL = request.getRequestURL();
        String requestURI = request.getRequestURI();
        System.out.println(test);
    }

}
