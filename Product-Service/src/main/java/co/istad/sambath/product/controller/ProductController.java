package co.istad.sambath.product.controller;

import co.istad.sambath.product.domain.dto.CreateProduct;
import co.istad.sambath.product.domain.dto.ResponseProduct;
import co.istad.sambath.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ResponseProduct>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping(path = "{uuid}")
    public ResponseEntity<ResponseProduct> findById(@PathVariable String uuid) {
        return ResponseEntity.ok(productService.findById(uuid));
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@Valid @RequestBody CreateProduct createProduct) {
        productService.createProduct(createProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product created successfully");
    }
}