package com.helloworld.lock.annotation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributeLock {

    @NotEmpty
    String lockKey();

    @NotNull
    long exSecond();


}
