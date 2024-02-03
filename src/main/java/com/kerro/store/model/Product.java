package com.kerro.store.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Product")
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @SequenceGenerator(name = "product_sequence", sequenceName = "product_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")

    private Long id;


    @Column(name = "name", length = 255, nullable = false)
    private  String name;


    @Column(name = "price", length = 255, nullable = false)
    private String price;

    @Column(name = "description", length = 255, nullable = true)
    private String description;


    @Column(name = "stock")
    private int stock;

    @Column(name = "image_url", length = 255, nullable = true)
    private String imageUrl;

    @ManyToMany(mappedBy = "products")
    private List<Cart> carts = new ArrayList<>();

    @ManyToMany(mappedBy = "products")
    private List<Order> orders = new ArrayList<>();

    public Product(Long id, String name, String price, String description, int stock, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.imageUrl = imageUrl;
    }

    public Product(String name, String price, String description, int stock,String imageUrl) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.imageUrl = imageUrl;
    }
}
