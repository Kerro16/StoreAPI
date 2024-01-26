package com.kerro.Store.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/product")
public class ProductController {

    private final ProductService productService;
@Autowired
    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addNewProducts(@Validated @RequestBody List<Product> productList) {
        productService.addNewProducts(productList);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/remove")
    public ResponseEntity<List<Product>> removeProducts(@Validated @RequestBody List<Product> productList) {
        List<Product> removedProducts = productService.removeProducts(productList);
        return ResponseEntity.ok(removedProducts);
    }

    @PostMapping("/update")
    public ResponseEntity<List<Product>> updateProducts(@Validated @RequestBody List<Product> productList) {
        List<Product> updatedProducts = productService.updateProducts(productList);
        return ResponseEntity.ok(updatedProducts);
    }

}
