package com.kerro.store.services;

import com.kerro.store.exceptions.ProductAlreadyExistsException;
import com.kerro.store.exceptions.ProductNotFoundException;
import com.kerro.store.model.Product;
import com.kerro.store.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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



    public Product addNewProduct(Product product) {
        try{

            validateProductDoesNotExist(product.getName());

            return productRepository.save(product);
        } catch (DataIntegrityViolationException e){
            throw new IllegalStateException("Error while added a new product: " + e.getMessage(), e);
        } catch (Exception e){
            throw new IllegalStateException("Error while proccessing the request: " + e.getMessage(), e);
        }


    }

    public void deleteProductById(Long id){
        Optional<Product> productOptional = productRepository.findById(id);

        if(productOptional.isPresent()){
            productRepository.delete(productOptional.get());
        } else {
            throw new ProductNotFoundException("Product not found with ID: " + id);
        }
    }


    public void UpdateProduct(Product product){

        if(product.getId() == null) {
            throw new IllegalArgumentException("Invalid ID");
        }

        if(!productRepository.existsById(product.getId())){
            throw new EntityNotFoundException("Cannot found a product with ID provided");
        }

        productRepository.save(product);
    }

    private void validateProductDoesNotExist(String productName) {
        if (productRepository.findByName(productName).isPresent()) {
            throw new ProductAlreadyExistsException("Product with name " + productName + " already exists");
        }
    }



}
