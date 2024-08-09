package com.sparta.msa_exam.product.products.dto;

import com.sparta.msa_exam.product.core.Product;

public record CreateProduct(
        String name,
        Integer supplyPrice
) {

    public Product toEntity(){
        return Product.builder()
                .name(name)
                .supplyPrice(supplyPrice)
                .build();
    }
}
