package com.springcloud.controller;

import com.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping(value = "/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Value(value = "${server.port}")

    @GetMapping(value = "/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable(value = "id") Integer id) throws Exception {
        TimeUnit.SECONDS.sleep(5);
        return paymentService.paymentInfo_OK(id);
    }

    @GetMapping(value = "/hystrix/timeout/{id}")
    public String paymentInfo_Timeout(@PathVariable(value = "id") Integer id) {
        log.info(id.toString());
        return paymentService.paymentInfo_Timeout(id);
    }

    @GetMapping(value = "/hystrix/circuitBreaker/{id}")
    public String paymentCircuitBreaker(@PathVariable(value = "id") Integer id) {
        return paymentService.paymentCircuitBreaker(id);
    }

}
