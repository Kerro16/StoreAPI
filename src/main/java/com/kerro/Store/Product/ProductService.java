package com.kerro.Store.Product;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    public List<Product> addNewProducts(List<Product> productList) {
        List<Product> addedProducts = new ArrayList<>();

        // Verificar la existencia de productos por nombre en un solo lote
        List<String> productNames = productList.stream().map(Product::getName).collect(Collectors.toList());
        List<String> existingNames = productRepository.findExistingNames(productNames);
        if (!existingNames.isEmpty()) {
            throw new DuplicateKeyException("Products already exist: " + existingNames);
        }

        // Guardar productos
        addedProducts.addAll(productRepository.saveAll(productList));

        return addedProducts;
    }


    public List<Product> removeProducts(List<Product> productList) {
        List<Product> removedProducts = new ArrayList<>();

        for (Product product : productList) {
            Optional<Product> productOptional = productRepository.findById(product.getId());
            productOptional.ifPresent(existingProduct -> {
                productRepository.delete(existingProduct);
                removedProducts.add(existingProduct);
            });
        }

        if (removedProducts.isEmpty()) {
            throw new IllegalStateException("No products were found for removal.");
        }

        return removedProducts;
    }


    public List<Product> updateProducts(List<Product> productList) {
        List<Product> updatedProducts = new ArrayList<>();

        for (Product product : productList) {
            Optional<Product> existingProductOptional = productRepository.findById(product.getId());
            existingProductOptional.ifPresent(existingProduct -> {
                // Actualizar campos relevantes del producto existente
                existingProduct.setName(product.getName());
                existingProduct.setPrice(product.getPrice());
                // ... (actualizar otros campos según sea necesario)
                updatedProducts.add(existingProduct);
            });
        }

        if (updatedProducts.isEmpty()) {
            throw new IllegalStateException("No products were found for update.");
        }

        return updatedProducts;
    }

}
