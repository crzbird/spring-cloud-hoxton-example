package com.springcloud.controller;

import com.springcloud.entities.CommonResult;
import com.springcloud.entities.Payment;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/consumer")
@Slf4j
public class OrderController {

    //public static final String PAYMENT_URL = "http://localhost:8001/payment/";
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE/";

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping(value = "/payment/create")
    public CommonResult<Payment> create(@RequestBody Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "payment/create", payment, CommonResult.class);
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable(value = "id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "payment/get/" + id, CommonResult.class);
    }

    @GetMapping(value = "/payment/getForEntity/{id}")
    public CommonResult<Payment> getPayment2(@PathVariable(value = "id") long id) {
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
        if (entity.getStatusCode().is2xxSuccessful()) {
            log.info(String.valueOf(entity.getStatusCodeValue()));
            return entity.getBody();
        }
        return new CommonResult<>(entity.getStatusCodeValue(), entity.getStatusCode().getReasonPhrase());

    }

    @GetMapping(value = "/payment/lb")
    public CommonResult<String> paymentLB() {
        String lb = restTemplate.getForObject(PAYMENT_URL + "/payment/lb", String.class);
        return new CommonResult<>(200, "suc", lb);
    }

    @GetMapping(value = "/payment/zipkin")
    public String paymentZipkin() {
        return restTemplate.getForObject(PAYMENT_URL + "/payment/zipkin", String.class);
    }

}
