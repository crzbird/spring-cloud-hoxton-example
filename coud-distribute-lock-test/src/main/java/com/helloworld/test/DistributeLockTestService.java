package com.helloworld.test;

import com.helloworld.lock.annotation.DistributeLock;
import org.springframework.stereotype.Component;

@Component
public class DistributeLockTestService {

    @DistributeLock(lockKey = "testLockD", exSecond = 10L)
    public void test() {
        for (int i = 0; i < 1000; i++) {
            DistributeLockResource.count++;
        }
    }

}
