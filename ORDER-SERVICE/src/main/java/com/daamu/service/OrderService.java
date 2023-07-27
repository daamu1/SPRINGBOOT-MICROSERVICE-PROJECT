package com.daamu.service;

import com.daamu.DTO.OrderLineItemDto;
import com.daamu.DTO.OrderRequest;
import com.daamu.model.Order;
import com.daamu.model.OrderLineItems;
import com.daamu.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderService {
    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final WebClient webClient;
    public void placedOrder(OrderRequest orderRequest) throws IllegalAccessException {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
       List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemList()
                .stream()
                .map(this::mapToDto)
                .toList();
       order.setOrderLineItemsList(orderLineItemsList);
//       call inventory service and place order if product is into stock
       boolean result= webClient.get()
                .uri("http://localhost:8083/api/inventrory")
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
       if(result) {
           orderRepository.save(order);
           log.info("{} Order successfully ", order.getOrderNumber());
       }
       else
       {
           throw new IllegalAccessException("Product is  not in stock please try again later");
       }
    }

    private OrderLineItems mapToDto(OrderLineItemDto orderLineItemDto) {
        OrderLineItems orderLineItems =new OrderLineItems();
        orderLineItems.setPrice(orderLineItemDto.getPrice());
        orderLineItems.setQuantity(orderLineItemDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemDto.getSkuCode());
        return orderLineItems;
    }
}
