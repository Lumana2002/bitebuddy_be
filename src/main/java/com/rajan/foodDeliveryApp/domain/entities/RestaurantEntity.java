package com.rajan.foodDeliveryApp.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "restaurants")
public class RestaurantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_id_seq")
    @Column(name = "restaurant_id")
    private Long restaurantId;
    private String name;
    private String email;
    private String cuisine;
    private String contact;
    private String address;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String status;
    @Column(name = "image")
    private String image;
    private Double averagePrice;
}
