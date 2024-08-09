package com.sparta.msa_exam.order.orders;


import com.sparta.msa_exam.order.client.ProductClient;
import com.sparta.msa_exam.order.client.ProductResponse;
import com.sparta.msa_exam.order.core.Order;
import com.sparta.msa_exam.order.core.OrderProduct;
import com.sparta.msa_exam.order.orders.dto.AddProductToOrder;
import com.sparta.msa_exam.order.orders.dto.CreateOrder;
import com.sparta.msa_exam.order.orders.dto.OrderResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductClient productClient;

    public void createOrder(CreateOrder request) {
        // 상품 목록 조회 - ID 만 비교
        Set<Long> existsProductIds = productClient.getAll().stream()
                .map(ProductResponse::productId)
                .collect(Collectors.toSet());
        log.info("existsProductIds: {}", existsProductIds);
        //주문 정보 저장
        var newOrder =  orderRepository.save(Order.builder().name(request.name()).build());
        List<OrderProduct> orderProducts = new ArrayList<>();

        // 주문 저장 로직
        if (!request.productsIds().isEmpty()) {
            request.productsIds().stream()
                    .filter(existsProductIds::contains) //상품이 존재한다면 추가
                    .forEach(id -> {
                        log.info("request Product Id :{}", id);
                        var newOrderProduct = OrderProduct.builder()
                                .order(newOrder).productId(id)
                                .build();
                        orderProducts.add(newOrderProduct);
                    });

            if (orderProducts.isEmpty()) {
                throw new IllegalArgumentException("주문에 유효한 상품 아이디가 없습니다.");
            }
        } else {
            throw new IllegalArgumentException("주문에 상품아이디가 없습니다.");
        }

        orderProductRepository.saveAll(orderProducts);
    }

    @Transactional
    public void updateOrder(AddProductToOrder request, Long orderId) {
        var order = orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("주문 정보가 없습니다. ID : " + orderId)
        );

        var orderProduct = OrderProduct.builder()
                .order(order).productId(request.productId())
                .build();

        orderProductRepository.save(orderProduct);
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(OrderResponse::from)
                .orElseThrow(() -> new EntityNotFoundException("주문 정보가 없습니다. ID : " + orderId));
    }
}
