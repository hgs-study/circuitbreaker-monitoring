package pir.demo.circuitbreakermonitoring.product.circuitbreaker;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.vavr.control.Try;
import org.junit.jupiter.api.*;
import pir.demo.circuitbreakermonitoring.common.exception.ProductFindOneException;
import pir.demo.circuitbreakermonitoring.product.application.ProductService;
import pir.demo.circuitbreakermonitoring.product.domain.Product;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

public class ProductCircuitBreakerTest {

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = mock(ProductService.class);
    }

    @Test
    public void shouldExecuteSupplierAndReturnWithSuccess() {
        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("productFindAllCircuitBreaker");
        CircuitBreaker.Metrics metrics = circuitBreaker.getMetrics();
        assertThat(metrics.getNumberOfBufferedCalls()).isZero();
        Product product1 = new Product(1L, "product_01");
        Product product2 = new Product(1L, "product_02");
        given(productService.findAll()).willReturn(List.of(product1, product2));

        List<Product> products = circuitBreaker.executeSupplier(productService::findAll);

        assertThat(products).hasSize(2);
        assertThat(products).contains(product1, product2);
        assertThat(metrics.getNumberOfBufferedCalls()).isEqualTo(1);
        assertThat(metrics.getNumberOfFailedCalls()).isZero();
        assertThat(metrics.getNumberOfSuccessfulCalls()).isEqualTo(1);
        then(productService).should().findAll();
    }

    @Test
    public void shouldDecorateSupplierAndReturnWithException() {
        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("productFindAllCircuitBreaker");
        CircuitBreaker.Metrics metrics = circuitBreaker.getMetrics();
        assertThat(metrics.getNumberOfBufferedCalls()).isZero();
        given(productService.findAll()).willThrow(new RuntimeException("Exception!"));
        Supplier<List<Product>> supplier = circuitBreaker.decorateSupplier(productService::findAll);

        Try<List<Product>> result = Try.ofSupplier(supplier);

        assertThat(result.isFailure()).isTrue();
        assertThat(result.failed().get()).isInstanceOf(RuntimeException.class);
        assertThat(metrics.getNumberOfBufferedCalls()).isEqualTo(1);
        assertThat(metrics.getNumberOfFailedCalls()).isEqualTo(1);
        assertThat(metrics.getNumberOfSuccessfulCalls()).isZero();
        then(productService).should().findAll();
    }

    @Test
    @DisplayName("서킷브레이커 Open 테스트")
    public void circuitBreakerOpenException() throws Exception {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .slidingWindowSize(2)
                .permittedNumberOfCallsInHalfOpenState(2)
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .build();
        CircuitBreaker circuitBreaker = CircuitBreaker.of("testName", circuitBreakerConfig);
        Product product1 = new Product(1L, "product_01");
        Product product2 = new Product(1L, "product_02");
        given(productService.findAll()).willReturn(List.of(product1, product2));

        circuitBreaker.onError(0, TimeUnit.NANOSECONDS, new RuntimeException());
        circuitBreaker.onError(0, TimeUnit.NANOSECONDS, new RuntimeException());

        assertThat(circuitBreaker.getState()).isEqualTo(CircuitBreaker.State.OPEN);
        CircuitBreaker.Metrics metrics = circuitBreaker.getMetrics();
        assertThat(metrics.getNumberOfBufferedCalls()).isEqualTo(2);
        assertThat(metrics.getNumberOfFailedCalls()).isEqualTo(2);
    }

    @Test
    @DisplayName("productFindAll 서킷브레이커 Open 테스트")
    public void productFindAllCircuitBreakerOpenException() throws Exception {
        CircuitBreakerConfig circuitBreakerConfig = ProductCircuitBreakerConfigFactory.createProductFindAllCircuitBreakerConfig();
        CircuitBreaker circuitBreaker = CircuitBreaker.of(ProductCircuitBreakerProperty.FIND_ALL, circuitBreakerConfig);
        Product product1 = new Product(1L, "product_01");
        Product product2 = new Product(1L, "product_02");
        List<Product> products = List.of(product1, product2);
        given(productService.findAll()).willReturn(products);

        int minimumNumberOfCalls = circuitBreakerConfig.getMinimumNumberOfCalls();
        int slidingWindowSize = circuitBreakerConfig.getSlidingWindowSize();
        float failureRateThreshold = circuitBreakerConfig.getFailureRateThreshold();
        float failureCountWithPrime = slidingWindowSize * (failureRateThreshold / 100);
        int failureCount = (int) Math.ceil(failureCountWithPrime);
        int failureLoopCount = 0;

        for (int i = 0; i < minimumNumberOfCalls; i++) {
            if(notYetOpen(failureCount, failureLoopCount)) {
                failureLoopCount++;
                circuitBreaker.onError(0, TimeUnit.NANOSECONDS, new Exception());
            }else{
                circuitBreaker.onSuccess(0, TimeUnit.NANOSECONDS);
            }
        }

        CircuitBreaker.Metrics metrics = circuitBreaker.getMetrics();
        assertThat(metrics.getNumberOfBufferedCalls()).isEqualTo(minimumNumberOfCalls);
        assertThat(metrics.getNumberOfFailedCalls()).isEqualTo(failureCount);
        assertThat(circuitBreaker.getState()).isEqualTo(CircuitBreaker.State.OPEN);
    }

    @TestFactory
    @DisplayName("첫번째 서킷브레이커 - minimumNumber 기준, 두번째 서킷브레이커 - slidingWindowSize 기준 테스트")
    Stream<DynamicTest> productFindOneCircuitBreakerOpenException() throws Exception {
        CircuitBreakerConfig circuitBreakerConfig = ProductCircuitBreakerConfigFactory.createProductFindOneCircuitBreakerConfig();
        CircuitBreaker circuitBreaker = CircuitBreaker.of(ProductCircuitBreakerProperty.FIND_ONE, circuitBreakerConfig);
        Product product1 = new Product(1L, "product_01");
        given(productService.findOne()).willReturn(product1);

        return Stream.of(
                dynamicTest("첫 서킷 브레이커 오픈 (minimumNumber 기준 (defualt 100))", () ->{
                    int minimumNumberOfCalls = circuitBreakerConfig.getMinimumNumberOfCalls();
                    float failureRateThreshold = circuitBreakerConfig.getFailureRateThreshold();
                    float failureCountWithPrime = minimumNumberOfCalls * (failureRateThreshold / 100);
                    int failureCount = (int) Math.ceil(failureCountWithPrime);
                    int failureLoopCount = 0;
                    openCircuitBreaker(circuitBreaker, minimumNumberOfCalls, failureCount, failureLoopCount);

                    assertThat(circuitBreaker.getState()).isEqualTo(CircuitBreaker.State.OPEN);
                }),
                dynamicTest("두번째 서킷 브레이커 오픈 (slidingWindowSize 기준)", () ->{
                    // 두번째 테스트를 위해 서킷 브레이커 CLOSED 로 초기화
                    circuitBreaker.transitionToClosedState();
                    assertThat(circuitBreaker.getState()).isEqualTo(CircuitBreaker.State.CLOSED);

                    int slidingWindowSize = circuitBreakerConfig.getSlidingWindowSize();
                    float failureRateThreshold = circuitBreakerConfig.getFailureRateThreshold();
                    float failureCountWithPrime = slidingWindowSize * (failureRateThreshold / 100);
                    int failureCount = (int) Math.ceil(failureCountWithPrime);
                    int failureLoopCount = 0;
                    openCircuitBreaker(circuitBreaker, slidingWindowSize, failureCount, failureLoopCount);

                    CircuitBreaker.Metrics metrics = circuitBreaker.getMetrics();
                    assertThat(metrics.getNumberOfBufferedCalls()).isEqualTo(slidingWindowSize);
                    assertThat(metrics.getNumberOfFailedCalls()).isEqualTo(failureCount);
                    assertThat(circuitBreaker.getState()).isEqualTo(CircuitBreaker.State.OPEN);
                })
        );
    }

    private void openCircuitBreaker(CircuitBreaker circuitBreaker, int minimumNumberOfCalls, int failureCount, int failureLoopCount) {
        for (int i = 0; i < minimumNumberOfCalls; i++) {
            if(notYetOpen(failureCount, failureLoopCount)) {
                failureLoopCount++;
                circuitBreaker.onError(0, TimeUnit.NANOSECONDS, new ProductFindOneException("Exception!"));
            }else{
                circuitBreaker.onSuccess(0, TimeUnit.NANOSECONDS);
            }
        }
    }

    private boolean notYetOpen(int failureCount, int failureLoopCount) {
        return failureLoopCount < failureCount;
    }
}
