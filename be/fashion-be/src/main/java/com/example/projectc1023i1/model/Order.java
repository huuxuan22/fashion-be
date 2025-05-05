package com.example.projectc1023i1.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private long orderId;
    @Column(name = "order_code")
    private String orderCode;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;
    @Column(name = "payment_type")
    private String paymentType;
    private Double total;
    private LocalDateTime orderDate;
    private String note;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
}
