package com.weihan.ecommerce_backend.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "receiver", nullable = false, length = 20)
    private String receiver;

    @Column(name = "receiver_contact", nullable = false, length = 20)
    private String receiverContact;

    @Column(name = "receiver_address", nullable = false)
    private String receiverAddress;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "total_quantity", nullable = false)
    private Integer totalQuantity;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @CreationTimestamp
    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}