package org.example.shopapp.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@Entity
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name = "full_name", length = 255)
    private String fullName;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "note", length = 255)
    private String note;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "total_price")
    private Float totalPrice;

    @Column(name = "shipping_method", length = 50)
    private String shippingMethod;

    @Column(name = "shipping_address", length = 255)
    private String shippingAddress;

    @Column(name = "shipping_date")
    private LocalDateTime shippingDate;

    @Column(name = "tracking_number", length = 50)
    private String trackingNumber;

    @Column(name = "payment_method", length = 50)
    private String paymentMethod;

    @Column(name = "active")
    private boolean active;
}
