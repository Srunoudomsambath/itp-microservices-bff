package co.istad.sambath.cart.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private String uuid;
    private String name;
    private BigDecimal price;
    private Integer quantity;
}
