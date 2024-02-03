package com.kerro.store.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "order_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products = new ArrayList<>();

    @Column(name = "totalprice")
    private BigDecimal totalPrice;

    @Column(name = "createdat")
    private LocalDateTime createdAt;

    @Column(name = "updatedat")
    private LocalDateTime updatedAt;

    @Column(name ="quantity")
    private int quantityt;

    @Column(name = "status")
    private EStatus status;

    @Column(name = "shippingaddress", length = 255, nullable = true)
    private String shippingAddress;

    @Column(name = "paymentmethod", length = 255,nullable = false)
    private String paymentMethod;

    @Column(name = "paymentstatus")
    private EStatus paymentStatus;

    @Column(name = "shippingstatus")
    protected EStatus shippingStauts;


}
