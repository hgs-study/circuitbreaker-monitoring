package pir.demo.circuitbreakermonitoring.common.circuitbreaker;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CircuitBreakerRegistryTest {

    @Test
    public void shouldBeTheSameCircuitBreaker() {
        CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.ofDefaults();

        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("testName");
        CircuitBreaker circuitBreaker2 = circuitBreakerRegistry.circuitBreaker("testName");

        assertThat(circuitBreaker).isSameAs(circuitBreaker2);
        assertThat(circuitBreakerRegistry.getAllCircuitBreakers()).hasSize(1);
    }

    @Test
    public void shouldBeNotTheSameCircuitBreaker() {
        CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.ofDefaults();

        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("testName");
        CircuitBreaker circuitBreaker2 = circuitBreakerRegistry.circuitBreaker("otherTestName");

        assertThat(circuitBreaker).isNotSameAs(circuitBreaker2);
        assertThat(circuitBreakerRegistry.getAllCircuitBreakers()).hasSize(2);
    }

    @Test
    public void testCreateWithCustomConfiguration() {
        float failureRate = 30f;
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(failureRate).build();

        CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.of(circuitBreakerConfig);
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("testName");

        assertThat(circuitBreaker.getCircuitBreakerConfig().getFailureRateThreshold()).isEqualTo(failureRate);
    }
}
