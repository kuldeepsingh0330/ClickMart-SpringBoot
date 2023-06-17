package com.ransankul.clickmart.repositery;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ransankul.clickmart.model.Category;
import com.ransankul.clickmart.model.Product;

public interface ProductRepositery extends JpaRepository<Product,Integer>{

    public List<Product> findBycategory(Category category);

    public List<Product> findBynameContainingIgnoreCase(String name);
    
}
