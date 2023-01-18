package pir.demo.circuitbreakermonitoring.common.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CircuitBreakerConfiguration {

    @Bean
    public CircuitBreakerConfig defaultCircuitBreakerConfig(){
        return CircuitBreakerConfig.custom()
                                    .slidingWindowSize(10)
                                    .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                                    .failureRateThreshold(50)
                                    .waitDurationInOpenState(Duration.ofSeconds(10))
                                    .recordExceptions(Exception.class)
                                    .build();
    }
}
