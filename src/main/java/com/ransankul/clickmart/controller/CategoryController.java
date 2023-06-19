package com.ransankul.clickmart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ransankul.clickmart.exception.ResourceNotFoundException;
import com.ransankul.clickmart.model.Category;
import com.ransankul.clickmart.service.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public List<Category> getAllCategory() {
        return categoryService.getAllCategory();
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategoryByID(@PathVariable int categoryId) {
        Category category = categoryService.getCategoryByID(categoryId);
        if (category != null) {
            return ResponseEntity.ok(category);
        } else {
            throw new ResourceNotFoundException("No category found with this ID" + categoryId);
        }
    }

    @GetMapping("/search")
    public List<Category> searchCategory(@RequestParam("name") String searchCategoryName) {
        List<Category> list =  categoryService.searchCategory(searchCategoryName);
        if(list.size()>0) return list;
        else throw new ResourceNotFoundException("No category found with this name" + searchCategoryName);
    }
    
}
