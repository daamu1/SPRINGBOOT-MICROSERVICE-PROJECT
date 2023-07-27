package com.daamu.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "_product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    UUID productId;
    @Column(name = "product_name")
    String productName;
    @Column(name = "product_description", length = 1000)
    String productDescription;
    @Column(name = "price", length = 500)
    BigDecimal price;
}