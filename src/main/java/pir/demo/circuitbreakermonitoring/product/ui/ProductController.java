package pir.demo.circuitbreakermonitoring.product.ui;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pir.demo.circuitbreakermonitoring.product.application.ProductServiceRouter;
import pir.demo.circuitbreakermonitoring.product.domain.Product;

import java.util.List;

@RestController
public class ProductController {

    private final ProductServiceRouter productServiceRouter;

    public ProductController(ProductServiceRouter productServiceRouter) {
        this.productServiceRouter = productServiceRouter;
    }

    @GetMapping("/products")
    public List<Product> products(){
        return productServiceRouter.findAll();
    }

    @GetMapping("/product")
    public Product product(){
        return productServiceRouter.findOne();
    }
}
