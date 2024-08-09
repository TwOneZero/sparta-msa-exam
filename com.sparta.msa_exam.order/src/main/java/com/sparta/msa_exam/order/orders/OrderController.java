package com.sparta.msa_exam.order.orders;


import com.sparta.msa_exam.order.orders.dto.AddProductToOrder;
import com.sparta.msa_exam.order.orders.dto.CreateOrder;
import com.sparta.msa_exam.order.orders.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;


    @PostMapping
    public void create(@RequestBody CreateOrder request) {
        orderService.createOrder(request);
    }

    @PutMapping("/{orderId}")
    public void update(
            @RequestBody AddProductToOrder request,
            @PathVariable(name = "orderId") Long orderId) {
        orderService.updateOrder(request, orderId);
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse get(@PathVariable(name = "orderId") Long orderId) {
        return orderService.getOrder(orderId);
    }
}
