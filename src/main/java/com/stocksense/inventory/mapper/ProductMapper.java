package com.stocksense.inventory.mapper;

import com.stocksense.inventory.dto.ProductRequest;
import com.stocksense.inventory.dto.ProductResponse;
import com.stocksense.inventory.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequest request){
        Product product = new Product();
        updateEntity(product,request);
        return product;
    }

    public void updateEntity(Product product , ProductRequest request){
        product.setName(request.name().trim());
        product.setPrice(request.price());
        product.setQuantity(request.quantity());
    }

    public ProductResponse toResponse(Product product){
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity()
        );
    }
}
