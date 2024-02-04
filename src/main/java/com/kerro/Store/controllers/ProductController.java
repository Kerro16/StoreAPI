package com.kerro.Store.controllers;

import com.kerro.Store.exceptions.ProductAlreadyExistsException;
import com.kerro.Store.exceptions.ProductNotFoundException;
import com.kerro.Store.model.Product;
import com.kerro.Store.model.User;
import com.kerro.Store.repository.ProductRepository;
import com.kerro.Store.response.MessageResponse;
import com.kerro.Store.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", allowedHeaders = {"Origin", "X-Requested-With", "Content-Type", "Accept"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @GetMapping("/getall")
    public List<Product> getAllProducts(){return productService.getAllProducts();}

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



        return ResponseEntity.ok("Product deleted successfully");
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateProduct(@Validated @RequestBody Product product){
        productService.UpdateProduct(product);
        return ResponseEntity.ok("Product updated successfully");
    }

    @GetMapping("/get")
    public ResponseEntity<Object> getProduct(@RequestParam Long id){
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (ProductNotFoundException e ){
            return  ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }


    }

}
