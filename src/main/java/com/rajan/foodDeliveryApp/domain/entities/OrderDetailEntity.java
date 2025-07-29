package com.rajan.foodDeliveryApp.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_details")
public class OrderDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_details_id_seq")
    private Long id;

    @JoinColumn(name = "order_id")
    private Long orderId;

    @JoinColumn(name = "food_id")
    private Long foodId;

    private String foodName;

    private BigDecimal quantity;

    private BigDecimal price;

    @Column(name = "total_price")
    private Integer totalPrice;
}
