package co.istad.sambath.product.service.impl;

import co.istad.sambath.product.domain.Product;
import co.istad.sambath.product.domain.dto.CreateProduct;
import co.istad.sambath.product.domain.dto.ResponseProduct;
import co.istad.sambath.product.repository.ProductRepository;
import co.istad.sambath.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;


    @Override
    public ResponseProduct findById(String uuid) {
        Product product = productRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with uuid: " + uuid));

        return mapToResponseProduct(product);
    }

    @Override
    public List<ResponseProduct> findAll() {
        return productRepository.findAll().stream()
                .map(this::mapToResponseProduct)
                .toList();
    }
    @Override
    @Transactional
    public void createProduct(@Valid CreateProduct createProduct) {
        Product product = new Product();
        product.setUuid(UUID.randomUUID().toString());
        product.setProductName(createProduct.productName());
        product.setPrice(createProduct.price());

        productRepository.save(product);
    }

    private ResponseProduct mapToResponseProduct(Product product) {
        return ResponseProduct.builder()
                .uuid(product.getUuid())
                .productName(product.getProductName())
                .price(product.getPrice())
                .build();
    }

}