package com.springcloud.service.impl;

import com.springcloud.service.DubboPaymentService;
import org.apache.dubbo.config.annotation.Service;

@Service
public class DubboPaymentServiceImpl implements DubboPaymentService {
    @Override
    public String m1(String arg) {
        System.out.println(arg);
        return arg+10002;
    }
}
