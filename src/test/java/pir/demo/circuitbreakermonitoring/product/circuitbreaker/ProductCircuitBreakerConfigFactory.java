package pir.demo.circuitbreakermonitoring.product.circuitbreaker;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import pir.demo.circuitbreakermonitoring.common.exception.ProductFindOneException;

import java.time.Duration;

import static io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.custom;

public class ProductCircuitBreakerConfigFactory {

    public static CircuitBreakerConfig createProductFindAllCircuitBreakerConfig(){
        return custom()
                .slidingWindowSize(10)
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .failureRateThreshold(50)
                .minimumNumberOfCalls(10)
                .waitDurationInOpenState(Duration.ofSeconds(10))
                .permittedNumberOfCallsInHalfOpenState(3)
                .recordExceptions(Exception.class)
                .build();
    }

    public static CircuitBreakerConfig createProductFindOneCircuitBreakerConfig(){
        return custom()
                .slidingWindowSize(10)
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(10))
                .permittedNumberOfCallsInHalfOpenState(3)
                .recordExceptions(ProductFindOneException.class)
                .build();
    }
}
