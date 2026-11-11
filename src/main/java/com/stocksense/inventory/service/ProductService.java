package com.stocksense.inventory.service;

import com.stocksense.inventory.model.Product;
import com.stocksense.inventory.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }

    public Product createProduct(Product product){
            product.setId(null);  // ignoring any id set by the client
            return productRepository.save(product);
    }

    public Optional<Product> updateProduct(Long id , Product product){
        return productRepository.findById(id).map(existing -> {
            existing.setName(product.getName());
            existing.setPrice(product.getPrice());
            existing.setQuantity(product.getQuantity());
            return productRepository.save(existing);
        });
    }

    public boolean deleteProduct(Long id){
        if(!productRepository.existsById(id)){
            return false;
        }
        productRepository.deleteById(id);
        return true;
    }

    public List<Product> lowStockProduct(){
        return productRepository.findByQuantityLessThan(10);
    }

    public List<Product> searchByName(String name){
        return productRepository.findByNameContainingIgnoreCase(name);
    }

}
