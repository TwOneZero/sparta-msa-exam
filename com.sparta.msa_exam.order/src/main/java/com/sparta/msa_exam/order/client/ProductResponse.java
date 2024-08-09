package com.sparta.msa_exam.order.client;

public record ProductResponse(
        Long productId,
        String name,
        Integer supplyPrice
) {
}
