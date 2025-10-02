package com.learnp.kafka.demo;

import com.learnp.kafka.demo.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody
                                                CreateProductRequest request)
            throws ExecutionException, InterruptedException {
        final String productId = this.productService.createProduct(request);
        return ResponseEntity.ok("Product created :" + productId);
    }
}
