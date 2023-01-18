package pir.demo.circuitbreakermonitoring.product.application;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Component;
import pir.demo.circuitbreakermonitoring.common.exception.ProductFindAllException;
import pir.demo.circuitbreakermonitoring.common.nofity.CircuitBreakerNotifier;
import pir.demo.circuitbreakermonitoring.product.circuitbreaker.ProductCircuitBreakerFallBackProperty;
import pir.demo.circuitbreakermonitoring.product.circuitbreaker.ProductCircuitBreakerProperty;
import pir.demo.circuitbreakermonitoring.product.domain.Product;

import java.util.List;


@Component
public class ProductServiceRouter {

    private final ProductService productService;
    private final CircuitBreakerNotifier circuitBreakerNotifier;

    public ProductServiceRouter(ProductService productService, CircuitBreakerNotifier circuitBreakerNotifier) {
        this.productService = productService;
        this.circuitBreakerNotifier = circuitBreakerNotifier;
    }

    @CircuitBreaker(
            name = ProductCircuitBreakerProperty.FIND_ALL,
            fallbackMethod = ProductCircuitBreakerFallBackProperty.FIND_ALL
    )
    public List<Product> findAll(){
        return productService.findAll();
    }

    private List<Product> productFindAllFallBack(ProductFindAllException exception){
        circuitBreakerNotifier.opened(ProductCircuitBreakerProperty.FIND_ALL, exception);
        return List.of(
                new Product(3L, "fallback - mock product_03"),
                new Product(4L, "fallback - mock product_04")
        );
    }

    @CircuitBreaker(
            name = ProductCircuitBreakerProperty.FIND_ONE,
            fallbackMethod = ProductCircuitBreakerFallBackProperty.FIND_ONE
    )
    public Product findOne(){
        return productService.findOne();
    }

    private Product productFindOneFallBack(Exception exception){
        circuitBreakerNotifier.opened(ProductCircuitBreakerProperty.FIND_ONE, exception);
        return new Product(5L, "fallback - mock product_04");
    }
}
