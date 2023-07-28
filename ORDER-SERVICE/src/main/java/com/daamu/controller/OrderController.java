package com.daamu.controller;

import com.daamu.DTO.OrderRequest;
import com.daamu.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @CircuitBreaker(name = "inventory" ,fallbackMethod ="fallbackMethod" )
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest) throws IllegalAccessException {
        return  CompletableFuture.supplyAsync(()-> {
            try {
                return orderService.placedOrder(orderRequest);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });

    }
    public  CompletableFuture<String> fallbackMethod(OrderRequest orderRequest,RuntimeException runtimeException)
    {
        return CompletableFuture.supplyAsync(()->"Oops ! Something went wrong please order after some time");
    }
}
