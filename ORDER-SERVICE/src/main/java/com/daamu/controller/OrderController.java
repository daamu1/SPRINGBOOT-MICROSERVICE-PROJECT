package com.daamu.controller;

import com.daamu.DTO.OrderRequest;
import com.daamu.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String placeOrder(@RequestBody OrderRequest orderRequest) throws IllegalAccessException {
        orderService.placedOrder(orderRequest);
        return "Order placed Successfully";
    }
}
