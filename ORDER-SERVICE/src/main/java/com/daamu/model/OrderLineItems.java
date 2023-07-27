package com.daamu.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Table(name = "_orderLine_item")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderLineItems {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_line_item_id")
    Long orderLineItemId;
    @Column(name = "sku_code")
    String skuCode;
    @Column(name = "price")
    BigDecimal price;
    @Column(name = "quantity")
    Integer quantity;
}
