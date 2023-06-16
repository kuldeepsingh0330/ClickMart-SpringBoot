package com.ransankul.clickmart.repositery;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ransankul.clickmart.model.Category;

public interface CategoryRepositery extends JpaRepository<Category, Integer>{

    public List<Category> findBynameContainingIgnoreCase(String name);
    
}
