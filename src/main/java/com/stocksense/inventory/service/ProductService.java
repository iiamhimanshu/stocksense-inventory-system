package com.stocksense.inventory.service;

import com.stocksense.inventory.dto.ProductRequest;
import com.stocksense.inventory.dto.ProductResponse;
import com.stocksense.inventory.exception.ConflictException;
import com.stocksense.inventory.exception.ResourceNotFoundException;
import com.stocksense.inventory.mapper.ProductMapper;
import com.stocksense.inventory.model.Category;
import com.stocksense.inventory.model.Product;
import com.stocksense.inventory.repository.CategoryRepository;
import com.stocksense.inventory.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private static final int LOW_STOCK_THRESHOLD = 10;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository , CategoryRepository categoryRepository , ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    public List<ProductResponse> getAllProducts(){
        return productRepository.findAll().stream()
                .map(productMapper::toResponse)
                .toList();
    }

    public ProductResponse getProductById(Long id){
        return productMapper.toResponse(findProductOrthrow(id));
    }

    @Transactional
    public ProductResponse createProduct(ProductRequest request){
           String name = request.name().trim();
           if(productRepository.existsByNameIgnoreCase(name)){
               throw new ConflictException("A product named '"+name+"' already exists");
           }
           Product product = productMapper.toEntity(request);
           product.setCategory(findCategoryOrThrow(request.categoryId()));
           return productMapper.toResponse(productRepository.save(product));
    }

    @Transactional
    public ProductResponse updateProduct(Long id , ProductRequest request){
        Product product = findProductOrthrow(id);
        String name = request.name().trim();
        if(productRepository.existsByNameIgnoreCaseAndIdNot(name,id)){
            throw new ConflictException("A product named '"+name+"' already exists");
        }
        productMapper.updateEntity(product , request);
        product.setCategory(findCategoryOrThrow(request.categoryId()));
        return productMapper.toResponse(product);
    }

    @Transactional
    public void deleteProduct(Long id){
        if(!productRepository.existsById(id)){
            throw new ResourceNotFoundException("Product",id);
        }
        productRepository.deleteById(id);
    }

    public List<ProductResponse> lowStockProduct(){
        return productRepository.findByQuantityLessThan(LOW_STOCK_THRESHOLD)
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    public List<ProductResponse> searchByName(String name){
        return productRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    private Product findProductOrthrow(Long id){
        return productRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Product",id));
    }

    private Category findCategoryOrThrow(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("category",id));
    }

    public List<ProductResponse> getProductsByCategory(Long id){
        if(!categoryRepository.existsById(id)){
            throw new ResourceNotFoundException("Category",id);
        }
        return productRepository.findByCategoryId(id)
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }


}
