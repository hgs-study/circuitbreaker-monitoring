package pir.demo.circuitbreakermonitoring.product.infrastructure;

import org.springframework.stereotype.Repository;
import pir.demo.circuitbreakermonitoring.product.domain.Product;
import pir.demo.circuitbreakermonitoring.product.domain.ProductRepository;

import java.util.List;

@Repository
public class MockProductRepository implements ProductRepository {

    @Override
    public List<Product> findAll() {
        return List.of(
                new Product(1L, "product_01"),
                new Product(2L, "product_02")
        );
    }
}
