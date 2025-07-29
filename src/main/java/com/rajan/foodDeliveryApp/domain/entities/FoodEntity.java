package com.rajan.foodDeliveryApp.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "foods")
public class FoodEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "food_id_seq")
    @Column(name = "food_id")
    private Long foodId;

    private String name;

    private String category;

    private BigDecimal price;

    @Column(name = "spice_level")
    private Integer spiceLevel;

    @Column(name = "menu_id")
    private Long menuId;
}
