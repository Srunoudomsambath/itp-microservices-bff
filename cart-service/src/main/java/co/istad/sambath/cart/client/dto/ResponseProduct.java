package co.istad.sambath.cart.client.dto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder

public record ResponseProduct(

        String uuid,

        String productName,

        BigDecimal price,
        Integer quantity

) { }