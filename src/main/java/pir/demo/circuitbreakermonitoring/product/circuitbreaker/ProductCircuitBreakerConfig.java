package pir.demo.circuitbreakermonitoring.product.circuitbreaker;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pir.demo.circuitbreakermonitoring.common.exception.ProductFindOneException;

import java.time.Duration;

@Configuration
public class ProductCircuitBreakerConfig {

    @Bean
    public CircuitBreakerConfig productFindAllCircuitBreakerConfig(){
        return CircuitBreakerConfig.custom()
                                    .slidingWindowSize(10)
                                    .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                                    .failureRateThreshold(50)
                                    .minimumNumberOfCalls(10)
                                    .waitDurationInOpenState(Duration.ofSeconds(10))
                                    .permittedNumberOfCallsInHalfOpenState(3)
                                    .recordExceptions(Exception.class)
                                    .build();
    }

    @Bean
    public CircuitBreaker productFindAllCircuitBreaker(CircuitBreakerConfig productFindAllCircuitBreakerConfig,
                                                       CircuitBreakerRegistry registry) {
        return registry.circuitBreaker(ProductCircuitBreakerProperty.FIND_ALL, productFindAllCircuitBreakerConfig);
    }

    @Bean
    public CircuitBreakerConfig productFindOneCircuitBreakerConfig(){
        return CircuitBreakerConfig.custom()
                                    .slidingWindowSize(10)
                                    .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                                    .failureRateThreshold(50)
                                    .waitDurationInOpenState(Duration.ofSeconds(10))
                                    .permittedNumberOfCallsInHalfOpenState(3)
                                    .recordExceptions(ProductFindOneException.class)
                                    .build();
    }

    @Bean
    public CircuitBreaker productFindOneCircuitBreaker(CircuitBreakerConfig productFindOneCircuitBreakerConfig,
                                                       CircuitBreakerRegistry registry) {
        return registry.circuitBreaker(ProductCircuitBreakerProperty.FIND_ONE, productFindOneCircuitBreakerConfig);
    }
}
