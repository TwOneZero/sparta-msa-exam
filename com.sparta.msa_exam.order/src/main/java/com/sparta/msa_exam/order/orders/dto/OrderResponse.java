package com.sparta.msa_exam.order.orders.dto;

import com.sparta.msa_exam.order.core.Order;
import com.sparta.msa_exam.order.core.OrderProduct;

import java.util.List;

public record OrderResponse(
        Long orderId,
        List<Integer> productIds
) {

    public static OrderResponse from(Order entity) {
        return new OrderResponse(
                entity.getOrderId(),
                entity.getProductIds().stream()
                        .map(OrderProduct::getProductId)
                        .map(Long::intValue)
                        .toList()
        );
    }
}
