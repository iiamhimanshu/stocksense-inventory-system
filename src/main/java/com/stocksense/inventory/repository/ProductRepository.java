package com.stocksense.inventory.repository;

import com.stocksense.inventory.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product , Long> {

    List<Product> findByQuantityLessThan(int quantity);

    List<Product> findByNameContainingIgnoreCase(String name);


}
