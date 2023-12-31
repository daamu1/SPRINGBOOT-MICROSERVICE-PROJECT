package com.daamu.service;

import com.daamu.DTO.InventoeryResponse;
import com.daamu.DTO.OrderLineItemDto;
import com.daamu.DTO.OrderRequest;
import com.daamu.event.OrderPlacedEvent;
import com.daamu.model.Order;
import com.daamu.model.OrderLineItems;
import com.daamu.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
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
    private final WebClient.Builder webClientBuilder;
    @Autowired
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public String placedOrder(OrderRequest orderRequest) throws IllegalAccessException {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemList().stream().map(this::mapToDto).toList();
        order.setOrderLineItemsList(orderLineItemsList);
        List<String> skuCodes = order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode)
               .toList();
//       call inventory service and place order if product is into stock
        InventoeryResponse[] inventoeryResponses = webClientBuilder.build().get()
                .uri("http://INVENTORY-SERVICE/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes) // Corrected parameter name here
                                .build())
                .retrieve()
                .bodyToMono(InventoeryResponse[].class)
                .block();

        boolean allProductInStock= Arrays.stream(inventoeryResponses).allMatch(InventoeryResponse::isInStock);
       if(allProductInStock) {
           orderRepository.save(order);
           OrderPlacedEvent orderPlacedEvent=new OrderPlacedEvent(order.getOrderNumber());
           log.info("---------------------->" + orderPlacedEvent.getOrderNumber());
           kafkaTemplate.send("notificationTopic", orderPlacedEvent);
           return "Order Placed Successfully";
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
