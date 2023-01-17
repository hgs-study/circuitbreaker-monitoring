package pir.demo.circuitbreakermonitoring.product.application;

import org.springframework.stereotype.Service;
import pir.demo.circuitbreakermonitoring.product.domain.Product;
import pir.demo.circuitbreakermonitoring.product.domain.ProductRepository;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }
}
