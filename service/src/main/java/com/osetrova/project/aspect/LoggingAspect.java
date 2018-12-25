package com.osetrova.project.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private Logger log = Logger.getRootLogger();

    @Pointcut("within(com.osetrova.project.service..*) ")
    public void logging() {}

    @Around("logging()")
    public Object addLogging(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("method " + joinPoint.getSignature().getName()
                + " with args " + Arrays.toString(joinPoint.getArgs())
                + " in Class " + joinPoint.getSignature().getDeclaringTypeName() + " started");
        Object result;

        try {
            result = joinPoint.proceed();
        } catch (Throwable ex) {
            log.error("method " + joinPoint.getSignature().getName()
                    + " with args " + Arrays.toString(joinPoint.getArgs())
                    + " in Class " + joinPoint.getSignature().getDeclaringTypeName()
                    + " threw an Exception " + ex);
            throw ex;
        }

        log.info("method " + joinPoint.getSignature().getName()
                + " with args " + Arrays.toString(joinPoint.getArgs())
                + " in Class " + joinPoint.getSignature().getDeclaringTypeName() + " successfully executed");

        return result;
    }
}
