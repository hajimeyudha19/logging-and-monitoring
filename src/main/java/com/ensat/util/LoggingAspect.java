package com.ensat.util;

import java.time.Duration;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = Logger.getLogger(LoggingAspect.class.getName());
    private static final ThreadLocal<Instant> startTimeThreadLocal = new ThreadLocal<>();

    @Pointcut("execution(* com.ensat.controller..*(..))")
    public void controllerMethods() {}

    @Around("controllerMethods()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Instant startTime = Instant.now();
        startTimeThreadLocal.set(startTime);

        Object proceed = null;
        try {
            proceed = joinPoint.proceed(); // Call the method
            return proceed;
        } finally {
            logAfterReturning(joinPoint);
            startTimeThreadLocal.remove();
        }
    }

    private void logAfterReturning(ProceedingJoinPoint joinPoint) {
        Instant startTime = startTimeThreadLocal.get();
        if (startTime != null) {
            Duration duration = Duration.between(startTime, Instant.now());
            String methodName = joinPoint.getSignature().toShortString();
            String httpMethod = "UNKNOWN"; // You might need to determine this based on your setup
            int statusCode = 200; // Default to 200; actual status code needs to be captured
            String url = "UNKNOWN"; // You might need to capture the URL or path

            logger.log(Level.INFO, String.format("Method: %s, HTTP Status: %d, Execution Time: %d ms, URL: %s",
                    methodName, statusCode, duration.toMillis(), url));
        } else {
            logger.log(Level.WARNING, "Start time was not set for logging");
        }
    }
}
