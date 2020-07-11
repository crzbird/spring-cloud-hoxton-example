package com.springcloud.controller;

import com.springcloud.entities.CommonResult;
import com.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Slf4j
@RequestMapping(value = "/payment")
public class PaymentController {


    @Value(value = "${server.port}")
    private String serverPort;

    @GetMapping(value = "/consul")
    public String paymentConsul() {
        return "springcloud with consult:" + this.serverPort;
    }
}
