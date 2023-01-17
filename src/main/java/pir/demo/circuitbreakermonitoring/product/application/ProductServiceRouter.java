package pir.demo.circuitbreakermonitoring.product.application;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pir.demo.circuitbreakermonitoring.common.exception.ProductFindAllException;
import pir.demo.circuitbreakermonitoring.product.domain.Product;

import java.util.List;


@Component
public class ProductServiceRouter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceRouter.class);
    private final ProductService productService;

    public ProductServiceRouter(ProductService productService) {
        this.productService = productService;
    }

    @CircuitBreaker(name = "productFindAll", fallbackMethod = "findAllFallback")
    public List<Product> findAll(){
        return productService.findAll();
    }

    public List<Product> findAllFallback(ProductFindAllException exception){
        LOGGER.error("productFindAll Open");
        return List.of(new Product(3L, "fallback - mock product_03"));
    }
}
