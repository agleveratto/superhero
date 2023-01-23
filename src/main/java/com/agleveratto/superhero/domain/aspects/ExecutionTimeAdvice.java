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
        logger.info("Class Name: "+ point.getSignature().getDeclaringTypeName() +". " +
                "Method Name: "+ point.getSignature().getName() + ". " +
                "Time taken for Execution is: " + (endTime-startTime) +"ms");
        return object;
    }
}

