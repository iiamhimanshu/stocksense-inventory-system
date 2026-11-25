package com.stocksense.inventory.service;

import com.stocksense.inventory.dto.CategoryRequest;
import com.stocksense.inventory.dto.CategoryResponse;
import com.stocksense.inventory.exception.ConflictException;
import com.stocksense.inventory.exception.ResourceNotFoundException;
import com.stocksense.inventory.mapper.CatgeoryMapper;
import com.stocksense.inventory.model.Category;
import com.stocksense.inventory.repository.CategoryRepository;
import com.stocksense.inventory.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CatgeoryMapper mapper;

    public CategoryService(CategoryRepository categoryRepository , ProductRepository productRepository , CatgeoryMapper mapper){
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    public List<CategoryResponse> getAllCategories(){
        return categoryRepository.findAll().stream().map(mapper::toResponse).toList();
    }

    public CategoryResponse getCategoryById(Long id){
        return mapper.toResponse(findCategoryOrThrow(id));
    }

    @Transactional
    public CategoryResponse createCategory(CategoryRequest request){
        String name = request.name().trim();
        if(categoryRepository.existsByNameIgnoreCase(name)){
            throw new ConflictException("A category named '"+name+"' already exists");
        }
        return mapper.toResponse(categoryRepository.save(mapper.toEntity(request)));
    }

    @Transactional
    public CategoryResponse updateCategory(Long id , CategoryRequest request){
        Category category = findCategoryOrThrow(id);
        String name = request.name().trim();
        if(categoryRepository.existsByNameIgnoreCaseAndIdNot(name , id)){
            throw new ConflictException("A category named '"+name+"' already exists");
        }

        mapper.updateEntity(category,request);
        return mapper.toResponse(category);
    }

    @Transactional
    public void deleteCategory(Long id){
        Category category = findCategoryOrThrow(id);
        if(productRepository.existsByCategoryId(id)){
            throw new ConflictException("Cannot delete category '"+category.getName()+"' because products still use it");
        }
        categoryRepository.delete(category);
    }

    private Category findCategoryOrThrow(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category" , id));
    }
}
