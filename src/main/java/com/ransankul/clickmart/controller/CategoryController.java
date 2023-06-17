package com.ransankul.clickmart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ransankul.clickmart.model.Category;
import com.ransankul.clickmart.service.CategoryService;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public List<Category> getAllCategory() {
        return categoryService.getAllCategory();
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<Category> getCategoryByID(@PathVariable int categoryId) {
        Category category = categoryService.getCategoryByID(categoryId);
        if (category != null) {
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/categories/search")
    public List<Category> searchCategory(@RequestParam("name") String searchCategoryName) {
        return categoryService.searchCategory(searchCategoryName);
    }
    
}
