package com.agleveratto.superhero.domain.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeAdvice {
    Logger logger = LoggerFactory.getLogger(ExecutionTimeAdvice.class);
    @Around("@annotation(com.agleveratto.superhero.domain.annotations.ExecutionTime)")
    public Object executionTime(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object object = point.proceed();
        long endTime = System.currentTimeMillis();
        logger.info(new StringBuilder()
                .append("Class Name: ").append(point.getSignature().getDeclaringTypeName()).append(". ")
                .append("Method Name: ").append(point.getSignature().getName()).append(". ")
                .append("Time taken for Execution is: ").append(endTime-startTime).append("ms")
                .toString());
        return object;
    }
}

