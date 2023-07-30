package com.ransankul.clickmart.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ransankul.clickmart.model.Category;
import com.ransankul.clickmart.model.Product;
import com.ransankul.clickmart.model.Transaction;
import com.ransankul.clickmart.service.AdminService;
import com.ransankul.clickmart.repositery.CategoryRepositery;
import com.ransankul.clickmart.repositery.ProductRepositery;
import com.ransankul.clickmart.repositery.TransactionRepositery;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private CategoryRepositery categoryRepositery;
	
	@Autowired
	private ProductRepositery productRepositery;

	@Autowired
	private TransactionRepositery transactionRepositery;

	@Override
	public List<Category> getCategory(int pageNumber) {
		Pageable p = PageRequest.of(pageNumber,20);
		return categoryRepositery.findAll(p).getContent();
	}

	@Override
	public Category addCategory(Category category) {
        if (categoryRepositery.existsByName(category.getName())) {
            throw new IllegalArgumentException("Category with the same name already exists");
        }else {
        	category.setCreatedAt(System.currentTimeMillis());
        	category.setLastUpdate(System.currentTimeMillis());
        	category.setId(0);
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
	public void changeCategoryVisibility(int categoryId) {
		Category cat = categoryRepositery.findById(categoryId).get();
		
        if (cat == null) {
            throw new IllegalArgumentException("Category does not exist");
        }else {
        	cat.setIsPublic(!cat.getIsPublic());
        	categoryRepositery.save(cat);        	
        }	
	}

	@Override
	public List<Product> getProduct(int pageNumber) {
		Pageable p = PageRequest.of(pageNumber,20);
		return productRepositery.findAll(p).getContent();
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
    public Product updateProduct(Product updatedProduct) {
		return productRepositery.save(updatedProduct);
    }


	public List<Transaction> getAllTransaction(String pageNumber){
		Pageable p = PageRequest.of(Integer.parseInt(pageNumber),20);
		return transactionRepositery.findAll(p).getContent();
	}

	@Override
	public long getCount() {
		return categoryRepositery.count();
	}

	@Override
	public long getCountPublic() {
		return categoryRepositery.countByIsPublicTrue();
	}

	@Override
	public long getCountPrivate() {
		return categoryRepositery.countByIsPublicFalse();
	}

	@Override
	public Category getCategoryByID(int categoryId) {
		return categoryRepositery.findById(categoryId).get();
	}

	@Override
	public Product getProductByID(int categoryId) {
		return productRepositery.findById(categoryId).get();
	}

	@Override
	public Category updateCategory(Category category) {
		return categoryRepositery.save(category);
	}

	@Override
	public long getCountProduct() {
		return productRepositery.count();
	}

	@Override
	public long getCountoutOfStockProduct() {
		return productRepositery.countByIsAvailableFalse();
	}

	@Override
	public Product getProductById(int id) {
		return productRepositery.findById(id).get();
	}


	@Override
	public List<Object[]> getAllCategoryName(){

		return categoryRepositery.getAllCategoryNameandId();

	}

}
