package com.sparta.msa_exam.product.products;


import com.sparta.msa_exam.product.products.dto.CreateProduct;
import com.sparta.msa_exam.product.products.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(
            @RequestHeader(value = "X-User-Id") String userId,
                                  @RequestBody CreateProduct request) {
        return productService.createProduct(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAll() {
        return productService.getProducts();
    }
}
