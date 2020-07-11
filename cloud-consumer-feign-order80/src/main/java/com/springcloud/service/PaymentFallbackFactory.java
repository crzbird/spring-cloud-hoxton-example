package com.springcloud.service;

import com.springcloud.entities.CommonResult;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentFallbackFactory implements FallbackFactory<PaymentFeignService> {


    @Override
    public PaymentFeignService create(Throwable throwable) {
        return new PaymentFeignService() {
            @Override
            public CommonResult getPaymentById(Long id) {
                log.error("err:",throwable);
                return new  CommonResult(500,"调用失败：fallback");
            }

            @Override
            public String paymentFeignTimeout() {
                return null;
            }
        };
    }
}
