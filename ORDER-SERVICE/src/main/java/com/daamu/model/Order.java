package com.daamu.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    Long orderId;
    @Column(name = "order_number")
    String orderNumber;
    @OneToMany(cascade = CascadeType.ALL)
    List<OrderLineItems> orderLineItemsList;

}
