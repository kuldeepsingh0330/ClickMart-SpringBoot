package com.ransankul.clickmart.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

import com.ransankul.clickmart.model.APIResponse;
import com.ransankul.clickmart.model.Category;
import com.ransankul.clickmart.model.Product;
import com.ransankul.clickmart.model.Transaction;
import com.ransankul.clickmart.model.User;
import com.ransankul.clickmart.payloads.TransactionResponse;
import com.ransankul.clickmart.service.AdminService;
import com.ransankul.clickmart.service.TransactionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
// @PreAuthorize("hasRole('ROLE_ADMIN_USER')")
public class AdminController {
	
	@Autowired
	private AdminService adminService;

	@PostMapping("/category/add")
	public ResponseEntity<String> addCategory(@Valid @RequestBody Category category) {
		Category cat = adminService.addCategory(category);
		if(cat != null)
			return new ResponseEntity<>("Category added successfully", HttpStatus.CREATED);
		else
			return new ResponseEntity<String>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping("/category/all")
	public ResponseEntity<APIResponse<List<Category>>> getCategory(@PathVariable String pageNumber) {
		List<Category> cat = adminService.getCategory(Integer.parseInt(pageNumber));
		if(cat.isEmpty()){
			return new ResponseEntity<APIResponse<List<Category>>>(new APIResponse<>("201", null, "No category found"), HttpStatus.OK);
		}
		
		APIResponse<List<Category>> response = new APIResponse("200",cat,"");
		return new ResponseEntity<APIResponse<List<Category>>>(response, HttpStatus.OK);
	}

	@DeleteMapping("category/rm/{categoryId}")
	public ResponseEntity<String> removeCategory(@PathVariable int categoryId) {
		adminService.removeCategory(categoryId);
	    return new ResponseEntity<>("Category removed successfully", HttpStatus.OK);
	}

	@PutMapping("category/vis/{categoryId}")
	public ResponseEntity<String> changeCategoryVisibility(
		@PathVariable int categoryId,
	    @RequestParam("visible") boolean visible) {
		adminService.changeCategoryVisibility(categoryId, visible);
	        return new ResponseEntity<>("Category visibility changed successfully", HttpStatus.OK);
	}
	
	
	//PRODUCTS-----------------------

	@PostMapping("/category/")
	public ResponseEntity<APIResponse<List<Category>>> getProduct(@PathVariable String pageNumber) {
		List<Category> product = adminService.getCategory(Integer.parseInt(pageNumber));
		if(product.isEmpty()){
			return new ResponseEntity<APIResponse<List<Category>>>(new APIResponse<>("201", null, "No product found"), HttpStatus.OK);
		}
		
		APIResponse<List<Category>> response = new APIResponse("200",product,"");
		return new ResponseEntity<APIResponse<List<Category>>>(response, HttpStatus.OK);
	}
	
    @PostMapping("product/")
    public ResponseEntity<String> addProduct(@Valid @RequestBody Product product) {
        adminService.addProduct(product);
        return new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("product/rm/{productId}")
    public ResponseEntity<String> removeProduct(@PathVariable int productId) {
    	adminService.removeProduct(productId);
        return new ResponseEntity<>("Product removed successfully", HttpStatus.OK);
    }

    @PutMapping("product/update/{productId}")
    public ResponseEntity<String> updateProduct(
            @PathVariable int productId,
            @Valid @RequestBody Product product) {
    	Product pro = adminService.updateProduct(productId, product);
    	if(pro != null)
    		return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
    	else
			return new ResponseEntity<String>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
					
    }

	//Transaction && Order

	@PostMapping("/order/{pageNumber}")
    public ResponseEntity<APIResponse<List<TransactionResponse>>> getAllPayments(@PathVariable String pageNumber,HttpServletRequest request){
        	List<Transaction> list = adminService.getAllTransaction(pageNumber);
        	List<TransactionResponse> response = new ArrayList<>();
        	for(Transaction tr : list) {
        		String status = tr.getStatus();
        		Date orderTime = tr.getCreated_at();
        		String transactionId = tr.getOrder_id();
        		
        		for(Product p : tr.getOrderedProduct()) {
        			TransactionResponse tResponse = new TransactionResponse();
        			tResponse.setImageName(p.getImages().get(0));
        			tResponse.setProductId(String.valueOf(p.getProductId()));
        			tResponse.setProductName(p.getName());
        			tResponse.setOrderTime(orderTime);
        			tResponse.setPaymentStatus(status);
        			tResponse.setTransactionId(transactionId);
        			
        			response.add(tResponse);
        		}
        	}
        	
        	return new ResponseEntity<APIResponse<List<TransactionResponse>>>(new APIResponse<>("200",response,"OK"),HttpStatus.OK);
        
    }
    
}
