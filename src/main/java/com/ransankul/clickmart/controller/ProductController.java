package com.ransankul.clickmart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        return productService.getproductbyCategory(category);
    }

    @GetMapping("getProductById/{productId}")
    public Product getProductById(@PathVariable int productId) {
        return productService.getProductById(productId);
    }

    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return productService.getAllProduct();
    }
    
    @GetMapping("/search")
    public List<Product> searchProductByName(@RequestParam String name) {
        return productService.searchProductByName(name);
    }
}