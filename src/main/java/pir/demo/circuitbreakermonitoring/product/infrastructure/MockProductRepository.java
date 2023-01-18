package pir.demo.circuitbreakermonitoring.product.infrastructure;

import org.springframework.stereotype.Repository;
import pir.demo.circuitbreakermonitoring.common.exception.ProductFindAllException;
import pir.demo.circuitbreakermonitoring.common.util.PercentageCreator;
import pir.demo.circuitbreakermonitoring.product.domain.Product;
import pir.demo.circuitbreakermonitoring.product.domain.ProductRepository;

import java.util.List;

@Repository
public class MockProductRepository implements ProductRepository {

    @Override
    public List<Product> findAllByHalfPercent() {
        if(PercentageCreator.half()){
            throw new ProductFindAllException("[외부 통신 장애 발생 - findAll]");
        }

        return List.of(
                new Product(1L, "product_01"),
                new Product(2L, "product_02")
        );
    }

    @Override
    public Product findOneByHalfPercent() {
        if(PercentageCreator.half()){
            throw new ProductFindAllException("[외부 통신 장애 발생 - findOne]");
        }

        return new Product(1L, "product_01");
    }
}
