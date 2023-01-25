package pir.demo.circuitbreakermonitoring.product.infrastructure;

import org.springframework.stereotype.Repository;
import pir.demo.circuitbreakermonitoring.product.domain.Product;

import java.util.List;

@Repository
public class MockFallbackProductRepository {

    public List<Product> findAll() {
        return List.of(
                new Product(4L, "[fallback data] - product_04"),
                new Product(5L, "[fallback data] - product_05"),
                new Product(6L, "[fallback data] - product_06")
        );
    }

    public Product findOne() {
        return new Product(4L, "[fallback data] - product_04");
    }
}
