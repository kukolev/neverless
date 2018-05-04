package neverless.util.logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Profile("logs")
public class InvocationLogger {

    /** Handle to the log file */
    private final Log log = LogFactory.getLog(getClass());

    public InvocationLogger() {}

    @Before("execution(* neverless..*.*(..))")
    public void logMethodAccessBefore(JoinPoint joinPoint) {

        log.info("***** Starting: " + joinPoint.getSignature().getName() + " with args = " + joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "execution(* neverless..*.*(..))", returning = "result")
    public void logMethodAccessAfter(JoinPoint joinPoint, Object result) {

        log.info("***** Completed: " + joinPoint.getSignature().getName() + " with result = " + result);
    }
}