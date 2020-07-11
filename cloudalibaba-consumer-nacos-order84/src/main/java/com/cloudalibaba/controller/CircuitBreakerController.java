package com.cloudalibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.cloudalibaba.service.PaymentService;
import com.springcloud.entities.CommonResult;
import com.springcloud.entities.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {


    public static final String SERVICE_URL = "http://nacos-payment-provider";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PaymentService paymentService;

    @RequestMapping("/consumer/fallback/{id}")
    //@SentinelResource(value = "fallback")
    //@SentinelResource(value = "fallback", fallback = "handlerFallback")//fallback只管业务异常
    @SentinelResource(value = "fallback", blockHandler = "blockHandler")//blockHandler只管sentinel控制台异常配置
    //@SentinelResource(value = "fallback", blockHandler = "blockHandler", fallback = "handlerFallback",exceptionsToIgnore = IllegalArgumentException.class)//blockHandler和fallback可同时使用，不冲突，exceptionsToIgnore忽略此异常，不会触发降级
    public CommonResult<Payment> fallback(@PathVariable Long id) {
        CommonResult<Payment> result = restTemplate.getForObject(SERVICE_URL + "/paymentSQL/" + id, CommonResult.class, id);

        if (id == 4) {
            throw new IllegalArgumentException("IllegalArgument ,非法参数异常...");
        } else if (result.getData() == null) {
            throw new NullPointerException("NullPointerException,该ID没有对应记录，空指针异常");
        }

        return result;
    }

    public CommonResult handlerFallback(Long id, Throwable e) {
        return new CommonResult(444, "handlerFallback 兜底" + e.getMessage(), new Payment(id, ""));
    }

    public CommonResult blockHandler(Long id, BlockException e) {
        return new CommonResult(444, "blockHandler 兜底" + e.getMessage(), new Payment(id, ""));
    }

    //=========openFeign
    @GetMapping(value = "/consumer/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id) {
        return paymentService.paymentSQL(id);
    }

}
