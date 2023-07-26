package com.ransankul.clickmart.service;

import java.util.List;

import com.ransankul.clickmart.model.Category;
import com.ransankul.clickmart.model.Product;
import com.ransankul.clickmart.model.Transaction;

public interface AdminService {
	
	public Category addCategory(Category category);

	public List<Category> getCategory(int pageNumber);
	
	public void removeCategory(int categoryId);
	
	public void changeCategoryVisibility(int categoryId, boolean visible);

	public List<Product> getProduct(int pageNumber);
	
	public Product addProduct(Product product);
	
	public void removeProduct(int productId);
	
	public Product updateProduct(int productId, Product updatedProduct);

	public List<Transaction> getAllTransaction(String pageNumber);
	
	public long getCount();
	public long getCountPublic();
	public long getCountPrivate();

}
