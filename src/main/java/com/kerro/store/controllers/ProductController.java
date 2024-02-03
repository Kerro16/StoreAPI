package com.kerro.store.controllers;

import com.kerro.store.exceptions.ProductAlreadyExistsException;
import com.kerro.store.model.Product;
import com.kerro.store.repository.ProductRepository;
import com.kerro.store.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", allowedHeaders = "true")
@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<String> addNewProduct(@Validated @RequestBody Product product) {

        try{
            if(product.getName() == null || product.getName().isEmpty()){
                throw  new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            productService.addNewProduct(product);
            return ResponseEntity.ok("Product registeres succesfully");

        } catch (ProductAlreadyExistsException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (ResponseStatusException e){
            throw  e;
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to register product", e);
        }

    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteProduct(@RequestParam Long id){

        productService.deleteProductById(id);

        return ResponseEntity.ok("Product deleted successfully");
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateProduct(@Validated @RequestBody Product product){
        productService.UpdateProduct(product);
        return ResponseEntity.ok("Product updated successfully");
    }

}
