package com.ransankul.clickmart.service;

import java.util.List;

import com.ransankul.clickmart.model.Category;
import com.ransankul.clickmart.model.Product;

public interface ProductService {

    //getproductbyCategory
    public List<Product> getproductbyCategory(Category categortId);

    //getProductBYID
    public Product getProductById(int productId);
    
    //getAllProduct
    public List<Product> getAllProduct();

    //searchProductByName
    public List<Product> searchProductByName(String name);
    
}
