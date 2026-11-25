package com.stocksense.inventory.repository;

import com.stocksense.inventory.model.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product , Long> {

    @EntityGraph(attributePaths = "category")
    List<Product> findByQuantityLessThan(int quantity);

    @EntityGraph(attributePaths = "category")
    List<Product> findByNameContainingIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name , Long id);

    boolean existsByCategoryId(Long categoryId);

    @Override
    @EntityGraph(attributePaths = "category")
    List<Product> findAll();

    @EntityGraph(attributePaths = "category")
    List<Product> findByCategoryId(Long id);
}
