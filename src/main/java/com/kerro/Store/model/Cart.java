package com.kerro.Store.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
@Getter
@Setter
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "cart_products",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products = new ArrayList<>();

    @Column(name = "totalprice")
    private BigDecimal totalPrice;

    @Column(name = "createdat")
    private LocalDateTime createdAt;

    @Column(name = "Updatedat")
    private LocalDateTime updatedAt;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "status")
    private EStatus status;

    @Column(name = "ShippingAddres", length = 255, nullable = true)
    private String shippingAddress;

    public Cart(Long id, User user, List<Product> products, BigDecimal totalPrice, LocalDateTime createdAt, LocalDateTime updatedAt, int quantity, EStatus status, String shippingAddress) {
        this.id = id;
        this.user = user;
        this.products = products;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.quantity = quantity;
        this.status = status;
        this.shippingAddress = shippingAddress;
    }

    public Cart(User user, List<Product> products, BigDecimal totalPrice, LocalDateTime createdAt, LocalDateTime updatedAt, int quantity, EStatus status, String shippingAddress) {
        this.user = user;
        this.products = products;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.quantity = quantity;
        this.status = status;
        this.shippingAddress = shippingAddress;
    }
}
