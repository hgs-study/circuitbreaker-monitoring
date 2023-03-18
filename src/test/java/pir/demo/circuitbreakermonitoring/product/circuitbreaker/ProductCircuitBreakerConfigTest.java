package pir.demo.circuitbreakermonitoring.product.circuitbreaker;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.internal.InMemoryCircuitBreakerRegistry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pir.demo.circuitbreakermonitoring.common.exception.ProductFindOneException;

import java.time.Duration;

import static org.assertj.core.api.BDDAssertions.then;

class ProductCircuitBreakerConfigTest {

    @Test
    @DisplayName("ProductFindAllCircuitBreaker Configuration 생성 테스트")
    void productFindAllCircuitBreakerConfigTest(){
        CircuitBreakerConfig productFindAllCircuitBreakerConfig = ProductCircuitBreakerConfigFactory.createProductFindAllCircuitBreakerConfig();

        then(productFindAllCircuitBreakerConfig.getSlidingWindowSize()).isEqualTo(10);
        then(productFindAllCircuitBreakerConfig.getSlidingWindowType()).isEqualTo(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED);
        then(productFindAllCircuitBreakerConfig.getFailureRateThreshold()).isEqualTo(50);
        then(productFindAllCircuitBreakerConfig.getMinimumNumberOfCalls()).isEqualTo(10);
        then(productFindAllCircuitBreakerConfig.getWaitDurationInOpenState()).isEqualTo(Duration.ofSeconds(10));
        then(productFindAllCircuitBreakerConfig.getPermittedNumberOfCallsInHalfOpenState()).isEqualTo(3);
        then(productFindAllCircuitBreakerConfig.getRecordExceptionPredicate().test(new Exception())).isTrue();
    }

    @Test
    @DisplayName("CircuitBreakerRegistry에 productFindAllCircuitBreaker 추가")
    void registerProductFindAllCircuitBreakerTest(){
        CircuitBreakerConfig productFindAllCircuitBreakerConfig = ProductCircuitBreakerConfigFactory.createProductFindAllCircuitBreakerConfig();
        InMemoryCircuitBreakerRegistry registry = new InMemoryCircuitBreakerRegistry();

        CircuitBreaker productFindAllCircuitBreaker = registry.circuitBreaker(ProductCircuitBreakerProperty.FIND_ALL, productFindAllCircuitBreakerConfig);

        then(registry.getAllCircuitBreakers()).hasSize(1);
        then(registry.getAllCircuitBreakers()).contains(productFindAllCircuitBreaker);
    }

    @Test
    @DisplayName("ProductFindOneCircuitBreaker Configuration 생성 테스트")
    void productFindOneCircuitBreakerConfigTest(){
        CircuitBreakerConfig productFindOneCircuitBreakerConfig = ProductCircuitBreakerConfigFactory.createProductFindOneCircuitBreakerConfig();

        then(productFindOneCircuitBreakerConfig.getSlidingWindowSize()).isEqualTo(10);
        then(productFindOneCircuitBreakerConfig.getSlidingWindowType()).isEqualTo(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED);
        then(productFindOneCircuitBreakerConfig.getFailureRateThreshold()).isEqualTo(50);
        then(productFindOneCircuitBreakerConfig.getWaitDurationInOpenState()).isEqualTo(Duration.ofSeconds(10));
        then(productFindOneCircuitBreakerConfig.getPermittedNumberOfCallsInHalfOpenState()).isEqualTo(3);
        then(productFindOneCircuitBreakerConfig.getRecordExceptionPredicate().test(new ProductFindOneException(""))).isTrue();
    }

    @Test
    @DisplayName("CircuitBreakerRegistry에 productFindOneCircuitBreaker 추가")
    void registerProductFindOneCircuitBreakerTest(){
        CircuitBreakerConfig productFindAllCircuitBreakerConfig = ProductCircuitBreakerConfigFactory.createProductFindAllCircuitBreakerConfig();
        InMemoryCircuitBreakerRegistry registry = new InMemoryCircuitBreakerRegistry();

        CircuitBreaker productFindOneCircuitBreaker = registry.circuitBreaker(ProductCircuitBreakerProperty.FIND_ONE, productFindAllCircuitBreakerConfig);

        then(registry.getAllCircuitBreakers()).hasSize(1);
        then(registry.getAllCircuitBreakers()).contains(productFindOneCircuitBreaker);
    }

    @Test
    @DisplayName("CircuitBreakerRegistry에 productFindAllCircuitBreaker, productFindOneCircuitBreaker 추가")
    void registerProductFindAllAndProductFindOneCircuitBreakerTest(){
        CircuitBreakerConfig productFindAllCircuitBreakerConfig = ProductCircuitBreakerConfigFactory.createProductFindAllCircuitBreakerConfig();
        CircuitBreakerConfig productFindOneCircuitBreakerConfig = ProductCircuitBreakerConfigFactory.createProductFindOneCircuitBreakerConfig();
        CircuitBreakerRegistry registry = new InMemoryCircuitBreakerRegistry();

        CircuitBreaker productFindAllCircuitBreaker = registry.circuitBreaker(ProductCircuitBreakerProperty.FIND_ALL, productFindAllCircuitBreakerConfig);
        CircuitBreaker productFindOneCircuitBreaker = registry.circuitBreaker(ProductCircuitBreakerProperty.FIND_ONE, productFindOneCircuitBreakerConfig);

        then(registry.getAllCircuitBreakers()).hasSize(2);
        then(registry.getAllCircuitBreakers()).contains(productFindAllCircuitBreaker, productFindOneCircuitBreaker);
    }
}