package com.example.StorePractice.aspect;

import com.example.StorePractice.models.Product;
import com.example.StorePractice.payload.request.ProductRequest;
import com.example.StorePractice.payload.request.ReviewRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LoggingAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @AfterReturning(value = "@annotation(com.example.StorePractice.annotations.LogUpdate)", returning = "result")
    public void afterReturning(JoinPoint joinPoint){

        String methodName =  joinPoint.getSignature().getName();
        Object[] arg = joinPoint.getArgs();

        if(arg[0] instanceof  ProductRequest) {
            ProductRequest productRequest = (ProductRequest) arg[0];
            Long productId = productRequest.getId();
            logger.info("Method '{}' successfully updated product with ID: {}", methodName, productId);
        }

        if (arg[0] instanceof  ProductRequest) {
            ReviewRequest ReviewRequest = (ReviewRequest) arg[0];
            Long reviewRequestId = ReviewRequest.getId();
            logger.info("Method '{}' successfully updated product with ID: {}", methodName, reviewRequestId);
        }
    }

    @AfterThrowing(value = "@annotation(com.example.StorePractice.annotations.LogUpdate)", throwing = "result")
    public void afterThrowing(JoinPoint joinPoint, Throwable throwable){

        String methodName =  joinPoint.getSignature().getName();
        Object[] arg = joinPoint.getArgs();
        if(arg[0] instanceof  ProductRequest) {
            ProductRequest productRequest = (ProductRequest) arg[0];
            Long productId = productRequest.getId();
            logger.info("Method '{}' Failed while updating: {}", methodName, productId, throwable.getMessage());
        }
    }


}
