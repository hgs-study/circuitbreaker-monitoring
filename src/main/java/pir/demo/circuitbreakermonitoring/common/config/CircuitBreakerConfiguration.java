package pir.demo.circuitbreakermonitoring.common.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pir.demo.circuitbreakermonitoring.common.exception.ProductFindAllException;

import java.time.Duration;

@Configuration
public class CircuitBreakerConfiguration {

    @Bean
    public CircuitBreakerConfig productFindAllCircuitBreakerConfig(){
        return CircuitBreakerConfig.custom()
                                    .slidingWindowSize(10)
                                    .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                                    .failureRateThreshold(50)
                                    .waitDurationInOpenState(Duration.ofSeconds(10))
                                    .recordExceptions(ProductFindAllException.class)
                                    .build();
    }
}
