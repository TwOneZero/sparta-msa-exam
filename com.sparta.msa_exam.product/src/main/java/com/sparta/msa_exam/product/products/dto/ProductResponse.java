package com.sparta.msa_exam.product.products.dto;

import com.sparta.msa_exam.product.core.Product;

import java.io.Serializable;

public record ProductResponse(
        Long productId,
        String name,
        Integer supplyPrice
) implements Serializable {

    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getProductId(),
                product.getName(),
                product.getSupplyPrice()
        );
    }
}
