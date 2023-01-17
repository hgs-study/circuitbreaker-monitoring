package pir.demo.circuitbreakermonitoring.common.circuitbreaker;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductCircuitBreaker {

    @Bean
    public CircuitBreaker productFindAll(CircuitBreakerConfig productFindAllCircuitBreakerConfig) {
        CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.of(productFindAllCircuitBreakerConfig);
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("productFindAll");

        return circuitBreaker;
    }
}
