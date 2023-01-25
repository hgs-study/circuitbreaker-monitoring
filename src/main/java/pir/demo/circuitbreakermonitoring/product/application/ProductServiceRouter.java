package pir.demo.circuitbreakermonitoring.product.application;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Component;
import pir.demo.circuitbreakermonitoring.common.exception.ProductFindAllException;
import pir.demo.circuitbreakermonitoring.common.exception.ProductFindOneException;
import pir.demo.circuitbreakermonitoring.product.circuitbreaker.ProductCircuitBreakerFallBackProperty;
import pir.demo.circuitbreakermonitoring.product.circuitbreaker.ProductCircuitBreakerProperty;
import pir.demo.circuitbreakermonitoring.product.domain.Product;

import java.util.List;


@Component
public class ProductServiceRouter {

    private final ProductService productService;
    private final ProductFallbackService productFallbackService;

    public ProductServiceRouter(ProductService productService, ProductFallbackService productFallbackService) {
        this.productService = productService;
        this.productFallbackService = productFallbackService;
    }

    @CircuitBreaker(
            name = ProductCircuitBreakerProperty.FIND_ALL,
            fallbackMethod = ProductCircuitBreakerFallBackProperty.FIND_ALL
    )
    public List<Product> findAll(){
        return productService.findAll();
    }

    public List<Product> productFindAllFallBack(ProductFindAllException exception){
        return productFallbackService.findAll();
    }

    @CircuitBreaker(
            name = ProductCircuitBreakerProperty.FIND_ONE,
            fallbackMethod = ProductCircuitBreakerFallBackProperty.FIND_ONE
    )
    public Product findOne(){
        return productService.findOne();
    }

    public Product productFindOneFallBack(Exception exception){
        return productFallbackService.findOne();
    }
}
