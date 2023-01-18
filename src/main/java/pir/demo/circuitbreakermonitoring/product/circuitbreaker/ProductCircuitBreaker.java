package pir.demo.circuitbreakermonitoring.product.circuitbreaker;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pir.demo.circuitbreakermonitoring.common.exception.ProductFindAllException;

import java.time.Duration;

@Configuration
public class ProductCircuitBreaker {

    @Bean
    public CircuitBreaker productFindAll() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                                                            .slidingWindowSize(10)
                                                            .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                                                            .failureRateThreshold(50)
                                                            .waitDurationInOpenState(Duration.ofSeconds(10))
                                                            .recordExceptions(ProductFindAllException.class)
                                                            .build();

        return CircuitBreaker.of("productFindAll", config);
    }

    @Bean
    public CircuitBreaker productFindOne(CircuitBreakerConfig defaultCircuitBreakerConfig){
        return CircuitBreaker.of("productFindOne", defaultCircuitBreakerConfig);
    }
}
