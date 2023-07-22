package com.ransankul.clickmart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

import com.ransankul.clickmart.model.Category;
import com.ransankul.clickmart.model.Product;
import com.ransankul.clickmart.service.AdminService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
// @PreAuthorize("hasRole('ROLE_ADMIN_USER')")
public class AdminController {
	
	@Autowired
	private AdminService adminService;


	@RequestMapping("/home")
	public String home(Model m){
		m.addAttribute("title","Home - ClickMart");
		return "home";
	}

	@RequestMapping("/category")
	public String category(Model m){
		m.addAttribute("title","Categoey - ClickMart");
		return "category";
	}

	@RequestMapping("/product")
	public String product(Model m){
		m.addAttribute("title","Product - ClickMart");
		return "product";
	}

	@RequestMapping("/order")
	public String order(Model m){
		m.addAttribute("title","Order - ClickMart");
		return "order";
	}

	@RequestMapping("/feedback")
	public String feedback(Model m){
		m.addAttribute("title","Feedback - ClickMart");
		return "feedback";
	}

	@RequestMapping("/help")
	public String help(Model m){
		m.addAttribute("title","Help - ClickMart");
		return "help";
	}
	
	@PostMapping("/category/add")
	public ResponseEntity<String> addCategory(@Valid @RequestBody Category category) {
		Category cat = adminService.addCategory(category);
		if(cat != null)
			return new ResponseEntity<>("Category added successfully", HttpStatus.CREATED);
		else
			return new ResponseEntity<String>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
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
    
}
