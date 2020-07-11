package com.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "CLOUD-PROVIDER-HYSTRIX-PAYMENT"/*,fallback = PaymentFallbackService.class*/)
public interface PaymentHystrixService {

    @GetMapping(value = "/payment/hystrix/ok/{id}")
    String paymentInfo_OK(@PathVariable(value = "id") Integer id);

    @GetMapping(value = "/payment/hystrix/timeout/{id}")
    String paymentInfo_Timeout(@PathVariable(value = "id") Integer id);

}
