package com.sparta.msa_exam.order.orders.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CreateOrder(
        String name,

        @JsonProperty("product_ids")
        List<Long> productsIds
) {

}
