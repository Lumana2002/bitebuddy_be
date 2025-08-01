package com.rajan.foodDeliveryApp.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private RestaurantEntity restaurant;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "partner_id")
    private DeliveryPartnerEntity partner;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetailEntity> orderDetails;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "average_price")
    private BigDecimal averagePrice;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "special_instructions")
    private String specialInstructions;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "cancellation_reason")
    private String cancellationReason;

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;

    @Column(name = "notes")
    private String notes;

    @Column(name= "weather")
    private String weather;
}