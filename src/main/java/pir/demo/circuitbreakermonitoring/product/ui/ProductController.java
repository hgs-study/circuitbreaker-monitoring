package pir.demo.circuitbreakermonitoring.product.ui;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pir.demo.circuitbreakermonitoring.product.application.ProductService;
import pir.demo.circuitbreakermonitoring.product.domain.Product;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> products(){
        return productService.findAll();
    }
}
