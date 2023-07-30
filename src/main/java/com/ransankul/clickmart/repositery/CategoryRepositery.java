package com.ransankul.clickmart.repositery;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ransankul.clickmart.model.Category;

public interface CategoryRepositery extends JpaRepository<Category, Integer>{

    public List<Category> findBynameContainingIgnoreCase(String name);
    
    public boolean existsByName(String name);
    
    @Query("SELECT e FROM Category e WHERE e.isPublic = true ORDER BY e.priority")
    public List<Category> getAllCategoryUser();


    @Query("SELECT c.name, c.categoryId FROM Category c")
    public List<Object[]> getAllCategoryNameandId();
    
    long count();
    
    long countByIsPublicTrue(); 
    
    long countByIsPublicFalse(); 
    
}
