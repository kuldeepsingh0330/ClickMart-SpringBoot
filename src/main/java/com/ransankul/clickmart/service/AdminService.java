package com.ransankul.clickmart.service;

import java.util.List;

import com.ransankul.clickmart.model.Category;
import com.ransankul.clickmart.model.Product;
import com.ransankul.clickmart.model.Transaction;

public interface AdminService {
	
	public Category addCategory(Category category);
	
	public Category updateCategory(Category category);

	public List<Category> getCategory(int pageNumber);
	
	public void removeCategory(int categoryId);
	
	public void changeCategoryVisibility(int categoryId);

	public List<Product> getProduct(int pageNumber);
	
	public Product addProduct(Product product);

	public Product getProductById(int id);
	
	public void removeProduct(int productId);
	
	public Product updateProduct(Product updatedProduct);

	public List<Transaction> getAllTransaction(String pageNumber);
	

	public Category getCategoryByID(int categoryId);
	
	public long getCount();
	public long getCountPublic();
	public long getCountPrivate();


	public long getCountProduct();
	public long getCountoutOfStockProduct();
	public List<Object[]> getAllCategoryName();
	public Product getProductByID(int categoryId);
	public boolean changeProductAvailibality(int productId);
	public boolean addMoreProductQuantity(int productId,int quantity);

}
