package pir.demo.circuitbreakermonitoring.product.circuitbreaker;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.junit.jupiter.api.Test;

import static io.github.resilience4j.circuitbreaker.CallNotPermittedException.createCallNotPermittedException;
import static org.assertj.core.api.Assertions.assertThat;

class ProductCircuitBreakerOpenExceptionTest {

    @Test
    public void shouldReturnCorrectMessageWhenStateIsOpen() {
        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("productFindAllCircuitBreaker");
        circuitBreaker.transitionToOpenState();
        assertThat(createCallNotPermittedException(circuitBreaker).getMessage())
                .isEqualTo("CircuitBreaker 'productFindAllCircuitBreaker' is OPEN and does not permit further calls");
    }

    @Test
    public void shouldReturnCorrectMessageWhenStateIsForcedOpen() {
        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("productFindAllCircuitBreaker");
        circuitBreaker.transitionToForcedOpenState();
        assertThat(createCallNotPermittedException(circuitBreaker).getMessage()).isEqualTo(
                "CircuitBreaker 'productFindAllCircuitBreaker' is FORCED_OPEN and does not permit further calls");
    }
}