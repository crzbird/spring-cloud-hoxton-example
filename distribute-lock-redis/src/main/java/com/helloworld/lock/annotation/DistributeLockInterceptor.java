package com.helloworld.lock.annotation;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Method;
import java.util.UUID;

public class DistributeLockInterceptor implements MethodInterceptor {

    private static final String REDIS_LOCK_LUA = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    private RedisTemplate redisTemplate;

    public DistributeLockInterceptor(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        DistributeLock distributeLock = null;
        if (method.isAnnotationPresent(DistributeLock.class)) {
            distributeLock = method.getAnnotation(DistributeLock.class);
        }
        if (distributeLock == null) {
            return methodInvocation.proceed();
        }
        @NotEmpty String lockKey = distributeLock.lockKey();
        @NotEmpty String lockValue = UUID.randomUUID().toString();
        @NotNull Long exSecond = distributeLock.exSecond();
        tryLock(lockKey, lockValue, exSecond);
        Object methodResult = methodInvocation.proceed();
        releaseLock(lockKey, lockValue);
        return methodResult;
    }

    private void tryLock(String lockKey, String lockValue, Long exSecond) {
        if (exSecond == null || exSecond.equals(0)) {
            exSecond = 6L;
        }
        try {
            Long finalExSecond = exSecond;


            redisTemplate.execute((RedisCallback) redisConnection -> {
                String value = null;
                do {
                    byte[] valueBytes = redisConnection.get(lockKey.getBytes());
                    value = valueBytes == null ? null : new String(valueBytes);
                } while (!StringUtils.isEmpty(value) ||
                        !redisConnection.set(lockKey.getBytes(), lockValue.getBytes(), Expiration.seconds(finalExSecond), RedisStringCommands.SetOption.SET_IF_ABSENT));
                System.out.println(Thread.currentThread().getName() + "\tget lock:\t" + lockKey +
                        "\tsuccess,expire in " + finalExSecond + " sec");
                return true;
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void releaseLock(String lockKey, String lockValue) {
        try {
            redisTemplate.execute((RedisCallback) redisConnection -> {
                byte[] valueBytes = redisConnection.get(lockKey.getBytes());
                String value = valueBytes == null ? null : new String(valueBytes);
                if (!StringUtils.isEmpty(value) && lockValue.equals(value)) {
                    if (!redisConnection.eval(REDIS_LOCK_LUA.getBytes(), ReturnType.INTEGER, 1, lockKey.getBytes(), lockValue.getBytes()).equals(0)) {
                        System.out.println(Thread.currentThread().getName() + "\trelease lock:\t" + lockKey);
                    } else {
                        System.out.println(Thread.currentThread().getName() + "\trelease lock:\t" + lockKey +
                                "\tfail,may it has been expire");
                    }
                } else {
                    System.out.println(Thread.currentThread().getName() + "\trelease lock:\t" + lockKey +
                            "\tfail,cause the lock dosen't exist or not belong current lock");
                }
                return true;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
