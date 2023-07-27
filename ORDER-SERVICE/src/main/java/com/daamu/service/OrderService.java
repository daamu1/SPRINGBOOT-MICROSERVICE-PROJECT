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

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    @Autowired
    private final OrderRepository orderRepository;
    public void placedOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
       List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemList()
                .stream()
                .map(this::mapToDto)
                .toList();
       order.setOrderLineItemsList(orderLineItemsList);
       orderRepository.save(order);
       log.info("{} Order successfully ",order.getOrderNumber());

    }

    private OrderLineItems mapToDto(OrderLineItemDto orderLineItemDto) {
        OrderLineItems orderLineItems =new OrderLineItems();
        orderLineItems.setPrice(orderLineItemDto.getPrice());
        orderLineItems.setQuantity(orderLineItemDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemDto.getSkuCode());
        return orderLineItems;
    }
}
