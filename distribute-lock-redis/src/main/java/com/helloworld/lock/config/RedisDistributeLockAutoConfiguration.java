package com.helloworld.lock.config;

import com.helloworld.lock.annotation.DistributeLock;
import com.helloworld.lock.annotation.DistributeLockInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

@ConditionalOnClass(StringRedisTemplate.class)
public class RedisDistributeLockAutoConfiguration {


    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor(@Autowired StringRedisTemplate stringRedisTemplate) {
        DistributeLockInterceptor distributeLockInterceptor = new DistributeLockInterceptor(stringRedisTemplate);
        AnnotationMatchingPointcut pointcut = AnnotationMatchingPointcut.forMethodAnnotation(DistributeLock.class);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(distributeLockInterceptor);
        return advisor;
    }
}
