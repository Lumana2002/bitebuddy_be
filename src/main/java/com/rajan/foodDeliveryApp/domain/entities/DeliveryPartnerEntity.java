package com.rajan.foodDeliveryApp.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "delivery_partner")
public class DeliveryPartnerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partner_id_seq")
    @Column(name = "partner_id")
    private Long id;

    private String name;
}
