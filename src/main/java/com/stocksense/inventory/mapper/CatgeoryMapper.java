package com.stocksense.inventory.mapper;

import com.stocksense.inventory.dto.CategoryRequest;
import com.stocksense.inventory.dto.CategoryResponse;
import com.stocksense.inventory.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CatgeoryMapper {

    public Category toEntity(CategoryRequest request){
        Category category = new Category();
        updateEntity(category , request);
        return category;
    }

    public void updateEntity(Category category , CategoryRequest request){
        category.setName(request.name().trim());
        category.setDescription(request.description() == null ? null : request.description().trim());
    }

    public CategoryResponse toResponse(Category category){
        return new CategoryResponse(category.getId(), category.getName(), category.getDescription());
    }

}
