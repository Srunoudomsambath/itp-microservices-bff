package co.istad.sambath.cart.client;

import co.istad.sambath.cart.client.dto.ResponseProduct;
import co.istad.sambath.cart.model.Cart;
import co.istad.sambath.cart.model.dto.AddToCartRequest;
import co.istad.sambath.cart.model.dto.ProductDTO;
import co.istad.sambath.cart.model.dto.ProductValidationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/api/v1/products")
public interface ProductClient {

    @GetExchange("/{uuid}")
    ResponseProduct findById(@PathVariable String uuid);


}