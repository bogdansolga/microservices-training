package net.safedata.microservices.training.product.inbound.adapter;

import net.safedata.microservices.training.dto.product.ProductDTO;
import net.safedata.microservices.training.marker.adapter.InboundAdapter;
import net.safedata.microservices.training.product.inbound.port.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequestMapping("/product")
public class ProductController implements InboundAdapter {

    private final ProductService productService;

    @Autowired
    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/{productId}")
    public ProductDTO getProduct(@PathVariable final long productId) {
        return productService.getById(productId);
    }
}
