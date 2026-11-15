package com.stocksense.inventory.controller;

import com.stocksense.inventory.dto.ProductRequest;
import com.stocksense.inventory.dto.ProductResponse;
import com.stocksense.inventory.model.Product;
import com.stocksense.inventory.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponse> getAll(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponse getById(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest request){
        ProductResponse created= productService.createProduct(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable Long id , @Valid @RequestBody ProductRequest request){
        return productService.updateProduct(id,request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/low-stock")
    public List<ProductResponse> getLowStockProduct(){
        return productService.lowStockProduct();
    }

    @GetMapping("/search")
    public List<ProductResponse> getByName(@RequestParam String name){
        return productService.searchByName(name);
    }
}
