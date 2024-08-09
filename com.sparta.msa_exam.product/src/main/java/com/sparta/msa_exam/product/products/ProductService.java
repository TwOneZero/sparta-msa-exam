package com.sparta.msa_exam.product.products;


import com.sparta.msa_exam.product.products.dto.CreateProduct;
import com.sparta.msa_exam.product.products.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(CreateProduct request) {
        productRepository.save(
                request.toEntity()
        );
    }

    public List<ProductResponse> getProducts() {
        return productRepository.findAll().stream()
                .map(ProductResponse::from)
                .toList();
    }
}
