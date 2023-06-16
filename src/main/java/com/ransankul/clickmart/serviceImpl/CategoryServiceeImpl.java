package com.ransankul.clickmart.serviceImpl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ransankul.clickmart.model.Category;
import com.ransankul.clickmart.repositery.CategoryRepositery;
import com.ransankul.clickmart.service.CategoryService;

@Service
public class CategoryServiceeImpl implements CategoryService {

    @Autowired
    private CategoryRepositery categoryRepositery;
    
    @Override
    public List<Category> getAllCategory() {
        return categoryRepositery.findAll();
    }

    @Override
    public Category getCategoryByID(int categoryId) {
        return categoryRepositery.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("Category not found with ID: " + categoryId));
    }

    @Override
    public List<Category> searchCategory(String searchCategoryName) {
        return categoryRepositery.findBynameContainingIgnoreCase(searchCategoryName);
    }
    
}
