package co.istad.sambath.product.service;

import co.istad.sambath.product.domain.dto.CreateProduct;
import co.istad.sambath.product.domain.dto.ResponseProduct;
import jakarta.validation.Valid;

import java.util.List;

public interface ProductService {

    ResponseProduct findById(String uuid);

    List<ResponseProduct> findAll();
    void createProduct(@Valid CreateProduct createProduct);

}