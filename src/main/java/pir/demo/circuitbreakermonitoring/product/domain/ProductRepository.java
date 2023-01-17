package pir.demo.circuitbreakermonitoring.product.domain;

import java.util.List;

public interface ProductRepository {

    List<Product> findAll();
}
