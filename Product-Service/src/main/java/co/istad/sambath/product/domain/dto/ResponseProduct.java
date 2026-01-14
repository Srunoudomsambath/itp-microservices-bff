package co.istad.sambath.product.domain.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ResponseProduct(

        String uuid,

        String productName,

        BigDecimal price,
        Integer quantity

) { }