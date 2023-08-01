package com.ransankul.clickmart.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import com.ransankul.clickmart.model.Category;
import com.ransankul.clickmart.model.Feedback;
import com.ransankul.clickmart.model.Help;
import com.ransankul.clickmart.model.Product;
import com.ransankul.clickmart.model.Transaction;
import com.ransankul.clickmart.service.AdminService;
import com.ransankul.clickmart.repositery.CategoryRepositery;
import com.ransankul.clickmart.repositery.FeedbackRepositery;
import com.ransankul.clickmart.repositery.HelpRepositery;
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

	@Autowired
	private FeedbackRepositery feedbackRepositery;

	@Autowired
	private HelpRepositery helpRepositery;

	@Override
	public List<Category> getCategory(int pageNumber) {
		Pageable p = PageRequest.of(pageNumber, 20);
		return categoryRepositery.findAll(p).getContent();
	}

	@Override
	public Category addCategory(Category category) {
		if (categoryRepositery.existsByName(category.getName())) {
			throw new IllegalArgumentException("Category with the same name already exists");
		} else {
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
		} else {
			categoryRepositery.delete(cat);
			;
		}

	}

	@Override
	public void changeCategoryVisibility(int categoryId) {
		Category cat = categoryRepositery.findById(categoryId).get();

		if (cat == null) {
			throw new IllegalArgumentException("Category does not exist");
		} else {
			cat.setIsPublic(!cat.getIsPublic());
			categoryRepositery.save(cat);
		}
	}

	@Override
	public List<Product> getProduct(int pageNumber) {
		Pageable p = PageRequest.of(pageNumber, 20);
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
		} else {
			productRepositery.delete(product);
		}

	}

	@Override
	public Product updateProduct(Product updatedProduct) {
		return productRepositery.save(updatedProduct);
	}

	public List<Transaction> getAllTransaction(String pageNumber) {
		Pageable p = PageRequest.of(Integer.parseInt(pageNumber), 20, Sort.by(Sort.Direction.DESC, "id"));
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
		return productRepositery.countByAvailableFalse();
	}

	@Override
	public Product getProductById(int id) {
		return productRepositery.findById(id).get();
	}

	@Override
	public List<Object[]> getAllCategoryName() {

		return categoryRepositery.getAllCategoryNameandId();

	}

	@Override
	public boolean changeProductAvailibality(int productId) {
		Product pro = productRepositery.findById(productId).get();

		if (pro != null) {
			pro.setAvailable(!pro.getAvailable());
			productRepositery.save(pro);
			return true;
		}
		return false;
	}

	@Override
	public boolean addMoreProductQuantity(int productId, int quantity) {
		Product pro = productRepositery.findById(productId).get();

		if (pro != null) {
			int q = pro.getQuantity() + quantity;
			pro.setQuantity(q);
			productRepositery.save(pro);
			return true;
		}
		return false;
	}

	@Override
	public Transaction getTransactionById(long id) {
		return transactionRepositery.findById(id).get();
	}

	// feedback
	@Override
	public List<Feedback> getAllFeedbacks(int pageNumber) {
		Pageable p = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
		return feedbackRepositery.findAll(p).getContent();
	}

	// help
	@Override
	public List<Help> getAllHelp(int pageNumber) {
		Pageable p = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
		return helpRepositery.findByResolvedFalse(p).getContent();
	}
}
