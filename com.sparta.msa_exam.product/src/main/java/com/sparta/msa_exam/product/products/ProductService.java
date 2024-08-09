package com.sparta.msa_exam.product.products;


import com.sparta.msa_exam.product.products.dto.CreateProduct;
import com.sparta.msa_exam.product.products.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;


    @CacheEvict(cacheNames = "storeAllCache", allEntries = true)
    public ProductResponse createProduct(CreateProduct request) {
        var product = productRepository.save(request.toEntity());
        return ProductResponse.from(product);
    }

    @Cacheable(cacheNames = "storeAllCache", key = "methodName")
    public List<ProductResponse> getProducts() {
        return productRepository.findAll().stream()
                .map(ProductResponse::from)
                .toList();
    }
}
