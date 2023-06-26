package com.ransankul.clickmart.service;

import com.ransankul.clickmart.model.Category;
import com.ransankul.clickmart.model.Product;

public interface AdminService {
	
	public Category addCategory(Category category);
	
	public void removeCategory(int categoryId);
	
	public void changeCategoryVisibility(int categoryId, boolean visible);
	
	public Product addProduct(Product product);
	
	public void removeProduct(int productId);
	
	public Product updateProduct(int productId, Product updatedProduct);

}
