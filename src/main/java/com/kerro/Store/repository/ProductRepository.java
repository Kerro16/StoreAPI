package com.kerro.Store.repository;

import com.kerro.Store.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {


    @Query("SELECT p.name FROM Product p WHERE p.name IN :names")
    List<String> findExistingNames(@Param("names") List<String> names);




}

