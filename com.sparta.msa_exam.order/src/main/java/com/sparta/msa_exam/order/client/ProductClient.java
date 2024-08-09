package com.sparta.msa_exam.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductClient {
//    @GetMapping("/products/{id}")
//    ProductResponse getProduct(@PathVariable("id") Long id);

    @GetMapping("/products")
    List<ProductResponse> getAll();

}

