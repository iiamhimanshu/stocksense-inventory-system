package com.stocksense.inventory.controller;

import com.stocksense.inventory.model.Product;
import com.stocksense.inventory.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAll(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id){
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product){
        Product saved = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id , @RequestBody Product product){
        return productService.updateProduct(id,product)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        if(productService.deleteProduct(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/low-stock")
    public List<Product> getLowStockProduct(){
        return productService.lowStockProduct();
    }

}
