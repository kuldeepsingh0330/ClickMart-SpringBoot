package com.ransankul.clickmart.serviceImpl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ransankul.clickmart.model.Category;
import com.ransankul.clickmart.model.Product;
import com.ransankul.clickmart.repositery.ProductRepositery;
import com.ransankul.clickmart.service.ProductService;

@Service
public class ProductSrviceImpl implements ProductService {

    @Autowired
    private ProductRepositery productRepositery;

    @Override
    public List<Product> getproductbyCategory(Category categortId) {
        return productRepositery.findBycategory(categortId);
    }

    @Override
    public Product getProductById(int productId) {
            return productRepositery.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found with ID: " + productId));
    
    }

    @Override
    public List<Product> getAllProduct(String pageNumber) {
    	Pageable p = PageRequest.of(Integer.parseInt(pageNumber), 20);
        return productRepositery .findAll(p).getContent();
    }

    @Override
    public List<Product> searchProductByName(String name, String pageNumber) {
    	Pageable p = PageRequest.of(Integer.parseInt(pageNumber), 20);    	
        return productRepositery.findBynameContainingIgnoreCase(name,p);
    }

	@Override
	public String displayImage(int id) {
		List<String> list = this.productRepositery.getImagesByProductId(id);
		return list.get(0);
	}
    
}
