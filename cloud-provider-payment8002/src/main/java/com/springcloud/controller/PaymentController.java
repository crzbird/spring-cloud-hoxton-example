package com.springcloud.controller;

import com.springcloud.entities.CommonResult;
import com.springcloud.entities.Payment;
import com.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping(value = "/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Value(value = "${server.port}")
    private String serverPort;

    @PostMapping(value = "/create")
    public CommonResult create(@RequestBody Payment payment) {

        try {
            int result = paymentService.create(payment);
            log.info(String.valueOf(result));
            return result > 0 ? new CommonResult(200, "success,serverPort:" + this.serverPort, result) : new CommonResult(201, "fail", result);
        } catch (Exception e) {
            return new CommonResult(500, "fail,serverPort:" + this.serverPort);
        }
    }

    @GetMapping(value = "/get/{id}")
    public CommonResult getPaymentById(@PathVariable(value = "id") Long id) {
        return new CommonResult(200, "success,serverPort:" + this.serverPort, paymentService.getPaymentById(id));
    }

    @GetMapping(value = "/lb")
    public String getPaymentLB() {
        return serverPort;
    }

    @GetMapping(value = "/feign/timeout")
    public String paymentFeignTimeout() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e) {
        }
        return serverPort;
    }
}
