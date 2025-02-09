package com.example.StorePractice.aspect;


import com.example.StorePractice.payload.request.KafkaRequest;
import com.example.StorePractice.payload.request.ProductRequest;
import com.example.StorePractice.payload.request.ReviewRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Aspect
@Component
public class LoggingAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String kafkaAPI = "http://localhost:8081/api/v1/messages/publish";
    @Autowired
    private RestTemplate URLRequest;

    @AfterReturning(value = "@annotation(com.example.StorePractice.annotations.LogUpdate)", returning = "result")
    public void afterReturning(JoinPoint joinPoint){

        String methodName =  joinPoint.getSignature().getName();
        Object[] arg = joinPoint.getArgs();

        if(arg[0] instanceof  ProductRequest) {
            ProductRequest productRequest = (ProductRequest) arg[0];
            Long productId = productRequest.getId();
            String message = "Method '{}' successfully updated product with ID: {} "+ methodName +" "+ productId;
            logger.info(message);
            messageBuilder(message);
        }

        if (arg[0] instanceof  ReviewRequest) {
            ReviewRequest ReviewRequest = (ReviewRequest) arg[0];
            Long reviewRequestId = ReviewRequest.getId();
            String message = "Method '{}' successfully updated product with ID: {} "+ methodName +" "+ reviewRequestId;
            logger.info(message);
            messageBuilder(message);
        }
    }

    @AfterThrowing(value = "@annotation(com.example.StorePractice.annotations.LogUpdate)", throwing = "result")
    public void afterThrowing(JoinPoint joinPoint, Throwable throwable){

        String methodName =  joinPoint.getSignature().getName();
        Object[] arg = joinPoint.getArgs();
        if(arg[0] instanceof  ProductRequest) {
            ProductRequest productRequest = (ProductRequest) arg[0];
            Long productId = productRequest.getId();
         //   logger.info("Method '{}' Failed while updating: {}", methodName, productId, throwable.getMessage());
            String message = "Method '{}' threw an exception while updating product with ID: {}. Exception: {}"+ methodName +" "+ productId + " " + throwable.getMessage();
            logger.info(message);
            messageBuilder(message);
        }
    }
    private void messageBuilder(String Message){
        URLRequest = new RestTemplate();
        KafkaRequest kafkaRequest = KafkaRequest.builder()
                .message(Message)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<KafkaRequest> requestEntity = new HttpEntity<>(kafkaRequest, headers);

        ResponseEntity<String> response = URLRequest.exchange(
                kafkaAPI,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        System.out.println("Response: " + response.getBody());
    }

}
