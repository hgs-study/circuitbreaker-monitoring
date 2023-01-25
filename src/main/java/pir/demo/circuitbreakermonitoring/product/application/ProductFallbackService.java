package pir.demo.circuitbreakermonitoring.product.application;

import org.springframework.stereotype.Service;
import pir.demo.circuitbreakermonitoring.product.domain.Product;
import pir.demo.circuitbreakermonitoring.product.infrastructure.MockFallbackProductRepository;

import java.util.List;

@Service
public class ProductFallbackService {
    private final MockFallbackProductRepository mockFallbackProductRepository;

    public ProductFallbackService(MockFallbackProductRepository mockFallbackProductRepository) {
        this.mockFallbackProductRepository = mockFallbackProductRepository;
    }

    public List<Product> findAll(){
        return mockFallbackProductRepository.findAll();
    }

    public Product findOne(){
        return mockFallbackProductRepository.findOne();
    }
}
