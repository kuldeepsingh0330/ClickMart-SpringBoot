package com.ransankul.clickmart.repositery;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ransankul.clickmart.model.Category;
import com.ransankul.clickmart.model.Product;

public interface ProductRepositery extends JpaRepository<Product,Integer>{

    public List<Product> findBycategory(Category category);

    public List<Product> findBynameContainingIgnoreCase(String name,Pageable p);
    
    @Query("SELECT p.images FROM Product p WHERE p.productId = :productId")
    List<String> getImagesByProductId(int productId);

    long count();
    
    long countByIsAvailableFalse(); 
    
    
    
}
