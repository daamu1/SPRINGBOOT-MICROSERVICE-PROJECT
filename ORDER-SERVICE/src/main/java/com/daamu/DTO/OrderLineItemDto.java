package com.daamu.DTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderLineItemDto {
    Long orderLineItemId;
    String skuCode;
    BigDecimal price;
    Integer quantity;
}
