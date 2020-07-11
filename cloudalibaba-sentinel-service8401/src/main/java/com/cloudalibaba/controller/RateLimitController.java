package com.cloudalibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.cloudalibaba.myHandler.CustomerBlockHandler;
import com.springcloud.entities.CommonResult;
import com.springcloud.entities.Payment;
import com.sun.deploy.security.BlockedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimitController {

    @GetMapping(value = "/byResource")
    @SentinelResource(value = "byResource",blockHandler = "handleException")
    public CommonResult byResource(){
        return new CommonResult(200,"按资源名称限流测试OK！",new Payment(2020L,"serial001"));
    }

    public CommonResult handleException(BlockException e){
        return new CommonResult(444,e.getClass().getCanonicalName()+"\t服务不可用！");
    }

    @GetMapping(value = "/rateLimit/byUrl")
    @SentinelResource(value = "byUrl")
    public CommonResult byUrl(){
        return new CommonResult(200,"按URL名称限流测试OK！",new Payment(2020L,"serial001"));
    }

    @GetMapping(value = "/rateLimit/customerBlockHandler")
    @SentinelResource(value = "customerBlockHandler",blockHandlerClass = CustomerBlockHandler.class,blockHandler = "handlerException2")
    public CommonResult customerBlockHandler(){
        return new CommonResult(200,"按客户自定义",new Payment(2020L,"serial001"));
    }
}
