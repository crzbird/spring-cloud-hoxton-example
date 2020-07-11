package com.cloudalibaba.myHandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.springcloud.entities.CommonResult;
//指定 blockHandlerClass 为对应的类的 Class 对象，注意对应的函数必需为 static 函数，否则无法解析。
public class CustomerBlockHandler {

    public static CommonResult handlerException(BlockException e){
        return new CommonResult(4444,"按客户自定义,global Handler Exception-----1");
    }
    public static CommonResult handlerException2(BlockException e){
        return new CommonResult(4444,"按客户自定义,global Handler Exception-----2");
    }


}
