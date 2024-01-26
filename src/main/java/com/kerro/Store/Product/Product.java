package com.kerro.Store.Product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public Product(Long id, String name, String price, String description, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
    }

    public Product(String name, String price, String description, int stock) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
    }
}
