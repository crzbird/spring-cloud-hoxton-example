package com.springcloud.controller;

import com.springcloud.service.DubboPaymentService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer")
public class DubboConsumerController {

    @Reference
    private DubboPaymentService dubboPaymentService;

    @GetMapping(value = "/payment/{arg}")
    public String m1(@PathVariable(value = "arg")String arg){
        System.out.println(arg);
        return dubboPaymentService.m1(arg);
    }

}
