package com.cloudalibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class FlowLimitController {

    @GetMapping(value = "/testA")
    public String testA() {
        try {
            TimeUnit.MILLISECONDS.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "------testA";
    }

    @GetMapping(value = "/testB")
    public String testB() {
        log.info(Thread.currentThread().getName() + "\t ...... testB");
        return "------testB";
    }

    @GetMapping(value = "/testD")
    public String testD() {
        /*try {
            TimeUnit.MILLISECONDS.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        log.info("testD 测试RT");*/
        log.info("testD-----测试异常比例");
        int i = 1 / 0;
        return "-----testD";
    }

    @GetMapping(value = "/testE")
    public String testE() {
        log.info("testE ----- 测试异常数量");
        int i = 1 / 0;
        return "--------testE";
    }

    @GetMapping(value = "/testHotKey")
    @SentinelResource(value = "testHotKey",blockHandler = "deal_testHotKey")
    public String testHotKey(@RequestParam(value = "p1", required = false) String p1, @RequestParam(value = "p2", required = false) String p2) {
        int i=1/0;
        return "-------testHotKey";
    }

    public String deal_testHotKey(String p1, String p2, BlockException e){
        return "-------testHotKey,\t deal_testHotKey";//sentinel默认提示:Blocked by sentinel(flow limiting)
    }



}
