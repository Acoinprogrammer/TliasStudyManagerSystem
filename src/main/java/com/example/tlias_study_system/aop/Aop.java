package com.example.tlias_study_system.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Aspect
@Component
@ControllerAdvice
public class Aop {
    public static final Logger log = LoggerFactory.getLogger(Aop.class);
    @Around("execution(public * com.example.tlias_study_system.controller.*.*(..))")
    public Object RecordTime(ProceedingJoinPoint pjp) throws Throwable {
        double stime = System.currentTimeMillis();
        Object result = pjp.proceed();
        double etime = System.currentTimeMillis();
        log.info("{} 方法执行耗时 {} s",pjp.getSignature(),(etime-stime)/1000);
        return result;
    }
}
