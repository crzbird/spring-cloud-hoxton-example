package com.springcloud.service;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService {

    @Override
    public String paymentInfo_OK(Integer id) {
        return "paymentInfo_OK PaymentFallbackService";
    }

    @Override
    public String paymentInfo_Timeout(Integer id) {
        return "paymentInfo_Timeout PaymentFallbackService";
    }


}
