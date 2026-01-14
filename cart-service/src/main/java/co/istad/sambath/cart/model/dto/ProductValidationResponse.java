package co.istad.sambath.cart.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductValidationResponse {
    private boolean exists;
    private Integer id;
    private String productName;
    private BigDecimal price;
    private String uuid;
    private Integer quantity;
}