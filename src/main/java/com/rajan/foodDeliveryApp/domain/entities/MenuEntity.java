package com.rajan.foodDeliveryApp.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "menus")
public class MenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_id_seq")
    @Column(name = "menu_id")
    private Long menuId;

    private String name;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private RestaurantEntity restaurant;

    @OneToMany(mappedBy = "menuId", cascade = CascadeType.ALL)
    private List<FoodEntity> foods;

}