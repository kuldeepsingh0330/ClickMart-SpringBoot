package com.ransankul.clickmart.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ransankul.clickmart.model.Category;
import com.ransankul.clickmart.model.Product;
import com.ransankul.clickmart.service.AdminService;
import com.ransankul.clickmart.repositery.CategoryRepositery;
import com.ransankul.clickmart.repositery.ProductRepositery;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private CategoryRepositery categoryRepositery;
	
	@Autowired
	private ProductRepositery productRepositery;

	@Override
	public Category addCategory(Category category) {
        if (categoryRepositery.existsByName(category.getName())) {
            throw new IllegalArgumentException("Category with the same name already exists");
        }else {
        	return categoryRepositery.save(category);        	
        }	
	}

	@Override
	public void removeCategory(int categoryId) {
		
		Category cat = categoryRepositery.findById(categoryId).get();
		
		
        if (cat == null) {
            throw new IllegalArgumentException("Category does not exist");
        }else {
        	categoryRepositery.delete(cat);;        	
        }
		
	}

	@Override
	public void changeCategoryVisibility(int categoryId, boolean visible) {
		Category cat = categoryRepositery.findById(categoryId).get();
		
        if (cat == null) {
            throw new IllegalArgumentException("Category does not exist");
        }else {
        	cat.setPublic(visible);
        	categoryRepositery.save(cat);        	
        }	
	}
	
	@Override
    public Product addProduct(Product product) {
		
		return productRepositery.save(product);
		
    }

	@Override
    public void removeProduct(int productId) {
		
		Product product = productRepositery.findById(productId).get();
		
		
        if (product == null) {
            throw new IllegalArgumentException("Product does not exist");
        }else {
        	productRepositery.delete(product);        	
        }
		
    }

	@Override
    public Product updateProduct(int productId, Product updatedProduct) {
		
		Product product = productRepositery.findById(productId).get();
		
        if (product == null) {
            throw new IllegalArgumentException("Product does not exist");
        }else {
        	return productRepositery.save(product);      	
        }
    }

}
