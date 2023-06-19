package com.ransankul.clickmart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ransankul.clickmart.exception.ResourceNotFoundException;
import com.ransankul.clickmart.model.Category;
import com.ransankul.clickmart.model.Product;
import com.ransankul.clickmart.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
    
    
    @Autowired
    private ProductService productService;

    @GetMapping("getProductsByCategory/{categoryId}")
    public List<Product> getProductsByCategory(@PathVariable int categoryId) {
        Category category = new Category();
        category.setId(categoryId);
        List<Product> list =  productService.getproductbyCategory(category);
        if(list.size()>0) return list;
        else 
            throw new ResourceNotFoundException("No product found in this category" + categoryId);
    }

    @GetMapping("getProductById/{productId}")
    public Product getProductById(@PathVariable int productId) {
        Product product =  productService.getProductById(productId);
        if(product != null) return product;
        else 
            throw new ResourceNotFoundException("No product found with this ID" + productId);
    }

    @GetMapping("/all")
    public List<Product> getAllProducts() {
        List<Product> list =  productService.getAllProduct();
        if(list.size()>0) return list;
        else 
            throw new ResourceNotFoundException("No product found");
    }
    
    @GetMapping("/search")
    public List<Product> searchProductByName(@RequestParam String name) {
        List<Product> list =  productService.searchProductByName(name);
        if(list.size()>0) return list;
        else 
            throw new ResourceNotFoundException("No product found");
    }
}